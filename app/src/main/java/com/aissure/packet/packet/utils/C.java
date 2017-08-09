package com.aissure.packet.packet.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/7/14.
 */

public class C {

    public static final String WECHAT_PACKAGENAME = "com.tencent.mm";
    public static final String QQ_PACKAGENAME = "com.tencent.mobileqq";
    /** 红包消息的关键字*/
    public static final String LUCKY_MONEY_TEXT_KEY = "[微信红包]";
    public static final String QQ_LUCKY_MONEY_TEXT_KEY = "[QQ红包]";

    public static final String PICKING_LUCKY_MONEY = "领取红包";
    public static final String CHAT_ACTIVITY_UI="com.tencent.mm.ui.LauncherUI";//聊天列表界面
    public static final String LUCKY_MONEY_OPEN_UI = "com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f";//打开红包界面//"com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI".
    public static final String LUCKY_MONEY_DETAIL_UI = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI";
    public static final String BUTTON_CLASS_NAME = "android.widget.Button";
    public static final String LISTVIEW_CLASS_NAME = "android.widget.ListView";

    public static final String PACKAGE_MIUI_SECURITY_CENTER = "com.miui.securitycenter";
    public static final String ACTIVITY_MIUI_AUTO_START = "com.miui.permcenter.autostart.AutoStartManagementActivity";


    public static final String PACKAGE_NAME= "com.aissure.packet.packet";
    public static final String ACTIVITY_MAIN = "com.aissure.packet.packet.activity.MainActivity";


    public static final String QQ_CHAT_ACTIVITY_UI = "com.tencent.mobileqq.activity.SplashActivity";
    public static final String QQ_LUCKY_MONEY_DETAIL_UI = "cooperation.qwallet.plugin.QWalletPluginProxyActivity";
    public static final String QQ_PICKING_KEY = "点击拆开";

//    com.tencent.mobileqq/cooperation.qwallet.plugin.QWalletPluginProxyActivity



    public static final String ACTION_PACKET_SERVICE_DISCONNECT = "com.aissure.accessibility.disconnected";
    public static final String ACTION_PACKET_SERVICE_CONNECT = "com.aissure.accessibility.connected";
    public static final String PATH_HB_SOUND = Environment.getExternalStorageDirectory()+ File.separator+"Aissure"+File.separator+"hb.mp3";
//    public static final String
}
