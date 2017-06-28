package com.quanmin.sky.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quanmin.sky.R;
import com.quanmin.sky.bean.CommentEntry;
import com.quanmin.sky.config.ConfigUrl;
import com.quanmin.sky.section.CommentsSection;
import com.quanmin.sky.utils.DateUtils;

import java.util.List;

/**
 * 评论列表
 * Created by xiao on 2017/1/11 0011.
 */

public class CommentsAdapter extends BaseSectionQuickAdapter<CommentsSection, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public CommentsAdapter(int layoutResId, int sectionHeadResId, List<CommentsSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    /**
     * head头部
     * @param helper
     * @param item
     */
    @Override
    protected void convertHead(BaseViewHolder helper, CommentsSection item) {
        helper.setText(R.id.item_comments_tv_title, item.header);
        helper.setVisible(R.id.item_comments_tv_more, item.isMore());
        helper.addOnClickListener(R.id.item_comments_tv_more);

    }

    /**
     * content内容
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, CommentsSection item) {
        CommentEntry.CommentData.CommentDetail commentDetail = item.t;
        // 用户头像
        Glide.with(mContext)
                .load(ConfigUrl.IMAGE_URL + commentDetail.userHead)
                .apply(new RequestOptions().circleCrop())
                .into((ImageView) helper.getView(R.id.item_comments_iv_userPhoto));

        helper.setText(R.id.item_comments_tv_username, commentDetail.username);
        helper.setText(R.id.item_comments_tv_content, commentDetail.commentDetail);
        helper.setText(R.id.item_comments_tv_good, commentDetail.goodCount);

        String time = DateUtils.formatDate(DateUtils.getUnixTime(commentDetail.createTime));
        helper.setText(R.id.item_comments_tv_time, time);
    }
}
