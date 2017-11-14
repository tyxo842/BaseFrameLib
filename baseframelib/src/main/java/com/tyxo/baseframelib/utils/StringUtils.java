package com.tyxo.baseframelib.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author kkklaf
 */
public class StringUtils {

    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 是否为空或空格
     */
    public static boolean isBlankOrNull(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 是否为空
     */
    public static boolean isEmptyOrNull(String str) {
        return (str == null || str.equals("null") || TextUtils.isEmpty(str));
    }

    /**
     * 分割字符串
     *
     * @param str       String 原始字符串
     * @param splitsign String 分隔符
     * @return String[] 分割后的字符串数组
     */
    public static String[] split(String str, String splitsign) {
        int index;
        if (str == null || splitsign == null)
            return null;
        ArrayList<String> al = new ArrayList<String>();
        while ((index = str.indexOf(splitsign)) != -1) {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return (String[]) al.toArray(new String[0]);
    }

    /**
     * 判断list是否有效
     */
    public static <T> boolean isListValidate(List<T> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public static <K, V> boolean isMapValidate(Map<K, V> map) {
        if (map != null && !map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 将null转换成""，非null保留原格式
     */
    public static String nullStrToEmpty(String str) {
        return (str == null ? "" : str);
    }

    /**
     * 字符串首字母大写
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmptyOrNull(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                : new StringBuilder(str.length())
                .append(Character.toUpperCase(c))
                .append(str.substring(1)).toString();
    }

    /**
     * 字符串首url UTF-8编码,失败返回原字符串
     */
    public static String utf8Encode(String str) {
        if (!isEmptyOrNull(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * 字符串首GBK编码
     */
    public static String GBKEncode(String str) {
        if (!isEmptyOrNull(str) && str.getBytes().length != str.length()) {
            try {
                return new String(str.getBytes("GBK"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * 字符串首url UTF-8编码 ,失败返回默认值
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmptyOrNull(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * 是否是http开头
     *
     * @param str
     * @return
     */
    public static boolean isHttpUrl(String str) {
        if (!isEmptyOrNull(str)) {
            if (str.startsWith("http")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取html href 中的内容
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmptyOrNull(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern
                .compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * 将html中的敏感内容 &lt,&gt,&amp,&quot转换成<,> & \
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtils.isEmptyOrNull(source) ? source : source
                .replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * 全角转半角
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmptyOrNull(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 半角转全角
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmptyOrNull(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][123456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 将数值转换为千位符格式
     *
     * @param value
     * @return
     */
    public static String formatValue(String value) {
        BigDecimal bd = new BigDecimal(Double.parseDouble(value));
        DecimalFormat df = new DecimalFormat(",###,###");
        return df.format(bd);
    }

    /**
     * 将数值转换为千位符格式<br/>
     * 以'.'为标志设置两端的字体大小
     *
     * @param value
     * @param firstSize  前端字体
     * @param secondSize 后端字体
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder formatValue(String value, int firstSize, int secondSize) {

        BigDecimal bd = new BigDecimal(Double.parseDouble(value));
        DecimalFormat df = new DecimalFormat(",###,###.00");
        String str = df.format(bd);
        int start = str.indexOf(".");
        int length = str.length();

        String part1 = str.substring(0, start);
        String part2 = str.substring(start);

        SpannableString spannableString1 = new SpannableString(part1);
        spannableString1.setSpan(new AbsoluteSizeSpan(firstSize, true), 0,
                part1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString spannableString2 = new SpannableString(part2);
        spannableString2.setSpan(new AbsoluteSizeSpan(secondSize, true), 0,
                part2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(spannableString1).append(spannableString2);

        return ssb;

    }

    /**
     * 删除空格回车换行
     *
     * @param str
     * @return
     */
    public static String delSpace(String str) {
        if (str != null) {
            str = str.replaceAll("\r", "");
            str = str.replaceAll("\n", "");
            str = str.replace(" ", "");
        }
        return str;
    }

    /**
     * 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名。
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 判断输入的字符串是否为纯汉字
     *
     * @param str 传入的字符窜
     * @return 如果是纯汉字返回true, 否则返回false
     */
    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为浮点数，包括double和float
     *
     * @param str 传入的字符串
     * @return 是浮点数返回true, 否则返回false
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String FilterChineseName(String name) {
        name = name.replaceAll("[^(a-zA-Z0-9_\\u4e00-\\u9fa5)]", "");
        return name;
    }

    public static <T> T getJsonString(JSONObject jsonObject, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonObject.toString(), cls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }


    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 是否电话号码 1，如果是电话，那么区号和电话之间必须有横杠，并且必须有区号
     * 2，如果是手机，可以支持手机前带上“+86”、“86”、“86-”、“+86-”前缀
     * 3，电话只能匹配中国大陆的。02开头共三位，01开头的只允许010北京的号 固话7至8位
     * 010-120412014 020-89571800-125
     */
    public static boolean isTelephone(String phone) {
        boolean ret = false;
        String[] telRegex = new String[]{"^[1][0123456789]\\d{9}$",
                "^(010|02\\d{1}|0[3-9]\\d{2})-\\d{7,8}(-\\d+)?$"};
        if (TextUtils.isEmpty(phone))
            return false;
        else {
            for (String reg : telRegex) {
                ret = phone.matches(reg);
                if (ret) {
                    return ret;
                }
            }
        }
        return false;
    }

    /**
     * 将长时间yyyy-MM-dd HH:mm:ss格式字符串转换为时间 MM-dd mm:ss
     *
     * @param strDate
     * @return
     */
    public static String formatStrDate(String strDate) {
        if (TextUtils.isEmpty(strDate) || "null".equalsIgnoreCase(strDate)) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition parsePosition = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, parsePosition);
        SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd HH:mm");
        String format = formatter1.format(strtodate);
        return format;
    }

    public static String doubleTrans(double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }

    /**
     * 身份证验证
     *
     * @param id
     * @return
     */
    public static boolean isIDCard(String id) {
        Pattern pattern = Pattern.compile("\\d{15}|\\d{17}(x|X|[0-9])");
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }

    /**
     * 根据志愿者服务类别获取名字
     *
     * @param type
     * @return
     */
    public static String getServiceNameByType(int type) {
        if (type == 1) {
            return "志愿者诊断服务";
        } else if (type == 2) {
            return "志愿者药事服务";
        } else if (type == 3) {
            return "志愿者宣传服务";
        }
        return "";
    }

    public static int formNum(int num) {
        return num + (5 - num % 5);
    }

    /**
     * 直辖市去重
     *
     * @param city
     * @return
     */
    public static String getCityName(String city) {
        if (city.equals("重庆市") || city.equals("上海市") || city.equals("北京市") || city.equals("天津市")) {
            return "";
        }
        return city;
    }

    public static String subTeamNameStr(String str) {
        if (!StringUtils.isEmptyOrNull(str) && str.length() > 40) {
            String s1 = str.substring(0, 25);//含头 不含尾
            String s2 = "...";
            String s3 = str.substring(str.length() - 12);
            str = s1 + s2 + s3;
        }
        return str;
    }

    public static SpannableStringBuilder subTeamNameStr(SpannableStringBuilder str) {
        if (!StringUtils.isEmptyOrNull(str.toString()) && str.length() > 40) {
            CharSequence c1 = str.subSequence(0, 25);//含头 不含尾
            CharSequence c2 = "...";
            CharSequence c3 = str.subSequence(str.length() - 12,str.length());
            str = new SpannableStringBuilder("");
            str.append(c1);
            str.append(c2);
            str.append(c3);
        }
        return str;
    }
}
