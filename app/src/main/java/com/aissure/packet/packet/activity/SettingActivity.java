package com.aissure.packet.packet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.adapter.SettingAdapter;
import com.aissure.packet.packet.controller.SettingsController;
import com.aissure.packet.packet.utils.Logger;
import com.aissure.packet.packet.utils.PermissionUtil;
import com.aissure.packet.packet.views.SettingsView;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.ViewInject;

/**
 * Created by Administrator on 2017/7/29.
 */
@ContentViewInject(R.layout.activity_setting)
public class SettingActivity extends BaseActivity implements SettingsView {

    @ViewInject(R.id.lv_setting)
    ListView lvSettings;
    SettingsController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);
        initReact();
        controller = new SettingsController(this, this);
        lvSettings.setOnItemClickListener(controller);
    }

    @Override
    public void initReact() {
        initActionBar();
        setBackLeft();
        setTitle(getString(R.string.setting_title));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SettingsController.REQUEST_OPEN_ACCESSIBILITY:
                updateAccessibilityState();
                break;
        }
        Logger.i("onActivityResult=======" + requestCode);
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        lvSettings.setAdapter(adapter);
    }


    private void updateAccessibilityState(){
        int firstVisiblePosition = lvSettings.getFirstVisiblePosition();
        int lastVisiblePosition = lvSettings.getLastVisiblePosition();
        if(0>=firstVisiblePosition && 0<=lastVisiblePosition){
            View view = lvSettings.getChildAt(0 - firstVisiblePosition);
            if(view.getTag() instanceof SettingAdapter.ViewHolder){
                SettingAdapter.ViewHolder vh = (SettingAdapter.ViewHolder) view.getTag();
                vh.toggleButton.setChecked(PermissionUtil.isAccessibilitySettingsOn(this));
            }
        }
    }
}
