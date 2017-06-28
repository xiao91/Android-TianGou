package com.quanmin.sky.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * 图片加载监听接口，适应屏幕大小
 *
 * @author xiao 2017-05-24
 */

public class GlideBitmapListener implements RequestListener<Drawable> {

    // 声明需要引用的控件
    private ImageView imageView;

    public GlideBitmapListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        // 图片显示模式
        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        // 获取imageView的布局参数
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        // 图片需要显示的宽度 = ImageView布局宽度-两边边距
        int pWidth = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
        // 缩放系数= 图片需要显示的宽度/从网络获取图片的宽度
        float scale = (float) pWidth / (float) resource.getIntrinsicWidth();
        int vHeight = Math.round(resource.getIntrinsicHeight() * scale);
        params.height = vHeight + imageView.getPaddingTop() + imageView.getPaddingBottom();
        imageView.setLayoutParams(params);
        return false;
    }
}

