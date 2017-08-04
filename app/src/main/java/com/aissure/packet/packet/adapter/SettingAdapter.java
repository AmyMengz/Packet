package com.aissure.packet.packet.adapter;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.aissure.packet.packet.R;
import com.aissure.packet.packet.controller.SettingsController;
import com.aissure.packet.packet.utils.PermissionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/29.
 */

public class SettingAdapter extends  BaseAdapter{
    List<HashMap<String,Object>> mList;
    LayoutInflater inflater;
    Activity mContext;
    public SettingAdapter(Activity context, List<HashMap<String,Object>> lists){
        this.mList = lists;
        mContext = context;
        inflater = context.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String,Object> map = mList.get(position);
        ViewHolder holder = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_setting,null);
            holder = new ViewHolder();
            holder.tvTips = (TextView)convertView.findViewById(R.id.tv_title_tips);
            holder.tvLittleTips = (TextView) convertView.findViewById(R.id.tv_little_tips);
            holder.toggleButton = (ToggleButton)convertView.findViewById(R.id.tb_switch);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvTips.setText(map.get(SettingsController.KEY_TEXT)+"");
        Object littleTip = map.get(SettingsController.KEY_LITTLE_TEXT);
        if(littleTip!=null&&!TextUtils.isEmpty(littleTip+"")){
            holder.tvLittleTips.setVisibility(View.VISIBLE);
            holder.tvLittleTips.setText(littleTip+"");
        }else {
            holder.tvLittleTips.setVisibility(View.GONE);
        }
        int id = (Integer)map.get(SettingsController.KEY_ID);
        switch (id){
            case SettingsController.ID_OPEN_ACCESSIBILITY:
                holder.toggleButton.setVisibility(View.VISIBLE);
                boolean isOn = PermissionUtil.isAccessibilitySettingsOn(mContext);
                holder.toggleButton.setChecked(isOn);
                break;
            case SettingsController.ID_SET_DELEY_TIME:
                holder.toggleButton.setVisibility(View.INVISIBLE);
                break;
            case SettingsController.ID_SET_STABILITY:
                holder.toggleButton.setVisibility(View.INVISIBLE);
                break;
            case SettingsController.ID_SET_MUTE_NOTIFICATION:
                holder.toggleButton.setVisibility(View.VISIBLE);
                break;
        }
        return convertView;
    }



    public class ViewHolder{
        public TextView tvTips;
        public TextView tvLittleTips;
        public Button optBtn;
        public ToggleButton toggleButton;
    }
}
