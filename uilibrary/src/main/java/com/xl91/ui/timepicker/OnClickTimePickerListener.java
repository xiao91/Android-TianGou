package com.xl91.ui.timepicker;

import android.view.View;

/**
 * 点击时间选择按钮的接口
 * Created by XL on 2016.08.23.
 */
public interface OnClickTimePickerListener {

    /**
     * 点击item控件
     * @param view
     * @param date
     */
    void onClickTimePicker(View view, String date, long unixTime);
}
