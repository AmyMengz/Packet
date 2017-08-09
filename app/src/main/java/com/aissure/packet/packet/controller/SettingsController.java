package com.aissure.packet.packet.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.adapter.SettingAdapter;
import com.aissure.packet.packet.utils.Config;
import com.aissure.packet.packet.utils.OpenActivityUtil;
import com.aissure.packet.packet.utils.PermissionUtil;
import com.aissure.packet.packet.utils.StringUtil;
import com.aissure.packet.packet.utils.UtilsDialog;
import com.aissure.packet.packet.views.SettingsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/29.
 */

public class SettingsController implements AdapterView.OnItemClickListener{
    List<HashMap<String,Object>> lists;
    Activity mContext;
    SettingsView viewInterface;
    SettingAdapter adapter;
    public static final int REQUEST_OPEN_ACCESSIBILITY = 0;
    public static final String KEY_TEXT= "KEY_TEXT";
    public static final String KEY_LITTLE_TEXT= "KEY_LITTLE_TEXT";
    public static final String KEY_ID= "KEY_ID";
    public static final int ID_OPEN_ACCESSIBILITY = 1;
    public static final int ID_SET_STABILITY = 2;
    public static final int ID_SET_DELEY_TIME = 3;
    public static final int ID_SET_MUTE_NOTIFICATION = 4;
    public static final int ID_IS_NOTIFY_SOUND = 5;

    public static final int ID_RETURN_TO_HOME = 6;
//    public static final int
    public SettingsController(Activity context, SettingsView viewInterface){
        this.mContext = context;
        this.viewInterface = viewInterface;
        lists= new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> map1 = new HashMap<>();
        map1.put(KEY_ID,ID_OPEN_ACCESSIBILITY);
        map1.put(KEY_TEXT,mContext.getResources().getString(R.string.setting_open_Accessibility));

        HashMap<String,Object> map2 = new HashMap<>();
        map2.put(KEY_ID,ID_SET_STABILITY);
        map2.put(KEY_TEXT,mContext.getResources().getString(R.string.setting_stability));
        map2.put(KEY_LITTLE_TEXT,mContext.getResources().getString(R.string.setting_open_auto_start));

        HashMap<String,Object> map3 = new HashMap<>();
        map3.put(KEY_ID,ID_SET_DELEY_TIME);
        map3.put(KEY_TEXT,mContext.getResources().getString(R.string.setting_delay_time));
        HashMap<String,Object> map4 = new HashMap<>();
        map4.put(KEY_ID,ID_SET_MUTE_NOTIFICATION);
        map4.put(KEY_TEXT,mContext.getResources().getString(R.string.setting_mute_notification));

        HashMap<String,Object> map5 = new HashMap<>();
        map5.put(KEY_ID,ID_IS_NOTIFY_SOUND);
        map5.put(KEY_TEXT,mContext.getResources().getString(R.string.setting_is_notify_sound));

        HashMap<String,Object> map6 = new HashMap<>();
        map6.put(KEY_ID,ID_RETURN_TO_HOME);
        map6.put(KEY_TEXT,mContext.getResources().getString(R.string.setting_return_to_home));



        lists.add(map1);
        lists.add(map2);
        lists.add(map3);
        lists.add(map4);
        lists.add(map5);
        lists.add(map6);
        adapter = new SettingAdapter(mContext,lists);
        viewInterface.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettingAdapter.ViewHolder holder = (SettingAdapter.ViewHolder) view.getTag();
        boolean status = holder.toggleButton.isChecked();
        Map<String,Object> map = lists.get(position);
        switch ((Integer)map.get(KEY_ID)){
            case ID_OPEN_ACCESSIBILITY:
                PermissionUtil.openAccessibilitySetForResult(mContext,REQUEST_OPEN_ACCESSIBILITY);

                break;
            case ID_SET_DELEY_TIME:
                int deleyTime = Config.getConfig(mContext).getWechatOpenDelayTime();
                UtilsDialog.getUtilsDialog(mContext).showChangTextDialog("" + deleyTime, mContext.getString(R.string.setting_delay_time),
                        "", mContext.getString(R.string.millisecond), new UtilsDialog.DialogOptListener() {
                            @Override
                            public void onBack(Object o) {
                                if(!(o instanceof EditText)){
                                    return;
                                }
                                EditText et = (EditText)o;
                                if(TextUtils.isEmpty(et.getText())){
                                    viewInterface.showErrorToast(mContext.getString(R.string.setting_delay_time_not_null));
                                }else {
                                    String newTime = et.getText().toString();

                                    if(!StringUtil.isNumber(newTime)){
                                        viewInterface.showErrorToast(mContext.getString(R.string.setting_delay_time_must_be_number));
                                    }else {
                                        Config.getConfig(mContext).setWechatOpenDelayTime(Integer.parseInt(newTime));
                                        viewInterface.showToast(mContext.getString(R.string.set_ok));
                                    }
                                }

                            }
                        });
                break;
            case ID_SET_STABILITY:
                viewInterface.showToast(mContext.getString(R.string.setting_open_auto_start_permission));
               if(! OpenActivityUtil.startAutoStart(mContext)){
                   Intent intent = new Intent();
                intent.setAction(Settings.ACTION_QUICK_LAUNCH_SETTINGS);
                           getAppDetailSettingIntent();
                   mContext.startActivity(intent);
               };
                break;
            case ID_SET_MUTE_NOTIFICATION:
                boolean state = holder.toggleButton.isChecked();
                holder.toggleButton.setChecked(!state);
                Config.getConfig(mContext).setIsMute(!state);
                if(!state){
                    OpenActivityUtil.startMuteSetActivity(mContext);
                }
                break;
            case ID_IS_NOTIFY_SOUND:

                Config.getConfig(mContext).setIsNotifySound(!status);
                holder.toggleButton.setChecked(!status);
                break;
            case ID_RETURN_TO_HOME:
                Config.getConfig(mContext).setReturnHome(!status);
                holder.toggleButton.setChecked(!status);
                break;
        }
    }
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(),null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());

        }
//        localIntent.setAction(Intent.ACTION_MAIN);
//        localIntent.setClassName("com.android.settings","com.android.settings.AppOpsDetailsActivity");
//        localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        //com.android.settings/.Settings$AppOpsDetailsActivity
        return localIntent;
    }



}
