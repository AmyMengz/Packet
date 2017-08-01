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

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */

public class SettingAdapter extends  BaseAdapter{
    List<HashMap<String,Object>> mList;
    LayoutInflater inflater;
    public SettingAdapter(Activity context, List<HashMap<String,Object>> lists){
        this.mList = lists;
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
        holder.tvTips.setText(mList.get(position).get(SettingsController.KEY_TEXT)+"");
        Object littleTip = mList.get(position).get(SettingsController.KEY_LITTLE_TEXT);
        if(littleTip!=null&&!TextUtils.isEmpty(littleTip+"")){
            holder.tvLittleTips.setVisibility(View.VISIBLE);
            holder.tvLittleTips.setText(littleTip+"");
        }else {
            holder.tvLittleTips.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tvTips;
        TextView tvLittleTips;
        Button optBtn;
        ToggleButton toggleButton;
    }
}
