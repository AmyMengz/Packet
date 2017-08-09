package com.aissure.packet.packet.job;

import android.view.accessibility.AccessibilityEvent;

import com.aissure.packet.packet.service.IStatusBarNotification;
import com.aissure.packet.packet.service.PacketService;


/**
 * Created by Administrator on 2017/7/13.
 */

public interface AccessibilityJob {
    String getTargetPackageName();
    void onCreatJob(PacketService setvice);
    void onReceiveJob(AccessibilityEvent event);
    void onStopJob();
    void onNotificationPosted(IStatusBarNotification sbn);
    boolean isEnable();
}
