package com.xiao91.sky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiao91.sky.R;
import com.xiao91.sky.bean.Share;

import java.util.List;

/**
 * 分享adapter
 * Created by xiao on 2017/1/14 0014.
 */

public class ShareAdapter extends BaseAdapter {

    private Context context;
    private List<Share> imgLists;
    private final LayoutInflater inflater;

    public ShareAdapter(Context context, List<Share> data) {
        this.context = context;
        this.imgLists = data;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgLists.size();
    }

    @Override
    public Object getItem(int i) {
        return imgLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = inflater.inflate(R.layout.item_dialog_share_gv, viewGroup, false);
        ImageView img = (ImageView) view1.findViewById(R.id.item_share_img);
        TextView item_share_title = (TextView) view1.findViewById(R.id.item_share_title);
        img.setImageResource(imgLists.get(i).resId);
        item_share_title.setText(imgLists.get(i).title);
        return view1;
    }
}
