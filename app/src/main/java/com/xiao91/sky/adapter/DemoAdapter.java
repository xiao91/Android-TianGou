package com.xiao91.sky.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiao91.sky.R;

import java.util.List;

/**
 * Created by hefangzhou on 2017/4/4.
 */

public class DemoAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    public DemoAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_joke, item);
    }
}
