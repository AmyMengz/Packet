package com.aissure.packet.packet.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.activity.MainActivity;
import com.aissure.packet.packet.activity.UserProtocolActivity;
import com.aissure.packet.packet.adapter.MyPagerAdapter;
import com.aissure.packet.packet.utils.Config;
import com.aissure.packet.packet.views.SplashView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class SplashController {
    private List<View> viewList = new ArrayList<>();
    private View view3;
    private Activity mContext;
    private SplashView viewInterface;

    public SplashController(Activity context, SplashView viewInterface) {
        mContext = context;
        this.viewInterface = viewInterface;
        addViewPager();
    }

    public void addViewPager() {
        ImageView view1 = new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        view1.setLayoutParams(layoutParams);
        view1.setImageResource(R.mipmap.spreash1);
        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView view2 = new ImageView(mContext);
        view2.setLayoutParams(layoutParams);
        view2.setImageResource(R.mipmap.spreash2);
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        LayoutInflater inflater = mContext.getLayoutInflater();
        view3 = inflater.inflate(R.layout.layout_splash3, null);
        final Button btnOpenNow = (Button) view3.findViewById(R.id.btn_splash_open_now);
        CheckBox cbAgree = (CheckBox) view3.findViewById(R.id.cb_splash_agree);
        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setButtonOpen(btnOpenNow, isChecked);
            }
        });


        final TextView tvProtocol = (TextView) view3.findViewById(R.id.tv_splash_protocol);
        tvProtocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvProtocol.getPaint().setAntiAlias(true);//抗锯齿
        tvProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, UserProtocolActivity.class));
            }
        });
        btnOpenNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MainActivity.class));
                Config.getConfig(mContext).setAgreement(true);
                mContext.finish();
            }
        });
        setButtonOpen(btnOpenNow, cbAgree.isChecked());
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        PagerAdapter pagerAdapter = new MyPagerAdapter(viewList);
        viewInterface.setViewPager(pagerAdapter, 0);
    }

    public void setButtonOpen(Button btnOpenNow, boolean ischecked) {
        btnOpenNow.setSelected(ischecked);
        btnOpenNow.setTextColor(ischecked ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.gray));
        btnOpenNow.setClickable(ischecked);
    }
}
