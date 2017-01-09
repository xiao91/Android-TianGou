package com.xl91.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 向下滑出的PopupWindow
 * Created by xiao on 2017/1/8 0008.
 */

public class DownPopupWindow {

    private View convertView;

    public DownPopupWindow(Context context, int resId) {
        this.convertView = LayoutInflater.from(context).inflate(resId, null, false);

        initPopup();
    }

    private void initPopup() {
        int width = convertView.getWidth();
        int height = convertView.getHeight();
        // 创建PopupWindow实例
        final PopupWindow popupwindow = new PopupWindow(convertView, width, height);
        // 设置动画效果
        popupwindow.setAnimationStyle(R.style.PopupWindowAnimStyle);

        // 自定义view添加触摸事件
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow.isShowing()) {
                    popupwindow.dismiss();
                }
                return false;
            }
        });
    }

    public View getConvertView() {
        return convertView;
    }

}
