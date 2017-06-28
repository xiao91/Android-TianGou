package com.quanmin.sky.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.quanmin.sky.R;
import com.xl91.ui.photo.PhotoView;

import java.util.List;

/**
 * 图片滑动
 * Created by xiao on 2017/1/12 0012.
 */

public class MultiViewPagerAdapter extends PagerAdapter {

    private List<String> imgLists;
    private Context context;

    public MultiViewPagerAdapter(Context context, List<String> imgLists) {
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
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        viewpager_progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        viewpager_progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(pv_view_pager);

        // 把view添加到活动页
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
