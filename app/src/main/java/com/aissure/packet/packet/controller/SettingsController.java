package com.aissure.packet.packet.controller;

import android.app.Activity;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.adapter.SettingAdapter;
import com.aissure.packet.packet.views.SettingsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */

public class SettingsController {
    List<HashMap<String,Object>> lists;
    Activity mContext;
    SettingsView viewInterface;
    public static final String KEY_TEXT= "KEY_TEXT";
    public static final String KEY_LITTLE_TEXT= "KEY_LITTLE_TEXT";
    public SettingsController(Activity context, SettingsView viewInterface){
        this.mContext = context;
        this.viewInterface = viewInterface;
        lists= new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> map1 = new HashMap<>();
        lists.add(map1);
        map1.put(KEY_TEXT,mContext.getResources().getString(R.string.setting_open_Accessibility));
        map1.put(KEY_LITTLE_TEXT,mContext.getResources().getString(R.string.setting_open_auto_start));
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put(KEY_TEXT,mContext.getResources().getString(R.string.setting_delay_time));

        lists.add(map2);
        lists.add(map1);
        SettingAdapter adapter = new SettingAdapter(mContext,lists);
        viewInterface.setAdapter(adapter);

    }
}
