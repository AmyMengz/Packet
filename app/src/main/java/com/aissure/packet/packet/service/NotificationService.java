package com.aissure.packet.packet.service;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.aissure.packet.packet.utils.Logger;

/**
 * Created by Administrator on 2017/8/3.
 */

public class NotificationService extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Logger.i("open======"+sbn.toString());

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Logger.i("close======"+sbn.toString());
    }
}
