package com.xl91.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xl91.ui.R;

/**
 * 自定义的dialog，比较通用
 *
 * @author xiao 2017-05-26
 */

public class CommonDialog {

    // 上下文
    private Context context;

    private OnDialogClickListener onDialogClickListener;
    private AlertDialog alertDialog;

    // 设置监听
    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public CommonDialog(Context context) {
        this.context = context;
    }

    /**
     * 显示内容的对话框
     */
    public void showDialog(String... content) {
        // 创建dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // 填充视图View
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null, false);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(content[0]))
            tv_content.setText(content[0]);

        TextView tv_order_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_order_ok = (TextView) view.findViewById(R.id.tv_ok);

        tv_order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDialogClickListener) {
                    onDialogClickListener.onCancelClick();
                }

                if (null != alertDialog) {
                    alertDialog.dismiss();
                }
            }
        });

        tv_order_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDialogClickListener) {
                    onDialogClickListener.onOkClick();
                }

                if (null != alertDialog) {
                    alertDialog.dismiss();
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.show();
    }

    interface OnDialogClickListener {
        /**
         * 取消回调
         */
        void onCancelClick();

        /**
         * 确认回调
         */
        void onOkClick();
    }
}
