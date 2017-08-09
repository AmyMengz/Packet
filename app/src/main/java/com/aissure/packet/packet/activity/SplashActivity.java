package com.aissure.packet.packet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.controller.SplashController;
import com.aissure.packet.packet.views.SplashView;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/7/25.
 */
@ContentViewInject(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements SplashView{

    @ViewInject(R.id.vp_splash_vp)
    ViewPager viewPager;

    SplashController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);
        if(getConfig().isAgreement()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        controller = new SplashController(this,this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void setViewPager(PagerAdapter adapter,int index){
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);
    }

    @Override
    public void initReact() {

    }
}
