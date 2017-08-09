package com.aissure.packet.packet.service;

import android.app.Notification;

/**
 * Created by Administrator on 2017/7/14.
 */

public interface IStatusBarNotification {
    String getpackageName();
    Notification getNotification();
}
