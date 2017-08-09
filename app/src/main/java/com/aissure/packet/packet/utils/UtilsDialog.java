package com.aissure.packet.packet.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.aissure.packet.packet.R;

/**
 * Created by Administrator on 2017/8/2.
 */

public class UtilsDialog {
    private static UtilsDialog instance;
    static Byte b = 0 ;

    AlertDialog dialog;
    LayoutInflater layoutInflater;
    Context mContext;

    private UtilsDialog(Context context) {
        mContext = context;

        layoutInflater = LayoutInflater.from(context);
    }

    public static UtilsDialog getUtilsDialog(Context context) {
        if (instance == null) {
            synchronized (b) {
                if (instance == null) {
                    instance = new UtilsDialog(context);
                }
            }
        }
        return instance;

    }

    public interface DialogOptListener {
        public void onBack(Object o);
    }

    /**
     * 带输入框的Dialog
     *
     * @param originalText
     * @param title
     * @param orTips
     * @param orsuf
     * @param listener
     */
    public AlertDialog showChangTextDialog(String originalText, String title, String orTips, String orsuf,final DialogOptListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View dialogView = layoutInflater.inflate(R.layout.dialog_chang_text, null);
        final EditText et = (EditText) dialogView
                .findViewById(R.id.et_script_or_id);
        TextView suf = (TextView) dialogView
                .findViewById(R.id.et_script_suffix);
        TextView tip = (TextView) dialogView.findViewById(R.id.script_tc_tips);
        suf.setText(orsuf);
        tip.setText(orTips);
        et.setText(originalText + "");
        et.setSelection(originalText.length());
        builder.setView(dialogView);
        builder.setTitle(title);
        builder.setPositiveButton(mContext.getString(R.string.dialog_ok),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener!=null)
                            listener.onBack(et);
                    }
                });
        builder.setNegativeButton(mContext.getString(R.string.dialog_cancle),
                null);
        dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public void disMissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 显示时间拾取dialog
     * @return
     */
    public AlertDialog showTimePickerDialog(String time,final DialogOptListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View dialogView = layoutInflater.inflate(R.layout.dialog_time_picker, null);
        int hour1 = 0,minute1 = 0;
        if (time.contains(":")){
            hour1 = Integer.valueOf(time.substring(0,time.indexOf(":")));
            minute1 = Integer.valueOf(time.substring(time.indexOf(":")+1,time.length()));
        }
        final NumberPicker hour = (NumberPicker)dialogView.findViewById(R.id.np_hour);
        hour.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                String tmpStr = String.valueOf(value);
                if (value < 10) {
                    tmpStr = "0" + tmpStr;
                }
                return tmpStr;
            }
        });
        hour.setMaxValue(23);
        hour.setMinValue(0);
        hour.setValue(hour1);
        final NumberPicker minute = (NumberPicker)dialogView.findViewById(R.id.np_minutes);
        minute.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                String tmpStr = String.valueOf(value);
                if (value < 10) {
                    tmpStr = "0" + tmpStr;
                }
                return tmpStr;
            }
        });
        minute.setMaxValue(59);
        minute.setMinValue(0);
        minute.setValue(minute1);
        builder.setNegativeButton(mContext.getString(R.string.dialog_cancle), null);
        builder.setPositiveButton(mContext.getString(R.string.dialog_ok),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hourValue = hour.getValue();
                        int minuteValue = minute.getValue();
                        String hours = hourValue<10?"0"+hourValue:hourValue+"";
                        String minutes = minuteValue<10?"0"+minuteValue:minuteValue+"";
                        if(listener!=null)
                            listener.onBack(hours+":"+minutes);
                    }
                });
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();
        return dialog;
    }
}
