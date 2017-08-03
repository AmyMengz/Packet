package com.aissure.packet.packet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.controller.MainController;
import com.aissure.packet.packet.utils.PermissionUtil;
import com.aissure.packet.packet.views.MainView;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.OnClick;
import com.mz.annotation.ViewInject;

@ContentViewInject(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainView {

    @ViewInject(R.id.tv_main_lucky_money_count)
    TextView tvLuckyMoneyCount;
    @ViewInject(R.id.gv_main_other)
    GridView gvMainOther;
    @ViewInject(R.id.btn_main_start)
    Button btnStart;

    @ViewInject(R.id.vp_main_ad)
    ViewPager vpAd;
    @ViewInject(R.id.tv_picking_tips)
    TextView tvPickingTips;
    MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);
        String string = Settings.Secure.getString(getContentResolver(),
                "enabled_notification_listeners");
        if (!string.contains(NotificationListenerService.class.getName())) {
            startActivity(new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
        controller = new MainController(this, this);
//        PermissionUtil.checkAccessibility(this);
        initReact();
        if(PermissionUtil.isAccessibilitySettingsOn(this)){
            btnStart.setText(getString(R.string.main_pause));
            tvPickingTips.setVisibility(View.VISIBLE);

        }else {
            btnStart.setText(getString(R.string.main_start));
            tvPickingTips.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.btn_main_start,R.id.left_image})
    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_main_start:
                if(PermissionUtil.checkAccessibility(this)){
                    btnStart.setText(getString(R.string.main_pause));
                    tvPickingTips.setVisibility(View.VISIBLE);
                }else{
                    btnStart.setText(getString(R.string.main_start));
                    tvPickingTips.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.left_image:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
        }
    }

    @Override
    public void initReact() {
        initActionBar();
        leftImage.setImageResource(R.drawable.setting1);
        leftImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void setGridAdapter(SimpleAdapter adapter) {
        gvMainOther.setAdapter(adapter);
    }


    @Override
    public void setViewPagerAdapter(PagerAdapter adapter) {
        vpAd.setAdapter(adapter);
    }
}
