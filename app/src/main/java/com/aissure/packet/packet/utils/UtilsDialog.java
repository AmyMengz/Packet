package com.aissure.packet.packet.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
        public void onBack(EditText et);
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
        }
    }
}
