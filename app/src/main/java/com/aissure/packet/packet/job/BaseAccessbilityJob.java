package com.aissure.packet.packet.job;

import android.view.accessibility.AccessibilityEvent;

import com.aissure.packet.packet.service.IStatusBarNotification;
import com.aissure.packet.packet.service.PacketService;


/**
 * Created by Administrator on 2017/7/14.
 */

public class BaseAccessbilityJob implements AccessbilityJob {
    private PacketService service;
    @Override
    public String getTargetPackageName() {
        return null;
    }

    @Override
    public void onCreatJob(PacketService service) {
        this.service = service;
    }

    @Override
    public void onReceiveJob(AccessibilityEvent event) {

    }

    @Override
    public void onStopJob() {

    }

    @Override
    public void onNotificationPosted(IStatusBarNotification service) {

    }

    @Override
    public boolean isEnable() {
        return false;
    }
    public PacketService getService(){
        return service;
    }
}
