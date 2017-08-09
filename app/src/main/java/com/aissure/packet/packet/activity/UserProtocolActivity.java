package com.aissure.packet.packet.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.aissure.packet.packet.R;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/7/26.
 */

@ContentViewInject(R.layout.activity_user_protocol)
public class UserProtocolActivity extends BaseActivity {
    @ViewInject(R.id.tv_protocol)
    TextView tvProtocol;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);
        initReact();
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
    public void initReact() {
        initActionBar();
        setBackLeft();
    }
}
