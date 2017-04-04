package com.xl91.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 正在加载提示+无网络提示+空数据提示+加载失败提示
 * Created by XL on 2016/9/29.
 * 参考网上
 *
 */
public class LoadingLayout extends FrameLayout {

    private Context context;

    /**
     * 正在加载
     */
    private LinearLayout mLinearLoading;
    // 正在加载的文字提示
    private TextView mTvLoading;

    /**
     * 加载完成
     */
    private LinearLayout mLinearLoad;
    // 图片提示
    private ImageView mIvLoad;
    // 文字提示
    private TextView mTvLoad;
    // 按钮
    private TextView mBtnLoad;

    // 点击按钮回调接口
    private OnRetryListener mOnRetryListener;

    // 加载状态
    private LoadingState mState;

    public LoadingLayout(Context context) {
        super(context);

        this.context = context;
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
    }

    /**
     * 加载的状态
     */
    public enum LoadingState {
        // 状态分别为：加载中，数据为空，无网络，错误
        STATE_LOADING, STATE_EMPTY, STATE_NO_NET, STATE_ERROR
    }

    /**
     * 设置点击接口
     *
     * @param mOnRetryListener
     */
    public LoadingLayout setOnRetryListener(OnRetryListener mOnRetryListener) {
        this.mOnRetryListener = mOnRetryListener;
        return this;
    }

    public void build() {
        // 填充视图
        LayoutInflater.from(context).inflate(R.layout.loadinglayout_view, this, true);

        /**
         * 正在加载
         */
        mLinearLoading = (LinearLayout) findViewById(R.id.lin_loading);
        // 正在加载的进度条
        ProgressBar ll_loading_progressBar = (ProgressBar) findViewById(R.id.ll_loading_progressBar);
        // 正在加载的文字提示
        mTvLoading = (TextView) findViewById(R.id.tv_loading);

        /**
         * 加载完成
         */
        mLinearLoad = (LinearLayout) findViewById(R.id.lin_loaded);
        // 加载完成的图片显示
        mIvLoad = (ImageView) findViewById(R.id.iv_loaded);
        // 加载完成的文字提示
        mTvLoad = (TextView) findViewById(R.id.tv_loaded);
        // 加载完成的按钮（网络出错时，显示）
        mBtnLoad = (TextView) findViewById(R.id.btn_loaded);

        mBtnLoad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(LoadingState.STATE_LOADING);
                mOnRetryListener.onRetryClick();
            }
        });
    }

    /**
     * 加载中提示文字和图片提示
     */
    private String mLoadingText;

    /**
     * 1.设置正在加载的文字提示
     * @param loadingText
     * @return
     */
    public LoadingLayout setLoadingText(String loadingText) {
        this.mLoadingText = loadingText;
        return this;
    }

    /**
     * 设置加载的状态
     *
     * @param state
     */
    public void setState(LoadingState state) {
        if (mState == state) {
            return;
        } else if (state == LoadingState.STATE_LOADING) {
            mLinearLoading.setVisibility(VISIBLE);
            mLinearLoad.setVisibility(GONE);
        } else if (state != LoadingState.STATE_LOADING) {
            mLinearLoading.setVisibility(GONE);
            mLinearLoad.setVisibility(VISIBLE);
        }
        changeState(state);
    }

    /**
     * 改变状态
     *
     * @param state
     */
    private void changeState(LoadingState state) {
        switch (state) {
            // 加载中
            case STATE_LOADING:
                mState = LoadingState.STATE_LOADING;
                mTvLoading.setText(mLoadingText);
                break;
            // 数据为空
            case STATE_EMPTY:
                mState = LoadingState.STATE_EMPTY;
                mIvLoad.setImageResource(mLoadEmptyIcon);
                mTvLoad.setText(mLoadEmptyText);
                if (btnEmptyEnable) {
                    mBtnLoad.setVisibility(VISIBLE);
                    mBtnLoad.setText(btn_empty_text);
                } else {
                    mBtnLoad.setVisibility(GONE);
                }
                break;
            // 加载失败
            case STATE_ERROR:
                mState = LoadingState.STATE_ERROR;
                mIvLoad.setImageResource(mErrorIco);
                mTvLoad.setText(mLoadErrorText);
                if (btnErrorEnable) {
                    mBtnLoad.setVisibility(VISIBLE);
                    mBtnLoad.setText(btn_error_text);
                } else {
                    mBtnLoad.setVisibility(GONE);
                }
                break;
            // 无网络
            case STATE_NO_NET:
                mState = LoadingState.STATE_NO_NET;
                mIvLoad.setImageResource(mNoNetworkIcon);
                mTvLoad.setText(mLoadNoNetworkText);
                if (btnNoNetworkEnable) {
                    mBtnLoad.setVisibility(VISIBLE);
                    mBtnLoad.setText(btn_nonet_text);
                } else {
                    mBtnLoad.setVisibility(GONE);
                }
                break;
        }

    }

    /**
     * 加载数据为空提示文字
     */
    private String mLoadEmptyText;
    private int mLoadEmptyIcon;

    public LoadingLayout setLoadEmptyIconResources(int resId) {
        mLoadEmptyIcon = resId;
        return this;
    }

    /**
     * 无网络提示
     */
    private String mLoadNoNetworkText;
    private int mNoNetworkIcon;

    public LoadingLayout setNoNetIconResources(int resId) {
        mNoNetworkIcon = resId;
        return this;
    }

    /**
     * 设置数据为空时、数据加载出错、无网络时是否可见
     */
    private boolean btnEmptyEnable = true;
    private boolean btnErrorEnable = true;
    private boolean btnNoNetworkEnable = true;

    public LoadingLayout setBtnNoNetEnable(boolean enable) {
        btnNoNetworkEnable = enable;
        return this;
    }

    public LoadingLayout setBtnErrorEnable(boolean enable) {
        btnErrorEnable = enable;
        return this;
    }

    public LoadingLayout setBtnEmptyEnable(boolean enable) {
        btnEmptyEnable = enable;
        return this;
    }


    /**
     * 后台或者本地出现错误提示
     */
    private String mLoadErrorText;
    private int mErrorIco;

    public LoadingLayout setErrorIconResources(int resId) {
        mErrorIco = resId;
        return this;
    }

    /**
     * 加载空数据
     * @param loadEmptyText
     * @return
     */
    public LoadingLayout setLoadEmptyText(String loadEmptyText) {
        this.mLoadEmptyText = loadEmptyText;
        return this;
    }

    /**
     *  4.无网络时候加载文字
     * @param loadNoNetworkText
     * @return
     */
    public LoadingLayout setLoadNoNetworkText(String loadNoNetworkText) {
        mLoadNoNetworkText = loadNoNetworkText;
        return this;
    }

    private String btn_empty_text = "重试";
    private String btn_error_text = "重试";
    private String btn_nonet_text = "重试";
    /**
     * 数据为空的Button的文字提示
     * @param text
     * @return
     */
    public LoadingLayout setBtnEmptyText(String text) {
        this.btn_empty_text = text;
        return this;
    }

    public LoadingLayout setBtnEmptyTextResources(int resId) {
        this.btn_empty_text = getResources().getString(resId);
        return this;
    }

    /**
     * 加载错误的Button的文字提示
     * @param text
     * @return
     */
    public LoadingLayout setBtnErrorText(String text) {
        this.btn_error_text = text;
        return this;
    }

    /**
     * 3.加载错误的文字提示
     * @param loadedErrorText
     * @return
     */
    public LoadingLayout setLoadErrorText(String loadedErrorText) {
        this.mLoadErrorText = loadedErrorText;
        return this;
    }

    /**
     * 2.加载无网络的Button的文字提示
     * @param text
     * @return
     */
    public LoadingLayout setBtnNoNetText(String text) {
        this.btn_nonet_text = text;
        return this;
    }


    /**
     * 点击按钮接口回调
     */
    public interface OnRetryListener {
        void onRetryClick();
    }
}
