package com.aissure.packet.packet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.aissure.packet.packet.activity.MuteNotificationActivity;

/**
 * Created by Administrator on 2017/8/3.
 */

public class OpenActivityUtil {
    public static boolean openApkByDetailInfo(Context context, String appPackageName, String className) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.setClassName(appPackageName, className);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Logger.i("openApkByDetailInfo=="+intent);
            return true;
        } catch (Exception e) {
            Logger.i("openApkByDetailInfo==Ex"+e);
            e.printStackTrace();
            return false;
        }
    }
    public static boolean startAutoStart(Activity context) {
        //com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivity
       return openApkByDetailInfo(context, C.PACKAGE_MIUI_SECURITY_CENTER, C.ACTIVITY_MIUI_AUTO_START);
    }

    public static void startMainActivity(Context context) {
        try{
            Intent intent = new Intent();
            intent.setClassName(C.PACKAGE_NAME, C.ACTIVITY_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch (Exception e){
            Logger.i("openApkByDetailInfo==Ex"+e);
            e.printStackTrace();
        }
    }
    public static void startMuteSetActivity(Context context) {
        openActivity(context, MuteNotificationActivity.class);
    }
    public static void openActivity(Context context,Class<? extends Activity> class1){
        Intent intent = new Intent(context, class1);
        context.startActivity(intent);

    }
}
