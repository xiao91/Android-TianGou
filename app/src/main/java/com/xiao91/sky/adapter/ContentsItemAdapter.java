package com.xiao91.sky.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiao91.sky.R;
import com.xiao91.sky.bean.Contents;
import com.xiao91.sky.listener.OnClickGridImageItemListener;
import com.xiao91.sky.listener.OnClickRecyclerItemListener;
import com.xiao91.sky.utils.TGUrls;
import com.xl91.ui.MenuGridView;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 内容item
 *
 * Created by xiao on 16-10-10.
 */

public class ContentsItemAdapter extends BaseQuickAdapter<Contents.Data.ContentsInfo, BaseViewHolder> {

    private static final int TYPE_TUIJIAN = 0;
    private static final int TYPE_DUANZI = 1;
    private static final int TYPE_GAOXIAO = 2;
    private static final int TYPE_MEINV = 3;
    private static final int TYPE_GUIGUSHI = 4;
    private static final int TYPE_MANHUA = 5;
    private static final int TYPE_SHIPING = 6;

    private OnClickGridImageItemListener onClickGridImageItemListener;

    public ContentsItemAdapter(int layoutResId, List<Contents.Data.ContentsInfo> data) {
        super(layoutResId, data);
    }

    public void setOnClickGridImageItemListener(OnClickGridImageItemListener onClickGridImageItemListener) {
        this.onClickGridImageItemListener = onClickGridImageItemListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, Contents.Data.ContentsInfo item) {
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
                .load(TGUrls.IMAGE_URL + item.userPhoto)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) helper.getView(R.id.item_contents_iv_user_photo));

        // 用户名
        helper.setText(R.id.item_contents_tv_user_name, item.username);
        // 标题
        helper.setText(R.id.item_contents_tv_title, item.title);

        View view = helper.getConvertView();

        // 九宫图
        MenuGridView contents_gv_img = (MenuGridView) view.findViewById(R.id.contents_gv_img);

        // 视频播放
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

                String content_desc = item.contentDescribe;
                if (content_desc.contains("<br/>")) {
                    String desc = item.contentDescribe.replace("<br/>", "");
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
                        .load(item.content)
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

                // 九宫图图片集合
                final ArrayList<String> meiNvUrls = new ArrayList<>();
                String img = item.content;
                if (img.contains(";")) {
                    String[] split = img.split(";");
                    for (String aSplit : split) {
                        meiNvUrls.add(aSplit);
                    }
                }else {
                    meiNvUrls.add(img);
                }

                final GridImageAdapter adapter = new GridImageAdapter(mContext, meiNvUrls);
                contents_gv_img.setAdapter(adapter);

                /**
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
                helper.setText(R.id.item_contents_tv_desc, item.contentDescribe);
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
                List<String> manhuaUrls = new ArrayList<>();
                String img1 = item.content;
                if (img1.contains(";")) {
                    String[] split = img1.split(";");
                    for (String aSplit : split) {
                        manhuaUrls.add(aSplit);
                    }
                }else {
                    manhuaUrls.add(img1);
                }

                Glide.with(mContext)
                        .load(manhuaUrls.get(0))
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

                contents_videoplayer.setUp(item.content, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
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
        helper.setText(R.id.item_contents_tv_good, item.goodCount);
        // 踩
        helper.setText(R.id.item_contents_tv_bad, item.badCount);
        // 评价
        helper.setText(R.id.item_contents_tv_comment, item.commentCount);
        // 转发
        helper.setText(R.id.item_contents_tv_skip, item.shareCount);
    }
}
