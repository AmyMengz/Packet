package com.aissure.packet.packet.job;

import android.content.Context;

import com.aissure.packet.packet.service.PacketService;
import com.aissure.packet.packet.utils.Config;


/**
 * Created by Administrator on 2017/7/14.
 */

public abstract class BaseAccessibilityJob implements AccessibilityJob {
    private PacketService service;

    @Override
    public void onCreatJob(PacketService service) {
        this.service = service;
    }
    public Context getContext() {
        return service.getApplicationContext();
    }
    public PacketService getService(){
        return service;
    }
    public Config getConfig() {
        return service.getConfig();
    }
}
