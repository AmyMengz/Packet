package com.aissure.packet.packet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.utils.Config;
import com.aissure.packet.packet.utils.StringUtil;

/**
 * Created by Administrator on 2017/7/25.
 */

public abstract class BaseActivity extends AppCompatActivity {

    Toast toast;

    ViewGroup viewGroup;
    ImageView leftImage,rightImage;
    TextView leftTv,topTv,rightTv;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        InjectUtils.injectAll(this);
    }

    public abstract void initReact();
    public void initActionBar(){
        viewGroup = (ViewGroup)findViewById(R.id.action_bar);
        leftImage = (ImageView)findViewById(R.id.left_image);
        rightImage = (ImageView)findViewById(R.id.right_image);
        leftTv = (TextView) findViewById(R.id.left_title);
        topTv = (TextView)findViewById(R.id.top_title);
        rightTv = (TextView)findViewById(R.id.right_title);
    }
    public void setTitle(String title){
        topTv.setVisibility(View.VISIBLE);
        topTv.setText(title);
    }
    public void setBackLeft(){
        leftImage.setVisibility(View.VISIBLE);
        leftImage.setImageResource(R.drawable.back1);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    public void showErrorToast(final String tips){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                toast = toast == null ? Toast.makeText(BaseActivity.this, tips, Toast.LENGTH_SHORT) : toast;
//                toast.setText(tips);
//                toast.show();
                toast = toast==null?Toast.makeText(BaseActivity.this, "", Toast.LENGTH_SHORT):toast;
                CharSequence charSequence = StringUtil.SpanColor(StringUtil.SpanSize(tips, 35), Color.RED);
                toast.setText(charSequence);
                toast.show();
            }
        });
    }
    public Config getConfig(){
        return Config.getConfig(this);
    }
}
