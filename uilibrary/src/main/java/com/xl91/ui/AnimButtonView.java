package com.xl91.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xl91.ui.listener.OnFastClickListener;

/**
 * Created by xiao on 2017/4/8.
 */

public class AnimButtonView extends RelativeLayout implements View.OnClickListener {

    private OnClickButtonViewListener onClickButtonViewListener;
    private ImageView anim_iv_back;
    private ImageView anim_iv_finished;
    private ImageView anim_iv_tack;
    private float dp100;

    public void setOnClickButtonViewListener(OnClickButtonViewListener onClickButtonViewListener) {
        this.onClickButtonViewListener = onClickButtonViewListener;
    }

    public AnimButtonView(Context context) {
        super(context);

        init(context);
    }

    public AnimButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public AnimButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    float lastValue;

    private void init(Context context) {
        dp100 = getResources().getDimension(R.dimen.dp_100);

        // 填充视图
        LayoutInflater.from(context).inflate(R.layout.anim_bottom_view, this, true);

        anim_iv_back = (ImageView) findViewById(R.id.anim_iv_back);
        anim_iv_finished = (ImageView) findViewById(R.id.anim_iv_finished);
        anim_iv_tack = (ImageView) findViewById(R.id.anim_iv_tack);

        anim_iv_back.setOnClickListener(this);
        anim_iv_finished.setOnClickListener(this);

        // 防止过快点击，否则Camera没有释放会报错
        anim_iv_tack.setOnClickListener(new OnFastClickListener() {
            @Override
            protected void onFastClick(View v) {
                // 回调点击事件
                if (null != onClickButtonViewListener)
                    onClickButtonViewListener.onTackClick();
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.anim_iv_back) {
            anim_iv_tack.setVisibility(View.VISIBLE);
            anim_iv_finished.setVisibility(View.GONE);
            anim_iv_back.setVisibility(View.GONE);
            if (null != onClickButtonViewListener)
                onClickButtonViewListener.onCancelClick();
        } else if (id == R.id.anim_iv_finished) {
            anim_iv_tack.setVisibility(View.VISIBLE);
            anim_iv_finished.setVisibility(View.GONE);
            anim_iv_back.setVisibility(View.GONE);

            if (null != onClickButtonViewListener)
                onClickButtonViewListener.onFinishedClick();
        }
    }

    /**
     * 开始动画，拍完照后即可保存
     */
    public void startAnim() {
        anim_iv_tack.setVisibility(View.GONE);
        anim_iv_finished.setVisibility(View.VISIBLE);
        anim_iv_back.setVisibility(View.VISIBLE);
        // 属性动画
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, dp100).setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float dis = value - lastValue;
                anim_iv_back.setX(anim_iv_back.getX() - dis);
                anim_iv_finished.setX(anim_iv_finished.getX() + dis);
                lastValue = value;
            }
        });
        valueAnimator.start();
    }

    public interface OnClickButtonViewListener {
        void onTackClick();

        void onCancelClick();

        void onFinishedClick();
    }

    /**
     * 重置view的状态
     */
    public void resetViewVisible() {
        anim_iv_tack.setVisibility(View.GONE);
        anim_iv_finished.setVisibility(View.VISIBLE);
        anim_iv_back.setVisibility(View.VISIBLE);
    }
}
