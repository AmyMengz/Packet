package com.aissure.packet.packet.job;

import android.view.accessibility.AccessibilityEvent;

import service.IStatusBarNotification;
import service.PacketService;


/**
 * Created by Administrator on 2017/7/13.
 */

public interface AccessbilityJob {
    String getTargetPackageName();
    void onCreatJob(PacketService setvice);
    void onReceiveJob(AccessibilityEvent event);
    void onStopJob();
    void onNotificationPosted(IStatusBarNotification service);
    boolean isEnable();
}
