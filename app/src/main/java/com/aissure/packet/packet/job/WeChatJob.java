package com.aissure.packet.packet.job;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.aissure.packet.packet.utils.AccessibilityHelper;
import com.aissure.packet.packet.utils.C;
import com.aissure.packet.packet.utils.Config;
import com.aissure.packet.packet.utils.Logger;
import com.aissure.packet.packet.utils.NotifyHelper;

import java.util.List;

import service.PacketService;


/**
 * Created by Administrator on 2017/7/14.
 */

public class WeChatJob extends BaseAccessbilityJob {
    PacketService service;
    private static final int WINDOW_NONE = 0;
    private static final int WINDOW_LUCKYMONEY_RECEIVEUI = 1;
    private static final int WINDOW_LUCKYMONEY_DETAIL = 2;
    private static final int WINDOW_LAUNCHER = 3;
    private static final int WINDOW_OTHER = -1;

    private int mCurrentWindow = WINDOW_NONE;
    private boolean isReceivingLuckyMoney;

    private static final int FROM_NOTIFY = 0;
    private static final int FROM_CHAT_WINDOW = 1;
    private static final int FROM_CHAT_LIST = 2;

    private int curFrom = FROM_NOTIFY;
    private  static  WeChatJob instance;
    private WeChatJob(){

    }
    public static  WeChatJob getWeChatJob(){
        if(instance==null){
            synchronized (WeChatJob.class){
                if(instance==null){
                    instance = new WeChatJob();
                }
            }
        }
        return instance;

    }
    @Override
    public void onCreatJob(PacketService service) {
        super.onCreatJob(service);
        this.service = service;
    }

    @Override
    public void onReceiveJob(AccessibilityEvent event) {
        Logger.i("isReceivingLuckyMoney onReceiveJob:" + getIsReceivingFlag());

        final int eventType = event.getEventType();
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

                if (mCurrentWindow != WINDOW_LAUNCHER) {//不在聊天界面或者聊天列表，不处理
                    return;
                }
                try {

                    AccessibilityNodeInfo nodeInfo = getService().getRootInActiveWindow();

                    List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/a4l");
                    Logger.i("child:::nodes" +nodes);
                    if(nodes!=null &&!nodes.isEmpty()){
                        AccessibilityNodeInfo node = nodes.get(0);
                        int count = node.getChildCount();
                        AccessibilityNodeInfo child = node.getChild(count - 1);
                        if(child!=null&&child.getChildCount()>0){
                            AccessibilityNodeInfo child2 = child.getChild(child.getChildCount()-1);
                            List<AccessibilityNodeInfo> hongbao = child2.findAccessibilityNodeInfosByText("领取红包");
                            if(hongbao!=null &&!hongbao.isEmpty()){
                                AccessibilityNodeInfo hongbao2 = hongbao.get(hongbao.size()-1);

//                    Logger.i("node:" + node);
                                AccessibilityHelper.performClick(hongbao2);
                                setIsReceivingFlag(false);
                            }
                        }
                    }else {
                        AccessibilityNodeInfo nodeInfo2 = getService().getRootInActiveWindow();
                        AccessibilityNodeInfo list = AccessibilityHelper.findNodeInfosByClassName(nodeInfo2,C.LISTVIEW_CLASS_NAME);
                        Logger.i("list:::nodes" +list);
                        if(list!=null&&list.getChildCount()>0){
                            int count = list.getChildCount();
                            AccessibilityNodeInfo child = list.getChild(count - 1);
                            if(child!=null&&child.getChildCount()>0){
                                AccessibilityNodeInfo child2 = child.getChild(child.getChildCount()-1);
                                List<AccessibilityNodeInfo> hongbao = child2.findAccessibilityNodeInfosByText("领取红包");
                                Logger.i("hongbao:::nodes" +hongbao);
                                if(hongbao!=null &&!hongbao.isEmpty()){
                                    AccessibilityNodeInfo hongbao2 = hongbao.get(hongbao.size()-1);

//                    Logger.i("node:" + node);
                                    AccessibilityHelper.performClick(hongbao2);
                                    setIsReceivingFlag(false);
                                }
                            }
                        }
                    }

                }catch(Exception e){
                    Logger.i("child:::e" + e);
                    e.printStackTrace();
                }


//                if (getIsReceivingFlag()) {
//                    handleChatListHongBao();
//                }
                break;
        }
    }
    /**
     * 聊天界面打开红包
     *
     * @param event
     */
    private void handleWindowChanged(AccessibilityEvent event) {
        Logger.i("isReceivingLuckyMoney handleOpenPacket:" + getIsReceivingFlag());
        if (C.LUCKY_MONEY_OPEN_UI.equals(event.getClassName())) {//拆红包
            mCurrentWindow = WINDOW_LUCKYMONEY_RECEIVEUI;
            handleLuckyMoneyReceive();
        } else if (C.LUCKY_MONEY_DETAIL_UI.equals(event.getClassName())) {//拆红包后的红包详情
            mCurrentWindow = WINDOW_LUCKYMONEY_DETAIL;
//            if(getConfig().getWechatAfterGetHongBaoEvent() == Config.WX_AFTER_GET_GOHOME) { //返回主界面，以便收到下一次的红包通知
            AccessibilityHelper.performBack(getService());
            setIsReceivingFlag(false);

//            }
        } else if (C.CHAT_ACTIVITY_UI.equals(event.getClassName())) {//聊天界面//聊天列表界面
            mCurrentWindow = WINDOW_LAUNCHER;
            handleChatListHongBao();
        } else {
            mCurrentWindow = WINDOW_OTHER;
        }

    }

    /**
     * 拆红包界面
     */
    private void handleLuckyMoneyReceive() {
        Logger.i("handleLuckyMoneyReceive");
        AccessibilityNodeInfo nodeInfo = getService().getRootInActiveWindow();
//        Logger.i("拆红包  nodeInfo::::" + nodeInfo);
        if (nodeInfo == null) {
            return;
        }
        AccessibilityNodeInfo targetNode = null;
        targetNode = AccessibilityHelper.findNodeInfosById(nodeInfo, "com.tencent.mm:id/bn9");
//        Logger.i("targetNode::::" + targetNode);
        if (targetNode == null) {

        }

        if (targetNode != null) {
            long sDelayTime = Config.getWechatOpenDelayTime(getContext());
            final AccessibilityNodeInfo node = targetNode;
//            AccessibilityHelper.performClick(node);
            if (sDelayTime != 0) {
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AccessibilityHelper.performClick(node);
                    }
                }, sDelayTime);

            }
        }
    }

    /**
     * 点击聊天界面的红包
     */
    private void handleChatListHongBao() {
        Logger.i("handleChatListHongBao :" + getIsReceivingFlag());
        try {
            AccessibilityNodeInfo nodeInfo = getService().getRootInActiveWindow();
            Logger.i("nodeInfo:" + nodeInfo);
//           isGroupChatUI(nodeInfo);
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(C.PICKING_LUCKY_MONEY);
            Logger.i("list::"+"  "+(list!=null?list.size():null)+" em:"+(list!=null?list.isEmpty():null));
            if (list != null) {
                if (getIsReceivingFlag()) {
                    AccessibilityNodeInfo node = list.get(list.size() - 1);
//                    Logger.i("node:" + node);
                    AccessibilityHelper.performClick(node);
                    setIsReceivingFlag(false);
                }

            }

//            //com.tencent.mm:id/i8
//            List<AccessibilityNodeInfo> list2 = //nodeInfo.findAccessibilityNodeInfosByText(C.LUCKY_MONEY_TEXT_KEY);
//            nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ai1");//最近一条消息
//            Logger.i("list::"+"  "+(list2!=null?list2.size():null)+" em:"+(list2!=null?list2.isEmpty():null));
//            if (list2 != null&&!list2.isEmpty()) {
////                if (getIsReceivingFlag()) {
//                    AccessibilityNodeInfo node = list2.get(0);
//                    Logger.i("node:" + node);
//                    node.getText();
////
////                    AccessibilityHelper.performClick(node);
////                    setIsReceivingFlag(true);
////                }
//
//            }
        } catch (Exception e) {
            Logger.i("e:" + e);
        }

    }

    /**
     * @param nodeInfo
     * @return
     */
    private boolean isGroupChatUI(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        String id = "com.tencent.mm:id/ces";
//        int wv = getWechatVersion();
//        if(wv <= 680) {
        id = "com.tencent.mm:id/ew";
//        } else if(wv <= 700) {
        id = "com.tencent.mm:id/cbo";
//        }
        String title = null;
        AccessibilityNodeInfo target = AccessibilityHelper.findNodeInfosById(nodeInfo, id);
        if (target != null) {
            title = String.valueOf(target.getText());
        }
        Logger.i("title:" + title + "  target:" + target);
        return true;

    }
/***************************************************通知******************************************************************/
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
//        if(PacketService)
        List<CharSequence> texts = event.getText();
        if (!texts.isEmpty()) {
            String text = String.valueOf(texts.get(0));
            notificationEvent(text, (Notification) data);
        }
    }

    /**
     * 通知栏接收信息包含【微信红包】信息
     *
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
        if (text.contains(C.LUCKY_MONEY_TEXT_KEY)) {
            openPacketNotification(data);
        }
    }

    private void openPacketNotification(Notification notification) {

        setIsReceivingFlag(true);
        Logger.i("isReceivingLuckyMoney:openPacketNotification " + getIsReceivingFlag());
        PendingIntent pendingIntent = notification.contentIntent;
        boolean lock = NotifyHelper.isLockScreen(getContext());
        if (!lock) {
            NotifyHelper.send(pendingIntent);
        } else {
//            NotifyHelper.showNotify(getContext(),
//                    String.valueOf(notification.tickerText),
//                    pendingIntent);
        }
    }

    public Context getContext() {
        return service.getApplicationContext();
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
