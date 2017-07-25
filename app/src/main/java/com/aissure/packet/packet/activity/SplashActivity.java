package com.aissure.packet.packet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.adapter.ViewPagerAdapter;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
@ContentViewInject(R.layout.activity_splash)
public class SplashActivity extends Activity {

    @ViewInject(R.id.vp_splash_vp)
    ViewPager viewPager;

    ViewPagerAdapter mViewPagerAdapter;
    List<View> viewList = new ArrayList<>();
    private View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);

        ImageView view1 = new ImageView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        view1.setLayoutParams(layoutParams);
        view1.setImageResource(R.mipmap.spreash1);
        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                setScaleType(ImageView.ScaleType.CENTER)
//
        ImageView view2 = new ImageView(this);
        view2.setLayoutParams(layoutParams);
        view2.setImageResource(R.mipmap.spreash2);
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView view3 = new ImageView(this);
        view3.setLayoutParams(layoutParams);
        view3.setImageResource(R.mipmap.spreash3);
        view3.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Button button = new Button(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        // set 四周距离
//        params.setMargins(10, 10, 10, 10);
//
//        button.setLayoutParams(params);
////        button.setBackground(getResources().getDrawable(R.drawable.btn_press_selected));
//        button.setText("table");
//				button.setTextSize(getResources().getDimension(R.dimen.text_size_smaller));
//        button.setTextColor(getResources().getColor(Color.RED));
//        LayoutInflater inflater = getLayoutInflater();
//        view1 = inflater.inflate(R.layout.layout1, null);
//        view2 = inflater.inflate(R.layout.layout2, null);
//        view3 = inflater.inflate(R.layout.layout3, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));

                return viewList.get(position);
            }
        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(viewList.size()/2);

    }
}
