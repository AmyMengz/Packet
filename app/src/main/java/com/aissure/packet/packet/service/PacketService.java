package com.aissure.packet.packet.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.aissure.packet.packet.job.BaseAccessbilityJob;
import com.aissure.packet.packet.job.IJobFactory;
import com.aissure.packet.packet.job.JobFactory;
import com.aissure.packet.packet.utils.Logger;


/**
 *
 */
public class PacketService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Logger.i(event.getClass()+"  "+event.getPackageName()+"  "+AccessibilityEvent.eventTypeToString(event.getEventType()));
        IJobFactory jobFactory = new JobFactory();
        BaseAccessbilityJob weChatJob = jobFactory.createWeiXinJob();
        weChatJob.onCreatJob(this);
        weChatJob.onReceiveJob(event);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return super.onKeyEvent(event);
    }

    public static boolean isNotificationServiceRunning(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN_MR2){
            return false;
        }
        try {

        }catch (Throwable t){}
        return  false;
    }
}
