package com.aissure.packet.packet.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.aissure.packet.packet.job.AccessibilityJob;
import com.aissure.packet.packet.job.QQJob;
import com.aissure.packet.packet.job.WeChatJob;
import com.aissure.packet.packet.utils.Config;
import com.aissure.packet.packet.utils.Logger;
import com.aissure.packet.packet.utils.OpenActivityUtil;
import com.aissure.packet.packet.utils.SendBroadCastUtil;
import com.aissure.packet.packet.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 */
public class PacketService extends AccessibilityService {
    private HashMap<String, AccessibilityJob> jobMap;
    List<AccessibilityJob> jobs;
    private static final Class[] ACCESSBILITY_JOBS= {
            WeChatJob.class, QQJob.class
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("onCreate==========");
        jobMap = new HashMap<>();
        jobs = new ArrayList<>();
        for (Class clazz:ACCESSBILITY_JOBS){
            try {
                Object obj = clazz.newInstance();
                if(obj instanceof AccessibilityJob){
                    AccessibilityJob job = (AccessibilityJob)obj;
                    job.onCreatJob(this);
                    jobs.add(job);
                    jobMap.put(job.getTargetPackageName(),job);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Logger.i("onAccessibilityEvent==========");
        Logger.i(event.getClass()+"  "+event.getPackageName()+"  "+AccessibilityEvent.eventTypeToString(event.getEventType()));
//        IJobFactory jobFactory = new JobFactory();
//        BaseAccessibilityJob weChatJob = jobFactory.createWeiXinJob();
//        weChatJob.onCreatJob(this);
//        weChatJob.onReceiveJob(event);
//
//        BaseAccessibilityJob qqJob = jobFactory.createQQJob();
//        qqJob.onCreatJob(this);
//        qqJob.onReceiveJob(event);
        boolean isMute = getConfig().isMute();
        if(isMute){
            String start = getConfig().getMuteStart();
            String end = getConfig().getMuteEnd();
//            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
//            sdf.format(new Date());
//            Date date = sdf.parse(start);
            if(TimeUtil.isMuteTime(start,end)){
                return;
            }
        }
        String pkn = String.valueOf(event.getPackageName());
        if(jobs!=null&&!jobs.isEmpty()){
            for (AccessibilityJob job:jobs){
                if(pkn.equals(job.getTargetPackageName())){
                    job.onReceiveJob(event);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Logger.i("onServiceConnected");
        OpenActivityUtil.startMainActivity(this);
        SendBroadCastUtil.serviceConnected(this);
        Toast.makeText(this, "已连接抢红包服务", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(jobMap!=null){
            jobMap.clear();
        }
        if(jobs!=null&&!jobs.isEmpty()){
            for (AccessibilityJob job:jobs){
                job.onStopJob();
            }
            jobs.clear();
        }
        jobs = null;
        jobMap = null;
        SendBroadCastUtil.serviceDisconnected(this);
    }
    public Config getConfig() {
        return Config.getConfig(this);
    }
}
