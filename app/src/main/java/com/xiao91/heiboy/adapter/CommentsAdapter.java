package com.xiao91.heiboy.adapter;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiao91.heiboy.R;
import com.xiao91.heiboy.bean.CommentInfo;
import com.xiao91.heiboy.bean.CommentsSection;
import com.xiao91.heiboy.glide.GlideCircleImageTransform;
import com.xiao91.heiboy.utils.DateUtils;
import com.xiao91.heiboy.utils.TianGouUrls;

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
        CommentInfo commentInfo = item.t;
        // 用户头像
        Glide.with(mContext)
                .load(TianGouUrls.IMAGE_URL + commentInfo.userPhoto)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideCircleImageTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) helper.getView(R.id.item_comments_iv_userPhoto));

        helper.setText(R.id.item_comments_tv_username, commentInfo.username);
        helper.setText(R.id.item_comments_tv_content, Html.fromHtml(commentInfo.commentDetail));
        helper.setText(R.id.item_comments_tv_good, commentInfo.userGoodCount);

        String time = DateUtils.formatFriendly(DateUtils.getUnixDate(commentInfo.createTime));
        helper.setText(R.id.item_comments_tv_time, time);
    }
}
