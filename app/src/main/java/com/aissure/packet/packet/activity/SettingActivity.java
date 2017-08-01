package com.aissure.packet.packet.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.controller.SettingsController;
import com.aissure.packet.packet.views.SettingsView;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.ViewInject;

/**
 * Created by Administrator on 2017/7/29.
 */
@ContentViewInject(R.layout.activity_setting)
public class SettingActivity extends BaseActivity implements SettingsView{

    @ViewInject(R.id.lv_setting)
    ListView lvSettings;
    SettingsController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);
        initReact();
        controller = new SettingsController(this,this);
    }

    @Override
    public void initReact() {
        initActionBar();
        setBackLeft();
        setTitle(getString(R.string.setting_title));
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        lvSettings.setAdapter(adapter);
    }
}
