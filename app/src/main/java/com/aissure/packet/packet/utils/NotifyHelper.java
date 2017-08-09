package com.aissure.packet.packet.utils;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;

import com.aissure.packet.packet.R;

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
    public static void wakeAndUnlock(Context context){
        PowerManager.WakeLock wl = getPowerManager(context).newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|
        PowerManager.SCREEN_BRIGHT_WAKE_LOCK,"bright");
        wl.acquire();
        wl.release();
        KeyguardManager.KeyguardLock kl = getKeyguardManager(context).newKeyguardLock("Lock");
        kl.disableKeyguard();
        Logger.i("wakeAndUnlock========");
    }
    /** 执行PendingIntent事件*/
    public static void send(PendingIntent pendingIntent) {
        try {
            pendingIntent.send();
            Logger.i("pendingIntent:"+pendingIntent);
        } catch (PendingIntent.CanceledException e) {
            Logger.i("eeee:"+e);
            e.printStackTrace();
        }
    }

    public static void showNotify(Context context, String s) {

    }
    /** 播放效果、声音与震动*/
    public static void playEffect(Context context, Config config) {
        //夜间模式，不处理
        if(TimeUtil.isMuteTime(config) && config.isMute()) {
            return;
        }

        if(config.isNotifySound()) {
            sound(context);
            vibrator(context);
        }
//        if(config.isNotifyVibrate()) {

//        }
    }
    /** 播放声音*/
    public static void sound(Context context) {
        try {
//            MediaPlayer player = MediaPlayer.create(context,Uri.parse("file:///system/media/audio/ui/MessageIncoming.ogg"));
                    /*Uri.parse("file:///system/media/audio/ui/camera_click.ogg""sdcard/hb.mp3"));*/
            MediaPlayer player = MediaPlayer.create(context, R.raw.hb);
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Vibrator sVibrator;

    /** 振动*/
    public static void vibrator(Context context) {
        if(sVibrator == null) {
            sVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        sVibrator.vibrate(new long[]{100, 10, 100, 400}, -1);
    }
}
