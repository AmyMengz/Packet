package com.aissure.packet.packet.activity;

import android.os.Bundle;

import com.aissure.packet.packet.R;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;

@ContentViewInject(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);
//        setContentView(R.layout.activity_main);
//        PermissionUtil.checkAccessibility(this);
    }
}
