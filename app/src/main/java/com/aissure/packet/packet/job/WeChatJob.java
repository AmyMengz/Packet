package com.aissure.packet.packet.job;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.aissure.packet.packet.utils.AccessibilityHelper;
import com.aissure.packet.packet.utils.C;
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

    @Override
    public void onCreatJob(PacketService service) {
        super.onCreatJob(service);
        this.service = service;
    }

    @Override
    public void onReceiveJob(AccessibilityEvent event) {
//        super.onReceiveJob(event);
        Logger.i("isReceivingLuckyMoney onReceiveJob:"+isReceivingLuckyMoney);
        final int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                handleNotification(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                handleOpenPacket(event);
                break;
            case AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION:
                if(mCurrentWindow !=WINDOW_LAUNCHER){//不在聊天界面或者聊天列表，不处理
                    return;
                }
                if(isReceivingLuckyMoney){
                    handleChatListHongBao();
                }
                break;
        }

    }

    /**
     * 聊天界面打开红包
     * @param event
     */
    private void handleOpenPacket(AccessibilityEvent event) {
        Logger.i("isReceivingLuckyMoney handleOpenPacket:"+isReceivingLuckyMoney);

        Logger.i("--wechat----------" + event.getClass() + " className:" + event.getClassName() +"==="+ C.LUCKY_MONEY_OPEN_UI.equals(event.getClassName()));
        if (C.LUCKY_MONEY_OPEN_UI.equals(event.getClassName())) {
            mCurrentWindow = WINDOW_LUCKYMONEY_RECEIVEUI;//拆红包界面
            //点中了红包，下一步就是去拆红包
            handleLuckyMoneyReceive();
        } else if (C.LUCKY_MONEY_DETAIL_UI.equals(event.getClassName())) {
            mCurrentWindow = WINDOW_LUCKYMONEY_DETAIL;
            //拆完红包后看详细的纪录界面
//            if(getConfig().getWechatAfterGetHongBaoEvent() == Config.WX_AFTER_GET_GOHOME) { //返回主界面，以便收到下一次的红包通知
                AccessibilityHelper.performBack(getService());
//            }
        } else if (C.CHAT_ACTIVITY_UI.equals(event.getClassName())) {//当前聊天界面
            mCurrentWindow = WINDOW_LAUNCHER;
//            //在聊天界面,去点中红包
            handleChatListHongBao();
        } else {
//            mCurrentWindow = WINDOW_OTHER;
        }

    }

    /**
     * 拆红包界面
     */
    private void handleLuckyMoneyReceive() {
        AccessibilityNodeInfo nodeInfo = getService().getRootInActiveWindow();
        Logger.i("拆红包  nodeInfo::::"+nodeInfo);
        if(nodeInfo == null){
            return;
        }
        AccessibilityNodeInfo targetNode = null;
        targetNode = AccessibilityHelper.findNodeInfosById(nodeInfo,"com.tencent.mm:id/bnr");
        Logger.i("targetNode::::"+targetNode);
        if(targetNode==null){

        }

        if(targetNode !=null){
            final AccessibilityNodeInfo node = targetNode;
            AccessibilityHelper.performClick(node);
        }


    }

    /**
     * 点击聊天界面的红包
     */
    private void handleChatListHongBao() {
        Logger.i("isReceivingLuckyMoney handleChatListHongBao:"+isReceivingLuckyMoney);
//        int mode = getConfig
        try {
            AccessibilityNodeInfo nodeInfo = getService().getRootInActiveWindow();
            Logger.i("nodeInfo:" + nodeInfo);
//           isGroupChatUI(nodeInfo);
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(C.PICKING_LUCKY_MONEY);
            Logger.i("list:" + list.size() + "  " + list.isEmpty());
            if (list != null && list.isEmpty()) {
                AccessibilityNodeInfo node = AccessibilityHelper.findNodeInfoByText(nodeInfo, C.LUCKY_MONEY_TEXT_KEY);
                if (node != null) {

                   isReceivingLuckyMoney = true;
                    AccessibilityHelper.performClick(nodeInfo);
                }
            }else if(list!=null){
                Logger.i("list!=null:" + (list!=null)+" isReceivingLuckyMoney:"+isReceivingLuckyMoney);
                if(isReceivingLuckyMoney){
                    AccessibilityNodeInfo node = list.get(list.size() - 1);
                    Logger.i("nodenodenodenode:" + node);
                    AccessibilityHelper.performClick(node);
                    isReceivingLuckyMoney = false;
                }

            }
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

    /**
     * 通知栏接收通知
     * Notification
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
     * @param ticker
     * @param data
     */
    private void notificationEvent(String ticker, Notification data) {
        String text = ticker;
        int index = text.indexOf(":");
        Logger.i("notify::"+text);
        if (index != -1) {
            text = text.substring(index + 1);
        }
        text = text.trim();
        Logger.i("notify::"+text);
        if (text.contains(C.LUCKY_MONEY_TEXT_KEY)) {
            openPacketNotification(data);
        }
    }

    private void openPacketNotification(Notification notification) {
        isReceivingLuckyMoney = true;
        Logger.i("isReceivingLuckyMoney:"+isReceivingLuckyMoney);
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


}
