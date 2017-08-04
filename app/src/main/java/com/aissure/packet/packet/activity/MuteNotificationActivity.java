package com.aissure.packet.packet.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aissure.packet.packet.R;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;

/**
 * Created by Administrator on 2017/8/4.
 */
@ContentViewInject(R.layout.activity_mute_notify)
public class MuteNotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);
        initReact();
    }

    @Override
    public void initReact() {
        initActionBar();
        setBackLeft();
        setTitle(getString(R.string.mute_title));
    }
}
