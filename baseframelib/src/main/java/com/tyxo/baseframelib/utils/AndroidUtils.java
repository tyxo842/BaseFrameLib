package com.tyxo.baseframelib.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tyxo.baseframelib.utils.logblt.AppLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helin on 2015/9/18.
 */
public class AndroidUtils {

    private static Toast toast;

    public static void showToast(Context context, String msg) {
        if (context == null || TextUtils.isEmpty(msg)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }


    /**
     * 显示dialog，默认是访问网络的loadingDialog
     *
     * @return
     */
//    public static Dialog showDialog(Context context, Dialog dialog) {
//        return showDialog(context, dialog, null);
//    }

//    public static Dialog showDialog(Context context, Dialog dialog,
//                                    String text) {
//        if (dialog == null) {
//            dialog = new Dialog(context, R.style.netLoadingDialog);
//            dialog.setContentView(R.layout.dialog_net_loading);
//            dialog.setCanceledOnTouchOutside(false);
//            // loadingDialog.setCancelable(false);
//        }
//        if (!TextUtils.isEmpty(text)) {
//            ((TextView) dialog.findViewById(R.id.loadingdialog_text))
//                    .setText(text);
//        }
//        if (!((Activity) context).isFinishing()) {
//            if (!dialog.isShowing()) {
//                dialog.show();
//            }
//        }
//        return dialog;
//    }
    public static void dismissLoading(Dialog loadingDialog) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public static void startActivity(Activity activity,
                                     Class<? extends Activity> clazz) {
        activity.startActivity(new Intent(activity, clazz));
    }

    public static void startActivityClearTop(Context context,
                                             Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void startActivitySingleClearTop(Context context,
                                                   Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * 判断程序是否运行在前端
     *
     * @param mContext
     * @param className
     * @return
     */
    public static boolean isAppRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = activityManager
                .getRunningTasks(1);
        if (!(taskList.size() > 0)) {
            return false;
        }
        isRunning = taskList.get(0).baseActivity.getClassName().contains(
                className);
        return isRunning;
    }

    /**
     * 判断一个程序是否显示在前端,根据测试此方法执行效率在11毫秒,无需担心此方法的执行效率
     *
     * @param packageName 程序包名
     * @param context     上下文环境
     * @return true--->在前端,false--->不在前端
     */
    public static boolean isApplicationShowing(String packageName,
                                               Context context) {
        boolean result = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        if (appProcesses != null) {
            for (RunningAppProcessInfo runningAppProcessInfo : appProcesses) {
                if (runningAppProcessInfo.processName.equals(packageName)) {
                    int status = runningAppProcessInfo.importance;
                    if (status == RunningAppProcessInfo.IMPORTANCE_VISIBLE
                            || status == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            AndroidUtils.showToast(context, "当前网络不可用，请检查网络设置");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        AndroidUtils.showToast(context, "当前网络不可用，请检查网络设置");
        return false;
    }

//    public static boolean checkNetworkAvailable(Context context) {
//        ConnectivityManager connectivity = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity == null) {
//            AndroidUtils.showToast(context, R.string.net_status_error);
//            AppLogger.i("NetWorkState Unavailabel");
//            return false;
//        } else {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        AppLogger.i("NetWorkState Availabel");
//                        return true;
//                    }
//                }
//            }
//        }
//        AndroidUtils.showToast(context, R.string.net_status_error);
//        return false;
//    }

    /**
     * 获取版本号
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取版本名称
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return verName;
    }

    /**
     * 获取android系统版本号
     */
    public static String getAndroidVerCode(Context context) {
        String androidVerCode = "";
        try {
            androidVerCode = android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return androidVerCode;
    }

    /**
     * 多少时间后显示软键盘
     */
    public static void showInputMethod(final View view, long delayMillis) {
        if (view != null)
        // 显示输入法
        {
            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    showInputMethod(view);
                }
            }, delayMillis);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showInputMethod(View view) {
        if (view == null) return;
        if (view instanceof EditText) view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            boolean success = imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            AppLogger.d("success ? " + success);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideInputMethod(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideInputMethod(final View view, long delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideInputMethod(view);
            }
        }, delayMillis);
    }


    /**
     * 显示软键盘
     */
    public static void showInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 显示键盘
     *
     * @param view
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.showSoftInput(view, 0);
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboardForce(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * 获取imsi
     */
    public static String getImsi(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        return imsi;
    }

    /**
     * 获取imei
     */
    public static String getImei(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }

    /**
     * 判断SIM卡是否存在
     *
     * @param context
     * @return
     */
    public static boolean isSimState(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int simState = mTelephonyMgr.getSimState();
        if (simState == TelephonyManager.SIM_STATE_ABSENT
                || simState == TelephonyManager.SIM_STATE_UNKNOWN) {
            return false;
        }
        return true;
    }

    /**
     * 获取电话号码
     */
    public static String getTel(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tel = mTelephonyMgr.getLine1Number();
        return tel;
    }

    /**
     * 强制不弹出软键盘
     */
    public static void hackToggleSoftInput(Activity activity, EditText et,
                                           boolean isShow) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            et.setInputType(InputType.TYPE_NULL);
        } else {
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setSoftInputShownOnFocus;
                setSoftInputShownOnFocus = cls.getMethod(
                        "setSoftInputShownOnFocus", boolean.class);
                setSoftInputShownOnFocus.setAccessible(true);
                setSoftInputShownOnFocus.invoke(et, isShow);
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }

    public static boolean isSdcardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long availCount = sf.getAvailableBlocks();
            long blockSize = sf.getBlockSize();
            long availSize = availCount * blockSize / 1024;

            if (availSize >= 3072) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static float convertDp2Px(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(1, (float) dp, metrics);
    }

    public static float convertSp2Px(Context context, int sp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(2, (float) sp, metrics);
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String getImei(Context context, String imei) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            AppLogger.e(e.getMessage());
        }
        return imei;
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多张图片的选择
     *
     * @param activity
     * @param mList
     * @param requestCode
     */
    public static void startMultiImageSelectorActivity(Activity activity, Fragment fragment, ArrayList<String> mList, int requestCode) {
//        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
//        // 是否显示拍摄图片
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
//        // 选择模式
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
//        // 默认选择
//        if (mList != null && mList.size() > 0) {//默认选择后的再相册中回显
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mList);
//        }
//        if (fragment == null) {
//            activity.startActivityForResult(intent, requestCode);
//            return;
//        }
//        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 多张图片的选择
     *
     * @param activity
     * @param mList
     * @param requestCode
     * @param selectCount 可选数量
     */
    public static void startMultiImageSelectorActivity(Activity activity, Fragment fragment, ArrayList<String> mList, int requestCode, int selectCount) {
//        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
//        // 是否显示拍摄图片
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, selectCount);
//        // 选择模式
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
//        // 默认选择
//        if (mList != null && mList.size() > 0) {//默认选择后的再相册中回显
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mList);
//        }
//        if (fragment == null) {
//            activity.startActivityForResult(intent, requestCode);
//            return;
//        }
//        fragment.startActivityForResult(intent, requestCode);
    }


    /**
     * 单图选择
     *
     * @param activity
     * @param fragment
     * @param mList
     * @param requestCode
     */
    public static void startSingleImageSelectorActivity(Activity activity, Fragment fragment, ArrayList<String> mList, int requestCode) {
//        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
//        // 是否显示拍摄图片
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
//        // 选择模式
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
//        // 默认选择
//        if (mList != null && mList.size() > 0) {//默认选择后的再相册中回显
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mList);
//        }
//        if (fragment == null) {
//            activity.startActivityForResult(intent, requestCode);
//            return;
//        }
//        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳到照片详情界面
     *
     * @param mList
     * @param currentPosition
     * @param requestCode
     */
    public static void startImageDetailsActivity(Activity activity, Fragment fragment, List<String> mList, int currentPosition, int requestCode, boolean isShowDelete) {

//        Intent intent = new Intent(activity, ImageDetailsActivity.class);
//        if (mList != null && mList.size() > 0) {//默认选择后的再相册中回显
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, (ArrayList) mList);
//        }
//        intent.putExtra(ImageDetailsActivity.EXTRA_CURRENT_POSITION, currentPosition);//当前点击的图片的位置传递
//        intent.putExtra("isShowDelete", isShowDelete);//是否显示删除图标
//        if (fragment == null) {
//            activity.startActivityForResult(intent, requestCode);
//            return;
//        }
//        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }
        AppLogger.d("sBar = " + sbar);
        return sbar;
    }

    /**
     * 判断团队和当前用户的关系
     */
//    public static void judgeUserTeamRelation(boolean isExec, long teamId, final IVolleyListener<Res137010> iVolleyListener) {
//        if (!isExec)
//            return;
//        Map<String, String> params = new HashMap<>();
//        params.put("id", String.valueOf(teamId));
//        VolleyUtil.getInstance().doGsonObjRequestGet(AppConstants.URL_137010, Res137010.class, params, new IVolleyListener<Res137010>() {
//            @Override
//            public void onResponse(Res137010 response) {
//                iVolleyListener.onResponse(response);
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                iVolleyListener.onErrorResponse(error);
//            }
//        });
//    }

    public static final int WIDTH = 180 * 2;
    public static final int HEIGHT = 120 * 2;

    /**
     * 从服务器端获取的图片传入客户端需要的尺寸重新获取
     *
     * @param url
     * @param x
     * @param y
     * @return
     */
    public static String getSuitableUrlByXY(String url, int x, int y) {
        if (StringUtils.isEmptyOrNull(url)) {
            return "";
        }
        return url.replaceFirst("(https?://img.baolaiyun.com.+?)(_\\d+x\\d+)?(\\.\\w+(\\?.+)?)$", "$1_" + x + "x" + y + "$3");
    }

}
