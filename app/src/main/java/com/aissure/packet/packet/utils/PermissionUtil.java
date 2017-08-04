package com.aissure.packet.packet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import com.aissure.packet.packet.R;

import com.aissure.packet.packet.service.PacketService;


/**
 * Created by Administrator on 2017/7/14.
 */

public class PermissionUtil {
    public static boolean checkAccessibility(Context context) {
        if (!isAccessibilitySettingsOn(context)) {
            openAccessibilitySet(context);
            return false;
        }
        return true;
    }

    public static void openAccessibilitySet(Context context){
        try {
            //打开系统设置中辅助功能
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            context.startActivity(intent);
            Toast.makeText(context, String.format(context.getResources().getString(R.string.main_check_permission), context.getString(R.string.app_name))
                    , Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Logger.i("openAccessibilitySet = EEEEEEE" + e);
            e.printStackTrace();
        }
    }
    public static void openAccessibilitySetForResult(Activity context,int requestCode){
        try {
            //打开系统设置中辅助功能
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            context.startActivityForResult(intent,requestCode);
            Toast.makeText(context, String.format(context.getResources().getString(R.string.main_check_permission), context.getString(R.string.app_name))
                    , Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Logger.i("openAccessibilitySet = EEEEEEE" + e);
            e.printStackTrace();
        }
    }

    /**
     * Android检测辅助功能是否开启
     *
     * @param mContext
     * @return
     */
    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = mContext.getPackageName() + "/" + PacketService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            Logger.i("accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Logger.i("Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Logger.i("***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Logger.i("accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Logger.i("We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Logger.i("***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }
}
