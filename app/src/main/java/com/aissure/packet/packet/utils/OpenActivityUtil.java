package com.aissure.packet.packet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
            Logger.i("intent================"+intent);
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
}
