package com.quanmin.sky.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quanmin.sky.R;
import com.quanmin.sky.listener.OnClickRecyclerItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 美女、漫画图片
 * Created by xiao on 2016/9/13.
 */
public class GridImageAdapter extends BaseAdapter {

    private static final int MAX_COUNT = 8;

    private Context context;
    private List<String> imgUrls = new ArrayList<>();
    private final LayoutInflater inflater;

    private OnClickRecyclerItemListener onClickRecyclerItemListener;

    private int maxCount;

    public GridImageAdapter(Context context, List<String> imgs) {
        this.context = context;
        this.maxCount = imgs.size();

        inflater = LayoutInflater.from(context);
        // 取前8个
        if (maxCount > MAX_COUNT) {
            for (int i = 0; i < 9; i++) {
                imgUrls.add(imgs.get(i));
            }
        } else {
            this.imgUrls = imgs;
        }

    }

    public void setOnClickRecyclerItemListener(OnClickRecyclerItemListener onClickRecyclerItemListener) {
        this.onClickRecyclerItemListener = onClickRecyclerItemListener;
    }

    @Override
    public int getCount() {
        return imgUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imgUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_gride_image, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_iv_img = (ImageView) view.findViewById(R.id.item_iv_img);
            viewHolder.rl_grid_img = (RelativeLayout) view.findViewById(R.id.rl_grid_img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(context)
                .load(imgUrls.get(position))
                .apply(options)
                .into(viewHolder.item_iv_img);

        if (position == MAX_COUNT) {
            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            viewHolder.item_iv_img.measure(w, h);
            int height = viewHolder.item_iv_img.getMeasuredHeight();
//            int width = viewHolder.item_iv_img.getMeasuredWidth();

            TextView textView = new TextView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(Color.parseColor("#66000000"));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(24);
            textView.setTextColor(Color.WHITE);
            textView.setText("+ " + (maxCount - 9));

            viewHolder.rl_grid_img.addView(textView);
        }

        viewHolder.item_iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickRecyclerItemListener != null) {
                    onClickRecyclerItemListener.onRecyclerViewItemClick(view, position);
                }
            }
        });

        return view;
    }

    private class ViewHolder {
        RelativeLayout rl_grid_img;
        ImageView item_iv_img;
    }

}
