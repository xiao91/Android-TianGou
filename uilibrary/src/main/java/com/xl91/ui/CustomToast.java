package com.xl91.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义Toast
 * Created by xiao on 2017/1/11 0011.
 */

public class CustomToast {

    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;

    public static void show(Context context, String title, int duration) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.custom_toast_title);
        tvTitle.setText(title);

        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0,  PixelUtils.formatDipToPx(context, 32));
        toast.setView(view);
        toast.show();
    }

}
