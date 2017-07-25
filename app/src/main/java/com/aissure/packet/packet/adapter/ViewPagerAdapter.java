package com.aissure.packet.packet.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class ViewPagerAdapter extends PagerAdapter {

    List<View> lists;

    public ViewPagerAdapter(Context context,List<View> lists){
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(View view, int position)                                //实例化Item
    {
        ((ViewPager) view).addView(lists.get(position), 0);
        return lists.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(lists.get(position));//删除页卡
    }



}
