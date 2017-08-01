package com.aissure.packet.packet.controller;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.adapter.MyPagerAdapter;
import com.aissure.packet.packet.views.MainView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MainController {
    private List<View> viewList = new ArrayList<>();
    Activity mContext;
    MainView viewInterface;
    public static final String ITEM_IMAGE = "ItemImage";
    public static final String ITEM_TEXT = "ItemText";
    ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
    public MainController(Activity context, MainView viewInterface){
        this.mContext = context;
        this.viewInterface = viewInterface;
        initGrid();
        initViewPager();
    }

    private void initGrid() {
        HashMap<String,Object> map1 = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        HashMap<String,Object> map3 = new HashMap<>();
        HashMap<String,Object> map4 = new HashMap<>();
        HashMap<String,Object> map5 = new HashMap<>();
        HashMap<String,Object> map6 = new HashMap<>();
        map1.put(ITEM_IMAGE,R.drawable.icon_main_member);
        map1.put(ITEM_TEXT,"抢红包");
        map2.put(ITEM_IMAGE,R.drawable.icon_main_mine);
        map2.put(ITEM_TEXT,"抢红包2");
        map3.put(ITEM_IMAGE,R.drawable.icon_main_one);
        map3.put(ITEM_TEXT,"抢红包3");
        map4.put(ITEM_IMAGE,R.drawable.icon_main_package);
        map4.put(ITEM_TEXT,"抢红包");
        map5.put(ITEM_IMAGE,R.drawable.icon_main_robmoney);
        map5.put(ITEM_TEXT,"抢红包2");
        map6.put(ITEM_IMAGE,R.drawable.icon_main_ronflow);
        map6.put(ITEM_TEXT,"抢红包3");
        lists.add(map1);
        lists.add(map2);
        lists.add(map3);
        lists.add(map4);
        lists.add(map5);
        lists.add(map6);
        SimpleAdapter simpleAdapter = new SimpleAdapter(mContext,lists,
                R.layout.item_image_text,
                new String[]{ITEM_IMAGE,ITEM_TEXT},
                new int[]{R.id.item_iv,R.id.item_tv});
        viewInterface.setGridAdapter(simpleAdapter);

    }

    public void initViewPager(){
        ImageView view1 = new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view1.setLayoutParams(layoutParams);
        view1.setImageResource(R.drawable.ad);
        view1.setScaleType(ImageView.ScaleType.FIT_END);
        ImageView view2 = new ImageView(mContext);
        view2.setLayoutParams(layoutParams);
        view2.setImageResource(R.drawable.ad);
        view2.setScaleType(ImageView.ScaleType.FIT_END);

        viewList.add(view1);
        viewList.add(view2);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(viewList);

        viewInterface.setViewPagerAdapter(pagerAdapter);
    }
}
