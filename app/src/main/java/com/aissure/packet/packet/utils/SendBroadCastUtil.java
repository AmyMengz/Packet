package com.aissure.packet.packet.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/8/7.
 */

public class SendBroadCastUtil {

    public static void serviceDisconnected(Context context){
        Intent intent = new Intent(C.ACTION_PACKET_SERVICE_DISCONNECT);
        context.sendBroadcast(intent);
    }

    public static void serviceConnected(Context context){
        Intent intent = new Intent(C.ACTION_PACKET_SERVICE_CONNECT);
        context.sendBroadcast(intent);
    }

}
