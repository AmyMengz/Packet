package com.aissure.packet.packet.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.utils.UtilsDialog;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.OnClick;
import com.mz.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/8/4.
 */
@ContentViewInject(R.layout.activity_mute_notify)
public class MuteNotificationActivity extends BaseActivity {


    @ViewInject(R.id.btn_mute_add)
    Button btnAdd;
    @ViewInject(R.id.btn_mute_ok)
    Button btnOk;

    @ViewInject(R.id.iv_mute_remove)
    ImageView ivRemove;

    @ViewInject(R.id.ll_mute_time_group)
    LinearLayout llMuteGroup;

    @ViewInject(R.id.item_time_origin)
    View itemTimeOrigin;

    @ViewInject(R.id.mute_time_start)
    TextView tvMuteTimeStart;
    @ViewInject(R.id.mute_time_end)
    TextView tvMuteTimeEnd;

    LayoutInflater inflater;
    String muteStart,muteEnd;
    List<HashMap<String,Object>> listMuteTimes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectAll(this);
        initReact();
        listMuteTimes = new ArrayList<>();
        inflater = getLayoutInflater();
        muteStart = getConfig().getMuteStart();
        muteEnd = getConfig().getMuteEnd();
        tvMuteTimeStart.setText(muteStart);
        tvMuteTimeEnd.setText(muteEnd);
    }

    @Override
    public void initReact() {
        initActionBar();
        setBackLeft();
        setTitle(getString(R.string.mute_title));
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
    @OnClick({R.id.btn_mute_add,R.id.btn_mute_ok,R.id.iv_mute_remove,R.id.mute_time_start,R.id.mute_time_end})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_mute_add:
                final View item = inflater.inflate(R.layout.item_mute_time_set,null);
                TextView start = (TextView)item.findViewById(R.id.mute_time_start);
                TextView end = (TextView)item.findViewById(R.id.mute_time_end);
                ImageView ivRemove = (ImageView)item.findViewById(R.id.iv_mute_remove);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                end.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ivRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llMuteGroup.removeView(item);
                    }
                });
                llMuteGroup.addView(item);
                break;
            case R.id.btn_mute_ok:

                break;
            case R.id.iv_mute_remove:
                llMuteGroup.removeView(itemTimeOrigin);
                break;
            case R.id.mute_time_start:
                muteStart = getConfig().getMuteStart();
                UtilsDialog.getUtilsDialog(MuteNotificationActivity.this).showTimePickerDialog(muteStart,new UtilsDialog.DialogOptListener() {
                    @Override
                    public void onBack(Object o) {
                        if(o instanceof String){
                            tvMuteTimeStart.setText(o.toString());
                            getConfig().setMuteStart(o.toString());
                            showToast(getString(R.string.set_ok));
                        }
                    }
                });
                break;
            case R.id.mute_time_end:
                muteEnd = getConfig().getMuteEnd();
                UtilsDialog.getUtilsDialog(MuteNotificationActivity.this).showTimePickerDialog(muteEnd,new UtilsDialog.DialogOptListener() {
                    @Override
                    public void onBack(Object o) {
                        if(o instanceof String){
                            tvMuteTimeEnd.setText(o.toString());
                            getConfig().setMuteEnd(o.toString());
                            showToast(getString(R.string.set_ok));
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UtilsDialog.getUtilsDialog(MuteNotificationActivity.this).disMissDialog();
    }
}
