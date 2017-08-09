package com.aissure.packet.packet.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/7/27.
 */

public class Config {
    public static final String KEY_IS_RECEIVING = "key_is_receiving";
    public static final boolean D_IS_RECEIVING = false;

    public static SharedPreferences settingSp;
    public static final String spName = "settingSp";
    private Config(Context context){
        mContext = context;
        if(settingSp ==null){
            settingSp = context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        }
    }
    private static Config instance;
    private Context mContext;
    public static Config getConfig(Context context){
        if(instance==null){
            synchronized (Config.class){
                if(instance==null){
                    instance = new Config(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
//    public static SharedPreferences getSettingsSpInstance(Context context){
//        if(settingSp ==null){
//            settingSp = context.getSharedPreferences(spName,Context.MODE_PRIVATE);
//        }
//        return settingSp;
//    }

    public String getString(String key,String value){
        return  settingSp.getString(key,value);
    }
    public void setString(String key,String value){
        settingSp.edit().putString(key,value).commit();
    }

    public int getInt(String key,int value){
        return  settingSp.getInt(key,value);
    }
    public boolean setInt(String key,int value){
        return settingSp.edit().putInt(key,value).commit();
    }

    public boolean getBoolean(String key,boolean value){
        return  settingSp.getBoolean(key,value);
    }
    public void setBoolean(String key,boolean value){
        settingSp.edit().putBoolean(key,value).commit();
    }


    public boolean getIsReceiving(){
        return settingSp.getBoolean(KEY_IS_RECEIVING,D_IS_RECEIVING);
    }
    public static boolean setIsReceiving(boolean value){
        return settingSp.edit().putBoolean(KEY_IS_RECEIVING,value).commit();
    }
    public static final String KEY_WECHAT_DELAY_TIME = "KEY_WECHAT_DELAY_TIME";
    public static final int D_WECHAT_DELAY_TIME = 0;
    private static final String KEY_AGREEMENT = "KEY_AGREEMENT";

    private static final String KEY_QQ_ALL_PACKET_COUNT = "KEY_QQ_ALL_PACKET_COUNT";
    private static final String KEY_WECHAT_ALL_PACKET_COUNT = "KEY_WECHAT_ALL_PACKET_COUNT";
    private static final String KEY_QQ_ALL_PACKET_COUNT_TODAY = "KEY_QQ_ALL_PACKET_COUNT_TODAY";
    private static final String KEY_WECHAT_ALL_PACKET_COUNT_TODAY = "KEY_WECHAT_ALL_PACKET_COUNT_TODAY";

    private static final String KEY_MUTE_TIME_START = "KEY_MUTE_TIME_START";
    private static final String D_MUTE_TIME_START = "00:00";

    private static final String KEY_MUTE_TIME_END = "KEY_MUTE_TIME_END";
    private static final String D_MUTE_TIME_END = "07:00";

    private static final String KEY_IS_MUTE = "KEY_IS_MUTE";
    private static final boolean D_IS_MUTE = true;

    private static final String KEY_IS_NOTIFY_SOUND = "KEY_IS_NOTIFY_SOUND";
    private static final boolean D_IS_NOTIFY_SOUND = false;

    private static final String KEY_IS_START_PICKING = "KEY_IS_START_PICKING";
    private static final boolean D_IS_START_PICKING = false;

    private static final String KEY_IS_RETURN_HOME = "KEY_IS_RETURN_HOME";
    private static final boolean D_IS_RETURN_HOME = true;

    /** 微信打开红包后延时时间*/
    public int getWechatOpenDelayTime() {
        return getInt(KEY_WECHAT_DELAY_TIME,D_WECHAT_DELAY_TIME);
    }
    public boolean setWechatOpenDelayTime(int value) {
        return setInt(KEY_WECHAT_DELAY_TIME,value);
    }

    /** 免费声明*/
    public boolean isAgreement() {
        return getBoolean(KEY_AGREEMENT, false);
    }

    /** 设置是否同意*/
    public void setAgreement(boolean agreement) {
        setBoolean(KEY_AGREEMENT, agreement);
    }

//    public static int getQQAllPacketCount()
    public void setMuteStart(String start){
        setString(KEY_MUTE_TIME_START,start);
    }
    public String getMuteStart(){
        return getString(KEY_MUTE_TIME_START,D_MUTE_TIME_START);
    }

    public void setMuteEnd(String end){
        setString(KEY_MUTE_TIME_END,end);
    }
    public String getMuteEnd(){
        return getString(KEY_MUTE_TIME_END,D_MUTE_TIME_END);
    }

    public void setIsMute(boolean isMute){
        setBoolean(KEY_IS_MUTE,isMute);
    }
    public boolean isMute(){
        return getBoolean(KEY_IS_MUTE,D_IS_MUTE);
    }

    public void setIsNotifySound(boolean isNotifySound){
        setBoolean(KEY_IS_NOTIFY_SOUND,isNotifySound);
    }
    public boolean isNotifySound(){
        return getBoolean(KEY_IS_NOTIFY_SOUND,D_IS_NOTIFY_SOUND);
    }

    public void setIsPicking(boolean value){
        setBoolean(KEY_IS_START_PICKING,value);
    }
    public boolean isPicking(){
        return getBoolean(KEY_IS_START_PICKING,D_IS_START_PICKING);
    }

    public void setReturnHome(boolean value){
        setBoolean(KEY_IS_RETURN_HOME,value);
    }

    public boolean isReturnHome(){
        return getBoolean(KEY_IS_RETURN_HOME,D_IS_RETURN_HOME);
    }

}
