package com.aissure.packet.packet.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/25.
 */

public class BaseActivity extends AppCompatActivity {

    Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

//        InjectUtils.injectAll(this);
    }

    public void showToast(final String tips) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast = toast == null ? Toast.makeText(BaseActivity.this, tips, Toast.LENGTH_SHORT) : toast;
                toast.setText(tips);
                toast.show();
            }
        });

    }
}
