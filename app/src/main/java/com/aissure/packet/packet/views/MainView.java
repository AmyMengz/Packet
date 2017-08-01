package com.aissure.packet.packet.views;

import android.support.v4.view.PagerAdapter;
import android.widget.SimpleAdapter;

/**
 * Created by Administrator on 2017/7/27.
 */

public interface MainView extends BaseView{
    public void setGridAdapter(SimpleAdapter adapter);

    public void setViewPagerAdapter(PagerAdapter adapter);

}
