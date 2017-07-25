package com.aissure.packet.packet.utils;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

/**
 * Created by Administrator on 2017/7/14.
 */

public class NotifyHelper {
    private static KeyguardManager sKeyguardManager;
    private static PowerManager sPowerManager;

    public static boolean isLockScreen(Context context){
        KeyguardManager km = getKeyguardManager(context);
        return km.inKeyguardRestrictedInputMode()||!isScreenOn(context);
    }
    public static KeyguardManager getKeyguardManager(Context context) {
        if(sKeyguardManager == null) {
            sKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        }
        return sKeyguardManager;
    }
    public static PowerManager getPowerManager(Context context) {
        if(sPowerManager == null) {
            sPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        }
        return sPowerManager;
    }
    public static boolean isScreenOn(Context context){
        PowerManager pm = getPowerManager(context);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT_WATCH){
            return pm.isInteractive();
        }else {
            return pm.isScreenOn();
        }
    }
    /** 执行PendingIntent事件*/
    public static void send(PendingIntent pendingIntent) {
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void showNotify(Context context, String s) {

    }
}
