package com.xl91.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 底部视图
 * Created by xiao on 2016.08.13.
 */
public class BottomDialog {

    // 动画属性
    private int mAnimStyle;
    private Dialog mDialog;
    private Context mContext;
    // 视图
    private View mConvertView;
    // 属性风格
    private int mThemeStyle;

    /**
     *
     * @param context    上下文
     * @param resId      布局资源id
     */
    public BottomDialog(Context context, int resId) {
        this.mContext = context;
        this.mConvertView = LayoutInflater.from(context).inflate(resId, null, false);

        // 从底部弹出动画
        mAnimStyle = R.style.BottomDialogAnimStyle;
        // 设置默认主题风格
        mThemeStyle = R.style.BottomDialogThemeDefault;
    }

    /**
     * 获取当前view，以便找到子控件
     *
     * @return view
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * dialog消失
     */
    public void dismissView() {
        if (null != mDialog)
            mDialog.dismiss();
    }

    /**
     * 显示dialog
     */
    public void showView() {
        // 添加属性
        mDialog = new Dialog(mContext, mThemeStyle);
        // 在dialog外是否可以点击
        mDialog.setCanceledOnTouchOutside(true);
        // 设置窗口没有标题
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // 设置视图
        mDialog.setContentView(mConvertView);

        // 获取当前窗口window
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // x轴与y轴位置
        layoutParams.x = 0;
        layoutParams.y =  window.getWindowManager().getDefaultDisplay().getHeight();

        // 设置布局参数
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // dialog自适应窗口
        mDialog.onWindowAttributesChanged(layoutParams);
        // 设置动画
        window.setWindowAnimations(mAnimStyle);
        // 显示dialog
        mDialog.show();
    }
}