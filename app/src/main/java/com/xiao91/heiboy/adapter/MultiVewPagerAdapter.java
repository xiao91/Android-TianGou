package com.xiao91.heiboy.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.xiao91.heiboy.R;
import com.xl91.ui.photo.PhotoView;

import java.util.List;

/**
 * 图片滑动
 * Created by xiao on 2017/1/12 0012.
 */

public class MultiVewPagerAdapter extends PagerAdapter {

    private List<String> imgLists;
    private Context context;

    public MultiVewPagerAdapter(Context context, List<String> imgLists) {
        this.imgLists = imgLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vp_image, container, false);
        PhotoView pv_view_pager = (PhotoView) view.findViewById(R.id.viewpager_PhotoView);
        // 设置可以缩放
        pv_view_pager.enable();

        final ProgressBar viewpager_progressBar = (ProgressBar) view.findViewById(R.id.viewpager_progressBar);

        // 设置图片
        Glide.with(context)
                .load(imgLists.get(position))
                .fitCenter()
                // 设置缓存策略
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                // 出错图
                .error(R.mipmap.ic_launcher)
                .into(new GlideDrawableImageViewTarget(pv_view_pager){

                    /**
                     * 加载完成
                     * @param resource
                     * @param animation
                     */
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);

                        viewpager_progressBar.setVisibility(View.GONE);
                    }

                    /**
                     * 加载失败
                     * @param e
                     * @param errorDrawable
                     */
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);

                        viewpager_progressBar.setVisibility(View.GONE);
                    }

                });

        // 把view添加到活动页
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
