package com.xiao91.heiboy.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiao91.heiboy.R;
import com.xiao91.heiboy.bean.Contents;
import com.xiao91.heiboy.glide.GlideCircleImageTransform;
import com.xiao91.heiboy.impl.OnClickGridImageItemListener;
import com.xiao91.heiboy.utils.UrlUtil;
import com.xl91.ui.MenuGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 分组评论
 * Created by xiao on 16-10-10.
 */

public class ContentsItemAdapter extends BaseQuickAdapter<Contents.ContentInfo, BaseViewHolder> {

    private static final int TYPE_TUIJIAN = 0;
    private static final int TYPE_DUANZI = 1;
    private static final int TYPE_GAOXIAO = 2;
    private static final int TYPE_MEINV = 3;
    private static final int TYPE_GUIGUSHI = 4;
    private static final int TYPE_MANHUA = 5;
    private static final int TYPE_SHIPING = 6;

    private OnClickGridImageItemListener onClickGridImageItemListener;

    public ContentsItemAdapter(int layoutResId, List<Contents.ContentInfo> data) {
        super(layoutResId, data);
    }

    public void setOnClickGridImageItemListener(OnClickGridImageItemListener onClickGridImageItemListener) {
        this.onClickGridImageItemListener = onClickGridImageItemListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, Contents.ContentInfo item) {
        final int adapterPosition = helper.getLayoutPosition() - getHeaderLayoutCount();

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

        int type = Integer.parseInt(item.type);

        // 用户头像
        Glide.with(mContext)
                .load(UrlUtil.IMAGE_URL + item.user_photo)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideCircleImageTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) helper.getView(R.id.item_contents_iv_user_photo));

        // 用户名
        helper.setText(R.id.item_contents_tv_user_name, item.user_name);
        // 标题
        helper.setText(R.id.item_contents_tv_title, item.title);

        View view = helper.getConvertView();

        // 九宫图
        MenuGridView contents_gv_img = (MenuGridView) view.findViewById(R.id.contents_gv_img);

        // 找到视频播放空间
        JCVideoPlayerStandard contents_videoplayer = (JCVideoPlayerStandard) view.findViewById(R.id.contents_videoplayer);

        switch (type) {
            /**
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

                String content_desc = item.content_desc;
                if (content_desc.contains("<br/>")) {
                    String desc = item.content_desc.replace("<br/>", "");
                    helper.setText(R.id.item_contents_tv_desc, desc);
                }else {
                    helper.setText(R.id.item_contents_tv_desc, content_desc);
                }
                break;
            /**
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

                // 内容图片
                Glide.with(mContext)
                        .load(item.img_url)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into((ImageView) helper.getView(R.id.item_contents_iv));
                break;
            /**
             * 3：美女图：可能有多张图
             *
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

                List<String> imgUrls = new ArrayList<>();
                String img = item.img_url;
                if (img.contains(";")) {
                    imgUrls = Arrays.asList(img.split(";"));
                }else {
                    imgUrls.add("img");
                }

                final GridImageAdapter adapter = new GridImageAdapter(mContext, imgUrls);
                contents_gv_img.setAdapter(adapter);

                contents_gv_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (null != onClickGridImageItemListener) {
                            onClickGridImageItemListener.onRecyclerViewItemClick(view, adapterPosition, i);
                        }
                    }
                });
                break;
            /**
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
                helper.setText(R.id.item_contents_tv_desc, item.content_desc);
                break;
            /**
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
                List<String> imgUrls1 = new ArrayList<>();
                String img1= item.img_url;
                if (img1.contains(";")) {
                    imgUrls1 = Arrays.asList(img1.split(";"));
                }else {
                    imgUrls1.add(img1);
                }

                Glide.with(mContext)
                        .load(imgUrls1.get(0))
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into((ImageView) helper.getView(R.id.item_contents_iv));
                break;
            /**
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

                contents_videoplayer.setUp(item.img_url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                // 添加缩略图
                Glide.with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(contents_videoplayer.thumbImageView);
                break;
           default:
               break;

        }

        // 点赞
        helper.setText(R.id.item_contents_tv_good, item.stamp_count);
        // 踩
        helper.setText(R.id.item_contents_tv_bad, item.praise_count);
        // 评价
        helper.setText(R.id.item_contents_tv_comment, item.comment_count);
        // 转发
        helper.setText(R.id.item_contents_tv_skip, item.retrans_count);
    }
}
