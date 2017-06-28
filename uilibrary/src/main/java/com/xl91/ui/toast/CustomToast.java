package com.xl91.ui.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xl91.ui.R;
import com.xl91.ui.utils.PixelUtils;

/**
 * 自定义view在屏幕中间弹出的Toast
 *
 * @author xiao 2017-01-11
 *
 */

public class CustomToast {

    public static void showCenterToast(Context context, String title) {
        Toast toast = new Toast(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_toast, null, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.custom_toast_title);
        tvTitle.setText(title);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0,  PixelUtils.formatDipToPx(context, 32));
        toast.setView(view);
        toast.show();
    }
}
