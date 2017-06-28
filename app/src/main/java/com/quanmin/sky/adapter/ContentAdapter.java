package com.quanmin.sky.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quanmin.sky.R;
import com.quanmin.sky.bean.ContentEntry;
import com.quanmin.sky.config.ConfigUrl;
import com.quanmin.sky.glide.GlideBitmapListener;
import com.quanmin.sky.listener.OnClickGridImageItemListener;
import com.quanmin.sky.listener.OnClickRecyclerItemListener;
import com.quanmin.sky.utils.GoodStateUtils;
import com.xl91.ui.MenuGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 内容item
 * <p>
 * Created by xiao on 2016-10-10.
 */

public class ContentAdapter extends BaseQuickAdapter<ContentEntry.ContentData.Content, BaseViewHolder> {

    private static final int TYPE_DUANZI = 1;
    private static final int TYPE_GAOXIAO = 2;
    private static final int TYPE_MEINV = 3;
    private static final int TYPE_GUIGUSHI = 4;
    private static final int TYPE_MANHUA = 5;
    private static final int TYPE_SHIPING = 6;

    private List<SparseBooleanArray> list = new ArrayList<>();

    private OnClickGridImageItemListener onClickGridImageItemListener;

    public ContentAdapter(int layoutResId, List<ContentEntry.ContentData.Content> data) {
        super(layoutResId, data);
        setGoodState(data);
    }

    /*
     * 键：0赞 1踩：点赞不能点踩，点踩不能点赞；默认没有点击；循环设置数据
     *
     * 添加、删除数据均要操作
     *
     */
    public void setGoodState(List<ContentEntry.ContentData.Content> data) {
        int size = data.size();
        for (int i = 0; i < size; i++) {
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            boolean goodState = GoodStateUtils.isGoodState(data.get(i));
            if (goodState) {
                sparseBooleanArray.put(0, true);
                sparseBooleanArray.put(1, false);
            } else {
                boolean badState = GoodStateUtils.isBadState(data.get(i));
                if (badState) {
                    sparseBooleanArray.put(0, false);
                    sparseBooleanArray.put(1, true);
                } else {
                    sparseBooleanArray.put(0, false);
                    sparseBooleanArray.put(1, false);
                }
            }

            list.add(i, sparseBooleanArray);
        }
    }

    /**
     * 设置点踩
     */
    public void setIsCheckedBad(int position) {
        list.get(position).put(1, true);
    }

    /**
     * 获取点踩
     */
    public boolean getIsCheckedBad(int position) {
        return list.get(position).get(1);
    }

    /**
     * 设置点赞
     */
    public void setIsCheckedGood(int position) {
        list.get(position).put(0, true);
    }

    /**
     * 获取点赞
     */
    public boolean getIsCheckedGood(int position) {
        return list.get(position).get(0);
    }

    public void setOnClickGridImageItemListener(OnClickGridImageItemListener onClickGridImageItemListener) {
        this.onClickGridImageItemListener = onClickGridImageItemListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentEntry.ContentData.Content item) {
        // 用户头像
        helper.addOnClickListener(R.id.item_contents_iv_user_photo);
        // 删除
        helper.addOnClickListener(R.id.item_contents_delete);
        // 段子
        helper.addOnClickListener(R.id.item_contents_tv_desc);
        // 搞笑图
        helper.addOnClickListener(R.id.item_contents_iv);
        // 赞、踩、评论、转发
        helper.addOnClickListener(R.id.item_contents_tv_good);
        helper.addOnClickListener(R.id.item_contents_tv_bad);
        helper.addOnClickListener(R.id.item_contents_tv_comment);
        helper.addOnClickListener(R.id.item_contents_tv_skip);

        int position = helper.getAdapterPosition() - getHeaderLayoutCount() - getFooterLayoutCount();

        int type = Integer.parseInt(item.contentType);

        RequestOptions options = new RequestOptions();
        options.circleCrop();
        // 用户头像
        Glide.with(mContext)
                .load(ConfigUrl.IMAGE_URL + item.userHead)
                .apply(options)
                .into((ImageView) helper.getView(R.id.item_contents_iv_user_photo));

        // 用户名
        helper.setText(R.id.item_contents_tv_user_name, item.username);
        // 标题
        helper.setText(R.id.item_contents_tv_title, item.contentTitle);

        View view = helper.itemView;

        // 九宫图
        MenuGridView contents_gv_img = (MenuGridView) view.findViewById(R.id.contents_gv_img);

        // 视频播放
        JCVideoPlayerStandard contents_videoplayer = (JCVideoPlayerStandard) view.findViewById(R.id.contents_videoplayer);

        switch (type) {
            /*
             * 1：段子
             *
             */
            case TYPE_DUANZI:
                // 图片、视频、九宫图隐藏
                helper.setVisible(R.id.item_contents_iv, false);
                contents_videoplayer.setVisibility(View.GONE);
                contents_gv_img.setVisibility(View.GONE);
                // 段子显示
                helper.setVisible(R.id.item_contents_tv_desc, true);

                // 类型
                helper.setText(R.id.item_contents_tv_type, "段子");

                String content_desc = item.contentDetail;
                if (!TextUtils.isEmpty(content_desc)) {
                    if (content_desc.contains("<br/>")) {
                        String desc = item.contentDetail.replace("<br/>", "");
                        helper.setText(R.id.item_contents_tv_desc, desc);
                    } else {
                        helper.setText(R.id.item_contents_tv_desc, content_desc);
                    }
                }
                break;
            /*
             * 2：搞笑
             *
             *
             */
            case TYPE_GAOXIAO:
                // 段子、视频、九宫图隐藏
                helper.setVisible(R.id.item_contents_tv_desc, false);
                contents_videoplayer.setVisibility(View.GONE);
                contents_gv_img.setVisibility(View.GONE);
                // 图片显示
                helper.setVisible(R.id.item_contents_iv, true);

                // 类型
                helper.setText(R.id.item_contents_tv_type, "内涵图");

                final ImageView item_contents_iv = helper.getView(R.id.item_contents_iv);
                // 内涵图片
                Glide.with(mContext)
                        .load(item.contentDetail)
                        .listener(new GlideBitmapListener(item_contents_iv))
                        .into(item_contents_iv);
                break;
            /*
             * 3：美女图：可能有多张图
             *
             *
             */
            case TYPE_MEINV:
                // 段子、搞笑图片、视频隐藏
                helper.setVisible(R.id.item_contents_tv_desc, false);
                helper.setVisible(R.id.item_contents_iv, false);
                contents_videoplayer.setVisibility(View.GONE);
                // 显示九宫图
                contents_gv_img.setVisibility(View.VISIBLE);

                // 类型
                helper.setText(R.id.item_contents_tv_type, "美女高清图");

                // 九宫图图片集合
                final ArrayList<String> meiNvUrls = new ArrayList<>();
                String img = item.contentDetail;
                if (img.contains(";")) {
                    String[] split = img.split(";");
                    Collections.addAll(meiNvUrls, split);
                } else {
                    meiNvUrls.add(img);
                }

                final GridImageAdapter adapter = new GridImageAdapter(mContext, meiNvUrls);
                contents_gv_img.setAdapter(adapter);

                /*
                 * 不能用gridview做点击事件，用adapter做嵌套点击
                 *
                 */
                adapter.setOnClickRecyclerItemListener(new OnClickRecyclerItemListener() {
                    @Override
                    public void onRecyclerViewItemClick(View view, int position) {
                        if (onClickGridImageItemListener != null) {
                            onClickGridImageItemListener.onClickGridImageItemListener(view, position, meiNvUrls);
                        }
                    }
                });

                break;
            /*
             * 4:鬼故事
             */
            case TYPE_GUIGUSHI:
                // 图片、九宫图、视频隐藏
                helper.setVisible(R.id.item_contents_iv, false);
                contents_videoplayer.setVisibility(View.GONE);
                contents_gv_img.setVisibility(View.GONE);

                helper.setVisible(R.id.item_contents_tv_desc, true);

                // 类型
                helper.setText(R.id.item_contents_tv_type, "故事汇");

                // 只显示描述：点击后可看见全部内容
                helper.setText(R.id.item_contents_tv_desc, item.contentDesc);
                break;
            /*
             * 5：漫画：可能有多张图，只显示一张
             *
             */
            case TYPE_MANHUA:
                // 段子、九宫图、视频隐藏
                helper.setVisible(R.id.item_contents_tv_desc, false);
                contents_gv_img.setVisibility(View.GONE);
                contents_videoplayer.setVisibility(View.GONE);
                // 显示图片
                helper.setVisible(R.id.item_contents_iv, true);

                // 类型
                helper.setText(R.id.item_contents_tv_type, "漫画");

                // 取第一张
                List<String> manhuaUrls = new ArrayList<>();
                String img1 = item.contentDetail;
                if (img1.contains(";")) {
                    String[] split = img1.split(";");
                    Collections.addAll(manhuaUrls, split);
                } else {
                    manhuaUrls.add(img1);
                }

                Glide.with(mContext)
                        .load(manhuaUrls.get(0))
                        .apply(new RequestOptions().centerCrop())
                        .into((ImageView) helper.getView(R.id.item_contents_iv));
                break;
            /*
             * 6：视频
             *
             */
            case TYPE_SHIPING:
                // 段子、九宫图、图片隐藏
                helper.setVisible(R.id.item_contents_tv_desc, false);
                contents_gv_img.setVisibility(View.GONE);
                helper.setVisible(R.id.item_contents_iv, false);

                contents_videoplayer.setVisibility(View.VISIBLE);

                // 类型
                helper.setText(R.id.item_contents_tv_type, "视频");

                contents_videoplayer.setUp(item.contentDetail, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

                // 添加缩略图
                Glide.with(mContext)
                        .load(item.contentDesc)
                        .apply(new RequestOptions().centerCrop())
                        .into(contents_videoplayer.thumbImageView);
                break;
            default:
                break;

        }

        Resources resources = mContext.getResources();

        // 踩
        TextView item_contents_tv_bad = helper.getView(R.id.item_contents_tv_bad);
        boolean isCheckedBad = getIsCheckedBad(position);
        if (isCheckedBad) {
            Drawable drawableBad = resources.getDrawable(R.mipmap.bad_blue);
            int blue = resources.getColor(R.color.color_good_bad_comment_skip);
            drawableBad.setBounds(0, 0, drawableBad.getMinimumWidth(), drawableBad.getMinimumHeight());
            item_contents_tv_bad.setCompoundDrawables(drawableBad, null, null, null);
            item_contents_tv_bad.setTextColor(blue);
        }else {
            Drawable drawableBad = resources.getDrawable(R.mipmap.bad);
            int blue = resources.getColor(R.color.color_pressed_good_bad_comment_skip);
            drawableBad.setBounds(0, 0, drawableBad.getMinimumWidth(), drawableBad.getMinimumHeight());
            item_contents_tv_bad.setCompoundDrawables(drawableBad, null, null, null);
            item_contents_tv_bad.setTextColor(blue);
        }
        item_contents_tv_bad.setText(item.badCount);

        // 点赞
        TextView item_contents_tv_good = helper.getView(R.id.item_contents_tv_good);
        boolean isCheckedGood = getIsCheckedGood(position);
        if (isCheckedGood) {
            Drawable drawable = resources.getDrawable(R.mipmap.good_blue);
            int blue = resources.getColor(R.color.color_good_bad_comment_skip);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            item_contents_tv_good.setCompoundDrawables(drawable, null, null, null);
            item_contents_tv_good.setTextColor(blue);
        }else {
            Drawable drawable = resources.getDrawable(R.mipmap.good);
            int blue = resources.getColor(R.color.color_pressed_good_bad_comment_skip);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            item_contents_tv_good.setCompoundDrawables(drawable, null, null, null);
            item_contents_tv_good.setTextColor(blue);
        }
        item_contents_tv_good.setText(item.goodCount);

        // 评价
        helper.setText(R.id.item_contents_tv_comment, item.commentCount);
        // 转发
        helper.setText(R.id.item_contents_tv_skip, item.shareCount);
    }

    /**
     * 设置点赞数据
     */
    public void setGoodTextView(int position) {
        // 更新该条数据
        String goodCount = getItem(position).goodCount;
        getItem(position).goodCount = String.valueOf((Integer.parseInt(goodCount) + 1));
        notifyItemChanged(position);
    }

    /**
     * 设置点踩数据
     */
    public void setBadTextView(int position) {
        // 更新数据
        String badCount = getItem(position).badCount;
        getItem(position).badCount = String.valueOf((Integer.parseInt(badCount) + 1));
        notifyItemChanged(position);
    }
}
