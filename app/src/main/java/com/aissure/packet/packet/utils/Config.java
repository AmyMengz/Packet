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
    public static SharedPreferences getSettingsSpInstance(Context context){
        if(settingSp ==null){
            settingSp = context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        }
        return settingSp;
    }

    public static String getString(Context  context,String key,String value){
        return  getSettingsSpInstance(context).getString(key,value);
    }
    public static void setString(Context context,String key,String value){
        getSettingsSpInstance(context).edit().putString(key,value).commit();
    }

    public static int getInt(Context  context,String key,int value){
        return  getSettingsSpInstance(context).getInt(key,value);
    }
    public static void setInt(Context context,String key,int value){
        getSettingsSpInstance(context).edit().putInt(key,value).commit();
    }

    public static boolean getBoolean(Context  context,String key,boolean value){
        return  getSettingsSpInstance(context).getBoolean(key,value);
    }
    public static void setBoolean(Context context,String key,boolean value){
        getSettingsSpInstance(context).edit().putBoolean(key,value).commit();
    }


    public static boolean getIsReceiving(Context context){
        return getSettingsSpInstance(context).getBoolean(KEY_IS_RECEIVING,D_IS_RECEIVING);
    }
    public static boolean setIsReceiving(Context context,boolean value){
        return getSettingsSpInstance(context).edit().putBoolean(KEY_IS_RECEIVING,value).commit();
    }
    public static final String KEY_WECHAT_DELAY_TIME = "KEY_WECHAT_DELAY_TIME";
    public static final int D_WECHAT_DELAY_TIME = 0;
    private static final String KEY_AGREEMENT = "KEY_AGREEMENT";
    /** 微信打开红包后延时时间*/
    public static int getWechatOpenDelayTime(Context context) {
        return getInt(context,KEY_WECHAT_DELAY_TIME,D_WECHAT_DELAY_TIME);
    }
    public static void setWechatOpenDelayTime(Context context,int value) {
        setInt(context,KEY_WECHAT_DELAY_TIME,D_WECHAT_DELAY_TIME);
    }

    /** 免费声明*/
    public static boolean isAgreement(Context context) {
        return getBoolean(context,KEY_AGREEMENT, false);
    }

    /** 设置是否同意*/
    public static void setAgreement(Context context,boolean agreement) {
        setBoolean(context,KEY_AGREEMENT, agreement);
    }

}
