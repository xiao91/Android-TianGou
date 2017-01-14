package com.xiao91.heiboy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiao91.heiboy.R;
import com.xiao91.heiboy.impl.OnClickRecyclerItemListener;

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
            viewHolder.item_tv_count = (TextView) view.findViewById(R.id.item_tv_count);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(context)
                .load(imgUrls.get(position))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.item_iv_img);


        if (position == MAX_COUNT) {
            viewHolder.item_tv_count.setVisibility(View.VISIBLE);
            viewHolder.item_tv_count.setText("+ " + (maxCount - 9));
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

    public class ViewHolder {
        ImageView item_iv_img;
        TextView item_tv_count;
    }

}
