package com.aissure.packet.packet.job;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.aissure.packet.packet.service.IStatusBarNotification;
import com.aissure.packet.packet.service.PacketService;
import com.aissure.packet.packet.utils.AccessibilityHelper;
import com.aissure.packet.packet.utils.C;
import com.aissure.packet.packet.utils.Logger;
import com.aissure.packet.packet.utils.NotifyHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */

public class QQJob extends BaseAccessibilityJob {

    boolean isReceivingLuckyMoney;
//    private  static  QQJob instance;
//    private QQJob(){
//    }
//    public static  QQJob getQQJob(){
//        if(instance==null){
//            synchronized (WeChatJob.class){
//                if(instance==null){
//                    instance = new QQJob();
//                }
//            }
//        }
//        return instance;
//    }

    @Override
    public String getTargetPackageName() {
        return C.QQ_PACKAGENAME;
    }

    @Override
    public void onCreatJob(PacketService service) {
        super.onCreatJob(service);

    }

    @Override
    public void onReceiveJob(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        Logger.i("QQ:"+AccessibilityEvent.eventTypeToString(event.getEventType()));
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                handleNotification(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗口变化
                handleWindowChanged(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
//                setIsReceivingFlag(true);
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED://界面内变化
                handleOpenPacket(event);

        }
    }

    /**
     * 窗体变化
     * @param event
     */
    private void handleWindowChanged(AccessibilityEvent event) {
        switch (event.getClassName().toString()){
            case C.QQ_CHAT_ACTIVITY_UI:
                handleOpenPacket(event);
                break;
            case C.QQ_LUCKY_MONEY_DETAIL_UI:
                if(getConfig().isReturnHome()){
                    AccessibilityHelper.performHome(getService());
                }else {
                    AccessibilityHelper.performBack(getService());
                }
                break;
        }

    }
    private void handleOpenPacket(AccessibilityEvent event){
        AccessibilityNodeInfo nodeInfo = getService().getRootInActiveWindow();

        if (nodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> targetNodes = nodeInfo.findAccessibilityNodeInfosByText(C.QQ_PICKING_KEY);
        if(targetNodes == null||targetNodes.isEmpty()){
            return;
        }
        final AccessibilityNodeInfo targetNode = targetNodes.get(targetNodes.size()-1);
        long sDelayTime = getConfig().getWechatOpenDelayTime();
        Logger.i("点击拆开  nodeInfo::::" + nodeInfo);
        if (sDelayTime != 0) {
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AccessibilityHelper.performClick(targetNode);
                }
            }, sDelayTime);
        }else {
            AccessibilityHelper.performClick(targetNode);
        }
    }

    @Override
    public void onStopJob() {

    }

    @Override
    public void onNotificationPosted(IStatusBarNotification sbn) {

    }

    @Override
    public boolean isEnable() {
        return false;
    }

    /**
     * 通知栏接收通知
     * Notification
     *
     * @param event
     */
    private void handleNotification(AccessibilityEvent event) {
        Parcelable data = event.getParcelableData();
        if (data == null || !(data instanceof Notification)) {
            return;
        }
        List<CharSequence> texts = event.getText();
        if (!texts.isEmpty()) {
            String text = String.valueOf(texts.get(0));
            notificationEvent(text, (Notification) data);
        }
    }

    /**
     * 通知栏接收信息包含【微信红包】信息
     * @param ticker
     * @param data
     */
    private void notificationEvent(String ticker, Notification data) {
        String text = ticker;
        int index = text.indexOf(":");
        Logger.i("notify::" + text);
        if (index != -1) {
            text = text.substring(index + 1);
        }
        text = text.trim();
        Logger.i("notify::" + text);
        if (text.contains(C.QQ_LUCKY_MONEY_TEXT_KEY)) {
            openPacketNotification(data);
        }
    }

    private void openPacketNotification(Notification notification) {
        setIsReceivingFlag(true);
        PendingIntent pendingIntent = notification.contentIntent;
        boolean lock = NotifyHelper.isLockScreen(getContext());
//        if (!lock) {
            NotifyHelper.send(pendingIntent);
//        } else {
////            NotifyHelper.showNotify(getContext(),
////                    String.valueOf(notification.tickerText),
////                    pendingIntent);
//        }
        NotifyHelper.playEffect(getContext(), getConfig());
        if(lock){
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    NotifyHelper.wakeAndUnlock(getContext());
                }
            },1000);

        }
    }

    public void setIsReceivingFlag(boolean falg) {
        isReceivingLuckyMoney = falg;
    }

    public boolean getIsReceivingFlag() {
        return isReceivingLuckyMoney;
    }
    private Handler mHandler = null;
    private Handler getHandler() {
        if(mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }
}
