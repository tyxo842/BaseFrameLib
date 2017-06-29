package com.tyxo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by LY on 2016/12/21 14: 23.
 * Mail      tyxo842@163.com
 * Describe :
 */

public class PhoneUtils {


    //启动手机飞行模式----未解决
    public static void startPhoneFlyMode(Context context,boolean enabling){
        /*//Setting airplane_mode_on has moved from android.provider.Settings.System to android.provider.Settings.Global
        //SendBroadcastPermission: action:android.intent.action.MEDIA_SCANNER_SCAN_FILE, mPermissionType:0
        Settings.System.putInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON,enabling ? 1 : 0);
        Intent intent2 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent2.putExtra("state",enabling);
        context.sendBroadcast(intent2);*/

        /*//Setting airplane_mode_on has moved from android.provider.Settings.System to android.provider.Settings.Global, value is unchanged.
        //SendBroadcastPermission: action:android.intent.action.MEDIA_SCANNER_SCAN_FILE, mPermissionType:0
        Settings.System.putInt(context.getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,enabling ? 1 : 0);
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent2.putExtra("state",enabling);
        context.sendBroadcast(intent2);*/

        ContentResolver contentResolver = context.getContentResolver();
        if(Settings.System.getString(contentResolver,Settings.System.AIRPLANE_MODE_ON).equals("0")){
            Settings.System.putString(contentResolver,Settings.System.AIRPLANE_MODE_ON, "1");
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            context.sendBroadcast(intent);// java.lang.SecurityException: Permission Denial: not allowed to send broadcast android.intent.action.AIRPLANE_MODE from pid=11356, uid=10268
        }else{
            //否则关闭飞行
            Settings.System.putString(contentResolver,Settings.System.AIRPLANE_MODE_ON, "0");
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            context.sendBroadcast(intent);
        }
    }

    //启动手机飞行模式----未解决
    public static void openAirplaneModeOn(Context context,boolean enabling) {
        //Settings.Global.putInt(context.getContentResolver(),
        //        Settings.Global.AIRPLANE_MODE_ON,enabling ? 1 : 0);
        //在4.2以上的版本app没有权限修改Setting.Global的，解决的办法就是把应用放到源码中去编译,并添加一个报红权限
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON,enabling ? 1 : 0);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", enabling);
        context.sendBroadcast(intent);//会崩溃
    }

}
