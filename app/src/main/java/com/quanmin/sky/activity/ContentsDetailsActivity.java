package com.quanmin.sky.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.quanmin.sky.R;
import com.quanmin.sky.activity.base.BaseMvpActivity;
import com.quanmin.sky.adapter.CommentsAdapter;
import com.quanmin.sky.adapter.GridImageAdapter;
import com.quanmin.sky.bean.AddCommentEntry;
import com.quanmin.sky.bean.CommentEntry;
import com.quanmin.sky.bean.ContentEntry;
import com.quanmin.sky.bean.FocusEntry;
import com.quanmin.sky.config.ConfigUrl;
import com.quanmin.sky.domain.ShareDomain;
import com.quanmin.sky.glide.GlideBitmapListener;
import com.quanmin.sky.mvp.presenter.ContentsDetailPresenter;
import com.quanmin.sky.mvp.view.ContentsDetailView;
import com.quanmin.sky.section.CommentsSection;
import com.quanmin.sky.utils.DeviceUtils;
import com.quanmin.sky.view.CustomLoadMoreView;
import com.xl91.ui.FillTextView;
import com.xl91.ui.MenuGridView;
import com.xl91.ui.dialog.BottomDialog;
import com.xl91.ui.flypopup.FlyPopupWindow;
import com.xl91.ui.toast.CustomToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 内容详情
 * <p>
 * 2017-01-11
 */
public class ContentsDetailsActivity extends BaseMvpActivity<ContentsDetailView, ContentsDetailPresenter>
        implements ContentsDetailView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    private static final String TYPE_DUANZI = "1";
    private static final String TYPE_GAOXIAO = "2";
    private static final String TYPE_MEINV = "3";
    private static final String TYPE_GUIGUSHI = "4";
    private static final String TYPE_MANHUA = "5";
    private static final String TYPE_SHIPING = "6";

    @BindView(R.id.details_tv_comment_edit)
    TextView detailsTvCommentEdit;
    @BindView(R.id.recyclerView)
    RecyclerView includeRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout includeSwipe;

    // 跳转传递的数据
    private ContentEntry.ContentData.Content contents;

    // 是否有过点赞
    private boolean isClickGood;
    // 是否有过点踩
    private boolean isClickBad;
    // 是否是评论
    private boolean isComment = false;

    private CommentsAdapter commentsAdapter;

    private ArrayList<String> meiNvOrManHuaUrls = new ArrayList<>();

    private BottomDialog bottomDialog;

    // 是否有评论数据
    private boolean hasCommentData = false;

    // HeaderView的父LinearLayout和动态添加TextView，显示没有评论
    private LinearLayout contentTypeLl;
    private TextView tvChildEmptyComment;

    // 输入法
    private InputMethodManager inputManager;

    /**
     * dialog中评论
     */
    private TextView dialog_tv_send;
    private EditText dialog_et_comment;

    /**
     * 点赞、点踩、评论、分享列表
     */
    private TextView itemContentsTvGood;
    private TextView itemContentsTvBad;
    private TextView itemContentsTvComment;
    private TextView itemContentsTvSkip;

    // 是否关注
    private boolean isFocus = false;

    private FocusEntry focusEntry;
    private TextView tvFocus;

    private List<CommentsSection> headSectionList;
    private List<CommentEntry.CommentData.CommentDetail> hotComment;

    @Override
    protected ContentsDetailPresenter createPresenter() {
        return new ContentsDetailPresenter(this);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_content_detail;
    }

    @Override
    protected void iniView(View baseView) {
        setToolbarTitle(true, "详情");
        setToolbarNavigation(true);

        // 加载进度条开始到结束，4种变换颜色
        includeSwipe.setProgressViewOffset(false, 0, 100);
        includeSwipe.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4);
        // 下拉刷新监听
        includeSwipe.setOnRefreshListener(this);

        includeRv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        includeRv.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contents = bundle.getParcelable("content");
            isComment = bundle.getBoolean("isClickComment");
            isClickGood = bundle.getBoolean("isGood");
            isClickBad = bundle.getBoolean("isBad");

            // 请求数据
            mPresenter.requestComments(contents.userId, contents.contentId);
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        // 刷新时延迟2秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ContentsDetailsActivity.this, "正在完善中...", Toast.LENGTH_SHORT).show();

                includeSwipe.setRefreshing(false);
            }
        }, 2000);
    }

    /**
     * 显示数据
     * @param data 对应bean数据
     */
    @Override
    public void onShowData(CommentEntry data) {
        hotComment = data.data.hotComment;
        List<CommentEntry.CommentData.CommentDetail> newComment = data.data.newComment;

        /*
         * 当没有最新数据时，显示没有评论提示
         */
        hasCommentData = !(newComment.isEmpty() || hotComment.isEmpty());

        // 添加头部分类
        headSectionList = new ArrayList<>();

        int hotCount = hotComment.size();
        if (hotCount != 0) {
            headSectionList.add(new CommentsSection(true, "热门评论", true));
            for (int i = 0; i < hotCount; i++) {
                headSectionList.add(new CommentsSection(hotComment.get(i)));
            }
        }

        int newCount = newComment.size();
        if (newCount != 0) {
            headSectionList.add(new CommentsSection(true, "最新评论", true));
            for (int j = 0; j < newCount; j++) {
                headSectionList.add(new CommentsSection(newComment.get(j)));
            }
        }

        commentsAdapter = new CommentsAdapter(R.layout.item_comments_content, R.layout.item_comments_head, headSectionList);
        commentsAdapter.setHeaderView(initHeadView(data));

        // 加载更多待完善
        commentsAdapter.setEnableLoadMore(true);
        commentsAdapter.setLoadMoreView(new CustomLoadMoreView());
        commentsAdapter.setOnLoadMoreListener(this, includeRv);

        includeRv.setAdapter(commentsAdapter);

        /*
         * 点击评论数据：回复评论：未做
         *
         */
        includeRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(ContentsDetailsActivity.this, "点击了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show();
            }
        });

        commentsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                initBottomDialog(position);
            }
        });

        // 如果首页跳转过来的是评论滑动到最底部
        if (isComment) {
            commentsAdapter.loadMoreEnd(true);

            includeRv.scrollToPosition(hotCount + newCount);
        }
    }

    /**
     * 请求数据显示错误
     * @param code 错误码
     */
    @Override
    public void onShowError(int code) {
        showToastError(code);
    }

    /**
     * 显示更多数据
     * @param comments 评论
     */
    @Override
    public void onShowMoreData(CommentEntry comments) {
        commentsAdapter.loadMoreComplete();
        List<CommentEntry.CommentData.CommentDetail> newComment = comments.data.newComment;
        if (newComment.isEmpty()) {
            commentsAdapter.loadMoreEnd(false);
        } else {
            // 先添加到集合中，添加到adapter中
            int size = newComment.size();
            for (int j = 0; j < size; j++) {
                headSectionList.add(new CommentsSection(newComment.get(j)));
            }

            commentsAdapter.addData(headSectionList);
        }
    }

    /**
     * 显示进度条
     */
    @Override
    public void onShowProgressbar() {
        includeSwipe.setRefreshing(true);
    }

    /**
     * 隐藏进度条
     */
    @Override
    public void onHideProgressbar() {
        includeSwipe.setRefreshing(false);
    }

    /**
     * 填充HeadView数据
     *
     * @param commentEntry 评论
     */
    private View initHeadView(CommentEntry commentEntry) {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_rv_contents_details, null, false);

        ImageView detailsIvUserPhoto = (ImageView) headerView.findViewById(R.id.details_iv_user_photo);
        TextView detailsTvUserName = (TextView) headerView.findViewById(R.id.details_tv_user_name);
        TextView detailsTvUserDesc = (TextView) headerView.findViewById(R.id.details_tv_user_desc);
        tvFocus = (TextView) headerView.findViewById(R.id.tv_focus);

        TextView itemContentsTvType = (TextView) headerView.findViewById(R.id.item_contents_tv_type);

        ImageView itemContentsIv = (ImageView) headerView.findViewById(R.id.item_contents_iv);
        FillTextView itemContentsTvDesc = (FillTextView) headerView.findViewById(R.id.item_contents_tv_desc);
        MenuGridView contentsGvImg = (MenuGridView) headerView.findViewById(R.id.contents_gv_img);
        JCVideoPlayerStandard contentsVideoplayer = (JCVideoPlayerStandard) headerView.findViewById(R.id.contents_videoplayer);

        contentTypeLl = (LinearLayout) headerView.findViewById(R.id.content_type_ll);
        itemContentsTvGood = (TextView) headerView.findViewById(R.id.item_contents_tv_good);
        itemContentsTvBad = (TextView) headerView.findViewById(R.id.item_contents_tv_bad);
        itemContentsTvComment = (TextView) headerView.findViewById(R.id.item_contents_tv_comment);
        itemContentsTvSkip = (TextView) headerView.findViewById(R.id.item_contents_tv_skip);

        detailsIvUserPhoto.setOnClickListener(this);
        detailsTvUserName.setOnClickListener(this);
        detailsTvUserDesc.setOnClickListener(this);
        tvFocus.setOnClickListener(this);
        itemContentsIv.setOnClickListener(this);

        itemContentsTvGood.setOnClickListener(this);
        itemContentsTvBad.setOnClickListener(this);
        itemContentsTvComment.setOnClickListener(this);
        itemContentsTvSkip.setOnClickListener(this);

        if (headSectionList.isEmpty()) {
            tvChildEmptyComment = new TextView(this);
            tvChildEmptyComment.setText("暂无评论>>>");
            tvChildEmptyComment.setTextColor(getResources().getColor(R.color.dialog_no));
            tvChildEmptyComment.setTextSize(14.0f);
            tvChildEmptyComment.setPadding(10, 30, 10, 20);
            tvChildEmptyComment.setGravity(LinearLayout.VERTICAL);

            contentTypeLl.addView(tvChildEmptyComment);
        }

        // 用户头像
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(this)
                .load(ConfigUrl.IMAGE_URL + contents.userHead)
                .apply(options)
                .into(detailsIvUserPhoto);

        // 用户名称
        detailsTvUserName.setText(contents.username);

        // 用户发布作品数量
        String worksCount = commentEntry.data.worksCount;
        String followersCount = commentEntry.data.followersCount;
        String countDesc = worksCount + "作品 " + followersCount + "粉丝";
        detailsTvUserDesc.setText(countDesc);

        // 类型
        switch (contents.contentType) {
            case TYPE_DUANZI:
                itemContentsTvType.setText("段子");
                // 图片、视频、隐藏
                itemContentsIv.setVisibility(View.GONE);
                contentsVideoplayer.setVisibility(View.GONE);
                contentsGvImg.setVisibility(View.GONE);
                itemContentsTvDesc.setVisibility(View.VISIBLE);

                String content_desc = contents.contentDetail;
                if (!TextUtils.isEmpty(content_desc)) {
                    if (content_desc.contains("<br/>")) {
                        String desc = contents.contentDetail.replace("<br/>", "");
                        itemContentsTvDesc.setText(desc);
                    } else {
                        itemContentsTvDesc.setText(content_desc);
                    }
                }
                break;
            case TYPE_GAOXIAO:
                itemContentsTvType.setText("内涵图");

                // 段子、视频、隐藏
                contentsVideoplayer.setVisibility(View.GONE);
                itemContentsTvDesc.setVisibility(View.GONE);
                contentsGvImg.setVisibility(View.GONE);
                itemContentsIv.setVisibility(View.VISIBLE);

                // 添加到list中，以便传递
                meiNvOrManHuaUrls.add(contents.contentDetail);

                Glide.with(this)
                        .load(contents.contentDetail)
                        .listener(new GlideBitmapListener(itemContentsIv))
                        .into(itemContentsIv);
                break;
            case TYPE_GUIGUSHI:
                itemContentsTvType.setText("故事汇");
                // 图片、视频、隐藏
                itemContentsIv.setVisibility(View.GONE);
                contentsVideoplayer.setVisibility(View.GONE);
                contentsGvImg.setVisibility(View.GONE);
                itemContentsTvDesc.setVisibility(View.VISIBLE);

                // 显示全部内容
                String imgUrlOrContent = contents.contentDetail;
                String content = imgUrlOrContent.replace("\\n", "\n");
                itemContentsTvDesc.setText(content);
                break;
            case TYPE_SHIPING:
                itemContentsTvType.setText("视频");

                // 图片、段子、隐藏
                itemContentsIv.setVisibility(View.GONE);
                itemContentsTvDesc.setVisibility(View.GONE);
                contentsGvImg.setVisibility(View.GONE);
                contentsVideoplayer.setVisibility(View.VISIBLE);

                contentsVideoplayer.setUp(contents.contentDetail, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                // 添加缩略图
                Glide.with(this)
                        .load(contents.contentDesc)
                        .apply(new RequestOptions().centerCrop())
                        .into(contentsVideoplayer.thumbImageView);

                break;
            case TYPE_MEINV:
                itemContentsTvType.setText("美女高清图");

                itemContentsIv.setVisibility(View.GONE);
                itemContentsTvDesc.setVisibility(View.GONE);
                contentsVideoplayer.setVisibility(View.GONE);
                contentsGvImg.setVisibility(View.VISIBLE);

                String img = contents.contentDetail;
                if (img.contains(";")) {
                    String[] split = img.split(";");
                    Collections.addAll(meiNvOrManHuaUrls, split);
                } else {
                    meiNvOrManHuaUrls.add(img);
                }

                final GridImageAdapter adapter = new GridImageAdapter(this, meiNvOrManHuaUrls);
                contentsGvImg.setAdapter(adapter);

                contentsGvImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(ContentsDetailsActivity.this, MultiImageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("imgList", meiNvOrManHuaUrls);
                        bundle.putInt("position", i);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                break;
            case TYPE_MANHUA:
                itemContentsTvType.setText("漫画");

                // 段子、视频、隐藏
                contentsVideoplayer.setVisibility(View.GONE);
                itemContentsTvDesc.setVisibility(View.GONE);
                contentsGvImg.setVisibility(View.GONE);
                itemContentsIv.setVisibility(View.VISIBLE);

                // 取第一张
                String img1 = contents.contentDetail;
                if (img1.contains(";")) {
                    String[] split = img1.split(";");
                    Collections.addAll(meiNvOrManHuaUrls, split);
                } else {
                    meiNvOrManHuaUrls.add(img1);
                }

                Glide.with(this)
                        .load(meiNvOrManHuaUrls.get(0))
                        .apply(new RequestOptions().centerCrop())
                        .into(itemContentsIv);
                break;
            default:
                break;
        }

        // 点踩
        if (isClickBad) {
            Drawable drawableBad = getResources().getDrawable(R.mipmap.bad_blue);
            int blue = getResources().getColor(R.color.color_good_bad_comment_skip);
            drawableBad.setBounds(0, 0, drawableBad.getMinimumWidth(), drawableBad.getMinimumHeight());
            itemContentsTvBad.setCompoundDrawables(drawableBad, null, null, null);
            itemContentsTvBad.setTextColor(blue);
        }else {
            Drawable drawableBad = getResources().getDrawable(R.mipmap.bad);
            int blue = getResources().getColor(R.color.color_pressed_good_bad_comment_skip);
            drawableBad.setBounds(0, 0, drawableBad.getMinimumWidth(), drawableBad.getMinimumHeight());
            itemContentsTvBad.setCompoundDrawables(drawableBad, null, null, null);
            itemContentsTvBad.setTextColor(blue);
        }
        itemContentsTvBad.setText(contents.badCount);

        // 点赞
        if (isClickGood) {
            Drawable drawable = getResources().getDrawable(R.mipmap.good_blue);
            int blue = getResources().getColor(R.color.color_good_bad_comment_skip);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            itemContentsTvGood.setCompoundDrawables(drawable, null, null, null);
            itemContentsTvGood.setTextColor(blue);
        }else {
            Drawable drawable = getResources().getDrawable(R.mipmap.good);
            int blue = getResources().getColor(R.color.color_pressed_good_bad_comment_skip);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            itemContentsTvGood.setCompoundDrawables(drawable, null, null, null);
            itemContentsTvGood.setTextColor(blue);
        }
        itemContentsTvGood.setText(contents.goodCount);

        // 评论、转发数量
        itemContentsTvComment.setText(contents.commentCount);
        itemContentsTvSkip.setText(contents.shareCount);

        // 请求关注的数据
        if (!TextUtils.isEmpty(userId)) {
            mPresenter.requestGetFocus(userId, contents.userId);
        }

        return headerView;
    }

    /**
     * 请求更多数据
     */
    @Override
    public void onLoadMoreRequested() {
        String contentsId = contents.contentId;
        // 如果list数量大于2个，说明有数据；这2个是title;
        if (headSectionList.size() > 2) {
            CommentEntry.CommentData.CommentDetail commentDetail = commentsAdapter.getItem((headSectionList.size() - 1)).t;
            String createTime = commentDetail.createTime;
            // 请求更多最新评论
            mPresenter.requestMoreComments(contentsId, createTime);
        } else {
            // 取消加载更多View
            if (commentsAdapter.isLoadMoreEnable()) {
                commentsAdapter.loadMoreEnd(true);
            }
        }
    }

    /**
     * 添加关注成功
     *
     * @param focusEntry 添加关注
     */
    @Override
    public void onShowAddFocus(FocusEntry focusEntry) {
        if (focusEntry.data.code == 1) {
            this.focusEntry = focusEntry;
            tvFocus.setText("√ 已关注");
            isFocus = true;
            Toast.makeText(this, "添加关注成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消关注
     * @param code 1取消关注成功，-1取消关注失败
     *
     */
    @Override
    public void onShowCancelFocus(Integer code) {
        if (code == 1) {
            tvFocus.setText("+ 关注");
            tvFocus.setTextColor(getResources().getColor(R.color.root_title));
            isFocus = false;
            Toast.makeText(this, "取消关注成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 评论成功
     *
     * @param addCommentEntry 添加评论
     */
    @Override
    public void onShowAddComment(AddCommentEntry addCommentEntry) {
        /*
         * 无数据时，不显示最热，只显示最新评论：自己添加的评论
         */
        if (hasCommentData) {
            int newPosition = hotComment.size() + 2;
            commentsAdapter.addData(newPosition, new CommentsSection(addCommentEntry.data));
        } else {
            headSectionList.add(new CommentsSection(true, "最新评论", true));
            headSectionList.add(new CommentsSection(addCommentEntry.data));

            contentTypeLl.removeView(tvChildEmptyComment);
        }

        Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 评论失败
     */
    @Override
    public void onShowAddCommentError() {
        Toast.makeText(this, "评论失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点赞
     * @param code 提示码，0:没有更新，1：更新成功，-1：更新失败
     */
    @Override
    public void onShowUpdateGoodCount(Integer code) {
        if (code == 1) {
            // 更新本地数据
            String count = (Integer.parseInt(contents.goodCount) + 1) + "";
            itemContentsTvGood.setText(count);

            // 设置点击后的颜色变化
            Resources resources = getResources();
            Drawable drawable = resources.getDrawable(R.mipmap.good_blue);
            int blue = resources.getColor(R.color.color_good_bad_comment_skip);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            itemContentsTvGood.setCompoundDrawables(drawable, null, null, null);
            itemContentsTvGood.setTextColor(blue);

            // 添加+1动画
            FlyPopupWindow flyPopupWindow = new FlyPopupWindow(this);
            flyPopupWindow.setTextInfo("+ 1", blue, 15);
            flyPopupWindow.show(itemContentsTvGood);

            // 设置当前为true
            isClickGood = true;
        }
    }

    /**
     * 点踩
     * @param code 提示码，0:没有更新，1：更新成功，-1：更新失败
     */
    @Override
    public void onShowUpdateBadCount(Integer code) {
        if (code == 1) {
            // 更新该条数据
            String count = Integer.parseInt(contents.badCount) + 1 + "";
            // 更新本地数据
            itemContentsTvBad.setText(count);

            Resources resources = getResources();
            Drawable drawableBad = resources.getDrawable(R.mipmap.bad_blue);
            int blue = resources.getColor(R.color.color_good_bad_comment_skip);
            drawableBad.setBounds(0, 0, drawableBad.getMinimumWidth(), drawableBad.getMinimumHeight());
            itemContentsTvBad.setCompoundDrawables(drawableBad, null, null, null);
            itemContentsTvBad.setTextColor(blue);

            // 添加+1动画：可加到成功后回调
            FlyPopupWindow flyPopupWindow = new FlyPopupWindow(this);
            flyPopupWindow.setTextInfo("+ 1", blue, 15);
            flyPopupWindow.show(itemContentsTvBad);

            isClickBad = true;
        }
    }

    /**
     * 获取关注的信息
     * @param focusEntry 关注
     */
    @Override
    public void onShowGetFocus(FocusEntry focusEntry) {
        if (focusEntry.data.code == 1) {
            this.focusEntry = focusEntry;
            if (tvFocus != null) {
                tvFocus.setText("√ 已关注");
                isFocus = true;
            }
        }
    }

    /**
     * 点击控件
     *
     * @param view 控件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_iv_user_photo:
            case R.id.details_tv_user_name:
            case R.id.details_tv_user_desc:
                Intent intent = new Intent(this, UserCenterActivity.class);
                intent.putExtra("userId", contents.userId);
                startActivity(intent);
                break;
            /*
             * 点击关注和取消关注
             *
             */
            case R.id.tv_focus:
                if (!TextUtils.isEmpty(userId)) {
                    if (isFocus) {
                        mPresenter.requestCancelFocus(focusEntry.data.focusId);
                    } else {
                        mPresenter.requestAddFocus(userId, contents.userId);
                    }
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            /*
             * 图片
             *
             */
            case R.id.item_contents_iv:
                intent = new Intent(ContentsDetailsActivity.this, MultiImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imgList", meiNvOrManHuaUrls);
                bundle.putInt("position", 0);
                bundle.putString("title", contents.contentTitle);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            /*
             * 赞
             */
            case R.id.item_contents_tv_good:
                // 判断当前是否踩过:被踩过，不能点赞
                if (!isClickBad) {
                    // 该条内容没有点赞过才能点赞
                    if (!isClickGood) {
                        // 更新服务器数据
                        String deviceId = DeviceUtils.getDeviceId();
                        mPresenter.requestUpdateGoodCount(contents.countId, userId, deviceId);

                    } else {
                        String title = getResources().getString(R.string.count_clicked);
                        CustomToast.showCenterToast(this, title);
                    }
                }
                break;
            /*
             * 踩
             */
            case R.id.item_contents_tv_bad:
                if (!isClickGood) {
                    if (!isClickBad) {
                        // 更新服务器数据
                        String deviceId = DeviceUtils.getDeviceId();
                        mPresenter.requestUpdateBadCount(contents.countId, userId, deviceId);
                    } else {
                        String title = getResources().getString(R.string.count_clicked);
                        CustomToast.showCenterToast(this, title);
                    }
                }
                break;
            /*
             * 评论
             */
            case R.id.item_contents_tv_comment:
                // TODO 未处理第三方输入法的表情输入
                initBottomDialog(0);
                break;
            /*
             * 转发
             */
            case R.id.item_contents_tv_skip:
                ShareDomain shareDomain = new ShareDomain(this);
                shareDomain.startShare();
                break;
            default:
                break;
        }
    }

    /**
     * 显示底部dialog，可以编辑评论
     * @param position
     */
    private String fromUid = "";
    private void initBottomDialog(int position) {
        bottomDialog = new BottomDialog(this, R.layout.dialog_bottom_send_comment);
        View convertView = bottomDialog.getConvertView();
        dialog_et_comment = (EditText) convertView.findViewById(R.id.dialog_et_comment);
        dialog_tv_send = (TextView) convertView.findViewById(R.id.dialog_tv_send);

        // 回复谁的评论
        if (position != 0) {
            if (commentsAdapter != null) {
                CommentsSection item = commentsAdapter.getItem(position);
                if (item != null) {
                    fromUid = item.t.fromUid;
                    String userHead = item.t.userHead;
                    dialog_et_comment.setText("回复@" + userHead + "&nbsp;&nbsp;");
                }
            }
        }

        bottomDialog.showDialog();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 设置可获得焦点
                dialog_et_comment.setFocusable(true);
                dialog_et_comment.setFocusableInTouchMode(true);
                // 请求获得焦点
                dialog_et_comment.requestFocus();

                // 调用系统输入法
                inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(dialog_et_comment, 0);

            }
        }, 300);

        dialog_et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    dialog_tv_send.setBackground(getResources().getDrawable(R.drawable.blue_tv_bg_corner));
                    dialog_tv_send.setClickable(true);
                } else {
                    dialog_tv_send.setBackground(getResources().getDrawable(R.drawable.gray01_tv_bg_corner));
                    dialog_tv_send.setClickable(false);
                }
            }
        });

        dialog_tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismissDialog();
                inputManager.hideSoftInputFromWindow(dialog_et_comment.getWindowToken(), 0);

                String content = dialog_et_comment.getText().toString();
                if (!TextUtils.isEmpty(userId)) {
                    mPresenter.requestAddComment(contents.contentId, content, userId, fromUid);
                }else {
                    Intent intent = new Intent(ContentsDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bottomDialog != null) {
            bottomDialog.dismissDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_complaints) {
            Toast.makeText(this, R.string.complaints, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.details_tv_comment_edit)
    public void onViewClicked() {
        // TODO 未处理第三方输入法的表情输入
        initBottomDialog(0);
    }
}
