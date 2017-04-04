package com.xiao91.sky.adapter;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiao91.sky.R;
import com.xiao91.sky.bean.CommentEntry;
import com.xiao91.sky.section.CommentsSection;
import com.xiao91.sky.utils.DateUtils;
import com.xiao91.sky.utils.TGUrls;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

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
        CommentEntry commentEntry = item.t;
        // 用户头像
        Glide.with(mContext)
                .load(TGUrls.IMAGE_URL + commentEntry.userPhoto)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) helper.getView(R.id.item_comments_iv_userPhoto));

        helper.setText(R.id.item_comments_tv_username, commentEntry.username);
        helper.setText(R.id.item_comments_tv_content, Html.fromHtml(commentEntry.commentDetail));
        helper.setText(R.id.item_comments_tv_good, commentEntry.userGoodCount);

        String time = DateUtils.formatFriendly(DateUtils.getUnixDate(commentEntry.createTime));
        helper.setText(R.id.item_comments_tv_time, time);
    }
}
