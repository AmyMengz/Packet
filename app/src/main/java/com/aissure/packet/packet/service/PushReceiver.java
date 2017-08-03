package com.aissure.packet.packet.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aissure.packet.packet.utils.Logger;

/**
 * Created by Administrator on 2017/8/3.
 */

public class PushReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.i("intent::"+intent);
        switch (intent.getAction()){
            case Intent.ACTION_USER_PRESENT:

                break;
            case Intent.ACTION_PACKAGE_ADDED:

                break;
        }

    }
}
