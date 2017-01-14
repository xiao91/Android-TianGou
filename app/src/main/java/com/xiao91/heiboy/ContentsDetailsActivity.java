package com.xiao91.heiboy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiao91.heiboy.adapter.CommentsAdapter;
import com.xiao91.heiboy.adapter.GridImageAdapter;
import com.xiao91.heiboy.bean.CommentInfo;
import com.xiao91.heiboy.bean.Comments;
import com.xiao91.heiboy.bean.CommentsSection;
import com.xiao91.heiboy.bean.Contents;
import com.xiao91.heiboy.bean.ContentsDetail;
import com.xiao91.heiboy.bean.FaBuComment;
import com.xiao91.heiboy.bean.GoodOrBadCount;
import com.xiao91.heiboy.domain.ShareDomain;
import com.xiao91.heiboy.glide.GlideCircleImageTransform;
import com.xiao91.heiboy.mvp_p.ContentsDetailPresenter;
import com.xiao91.heiboy.mvp_v.ContentsDetailView;
import com.xiao91.heiboy.utils.SharedUtils;
import com.xiao91.heiboy.utils.TianGouUrls;
import com.xiao91.heiboy.view.CustomLoadMoreView;
import com.xl91.ui.BottomDialog;
import com.xl91.ui.CustomToast;
import com.xl91.ui.FillTextView;
import com.xl91.ui.MenuGridView;
import com.xl91.ui.flypopup.FlyPopupWindow;
import com.xl91.ui.rv_divider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 内容详情
 * <p>
 * 2017-01-11
 */
public class ContentsDetailsActivity extends MVPAbsActivity<ContentsDetailView, ContentsDetailPresenter>
        implements ContentsDetailView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    private static final String TYPE_TUIJIAN = "0";
    private static final String TYPE_DUANZI = "1";
    private static final String TYPE_GAOXIAO = "2";
    private static final String TYPE_MEINV = "3";
    private static final String TYPE_GUIGUSHI = "4";
    private static final String TYPE_MANHUA = "5";
    private static final String TYPE_SHIPING = "6";

    @BindView(R.id.root_tv_title)
    TextView rootTvTitle;
    @BindView(R.id.root_iv_back)
    ImageView rootIvBack;
    @BindView(R.id.root_tv_desc)
    TextView rootTvDesc;
    @BindView(R.id.details_tv_comment_edit)
    TextView detailsTvCommentEdit;
    @BindView(R.id.include_rv)
    RecyclerView includeRv;
    @BindView(R.id.include_swipe)
    SwipeRefreshLayout includeSwipe;

    // 跳转传递的数据
    private Contents.Data.ContentsInfo contents;

    private boolean isComment = false;
    private CommentsAdapter commentsAdapter;

    private ArrayList<String> meiNvOrManHuaUrls = new ArrayList<>();
    private List<CommentsSection> list = new ArrayList<>();
    // 最新评论
    private List<CommentInfo> newComments = new ArrayList<>();
    private List<CommentInfo> hotComments = new ArrayList<>();

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
     * 点赞列表
     */
    private List<SparseBooleanArray> sparseList;
    private TextView itemContentsTvGood;
    private TextView itemContentsTvBad;
    private TextView itemContentsTvComment;
    private TextView itemContentsTvSkip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_details);
        ButterKnife.bind(this);

        initView();

        initData();
    }

    @Override
    protected ContentsDetailPresenter createPresenter() {
        return new ContentsDetailPresenter(this);
    }

    private void initView() {
        // 加载进度条开始到结束，4种变换颜色
        includeSwipe.setProgressViewOffset(false, 0, 100);
        includeSwipe.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4);
        // 下拉刷新监听
        includeSwipe.setOnRefreshListener(this);
        // 设置显示
        includeSwipe.setRefreshing(true);

        includeRv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        includeRv.setLayoutManager(linearLayoutManager);

        rootTvTitle.setText("详情");
        rootTvDesc.setText("投诉");
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contents = bundle.getParcelable("contents");
            isComment = bundle.getBoolean("isComment");

            mPresenter.requestComments(contents.userId, contents.contentsId);
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
     *
     * @param result
     */
    @Override
    public void showData(ContentsDetail result) {
        includeSwipe.setRefreshing(false);

        hotComments = result.data.hotComments;
        newComments = result.data.newComments;

        /**
         * 根据获取的数据显示用户是否点赞和踩：未做，需要用户登陆
         * 键：0赞 1踩：点赞不能点踩，点踩不能点赞；默认没有点击；循环设置数据
         *
         */
        sparseList = new ArrayList<>();
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        sparseBooleanArray.put(0, false);
        sparseBooleanArray.put(1, false);
        sparseList.add(sparseBooleanArray);

        /**
         * 当没有最新数据时，显示没有评论提示
         *
         */
        if (newComments.isEmpty() || hotComments.isEmpty()) {
            hasCommentData = false;
        } else {
            hasCommentData = true;
        }

        int hotCount = hotComments.size();
        if (hotCount != 0) {
            list.add(new CommentsSection(true, "热门评论", true));
            for (int i = 0; i < hotCount; i++) {
                list.add(new CommentsSection(hotComments.get(i)));
            }
        }

        int newCount = newComments.size();
        if (newCount != 0) {
            list.add(new CommentsSection(true, "最新评论", true));
            for (int j = 0; j < newCount; j++) {
                list.add(new CommentsSection(newComments.get(j)));
            }
        }

        commentsAdapter = new CommentsAdapter(R.layout.item_comments_content, R.layout.item_comments_head, list);
        commentsAdapter.setEnableLoadMore(true);
        commentsAdapter.setHeaderView(initHeadView(result));
        commentsAdapter.setLoadMoreView(new CustomLoadMoreView());
        if (list.size() > 2) {
            commentsAdapter.setOnLoadMoreListener(this);
        }
        includeRv.setAdapter(commentsAdapter);

        /**
         * 点击评论数据：回复评论：未做
         *
         */
        includeRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(ContentsDetailsActivity.this, "点击了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show();
            }
        });

        // 如果首页跳转过来的是评论滑动到最底部
        if (isComment) {
            commentsAdapter.loadMoreEnd(true);

            includeRv.scrollToPosition(hotCount + newCount);
        }

    }

    /**
     * 填充数据
     *
     * @param result
     */
    private View initHeadView(ContentsDetail result) {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_rv_contents_details, null, false);

        ImageView detailsIvUserPhoto = (ImageView) headerView.findViewById(R.id.details_iv_user_photo);
        TextView detailsTvUserName = (TextView) headerView.findViewById(R.id.details_tv_user_name);
        TextView detailsTvUserDesc = (TextView) headerView.findViewById(R.id.details_tv_user_desc);
        TextView detailsTvCare = (TextView) headerView.findViewById(R.id.details_tv_care);

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
        detailsTvCare.setOnClickListener(this);
        itemContentsIv.setOnClickListener(this);

        itemContentsTvGood.setOnClickListener(this);
        itemContentsTvBad.setOnClickListener(this);
        itemContentsTvComment.setOnClickListener(this);
        itemContentsTvSkip.setOnClickListener(this);

        if (list.isEmpty()) {
            tvChildEmptyComment = new TextView(this);
            tvChildEmptyComment.setText("暂无评论>>>");
            tvChildEmptyComment.setTextColor(getResources().getColor(R.color.dialog_no));
            tvChildEmptyComment.setTextSize(14.0f);
            tvChildEmptyComment.setPadding(10, 30, 10, 20);
            tvChildEmptyComment.setGravity(LinearLayout.VERTICAL);

            contentTypeLl.addView(tvChildEmptyComment);
        }

        ContentsDetail.Data data = result.data;

        // 用户头像
        Glide.with(this)
                .load(TianGouUrls.IMAGE_URL + contents.userPhoto)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideCircleImageTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(detailsIvUserPhoto);
        // 用户名称
        detailsTvUserName.setText(contents.username);

        // 用户发布作品数量
        String countDesc = data.userContentsCount + "作品 " + data.userFollowersCount + "粉丝";
        detailsTvUserDesc.setText(countDesc);

        // 类型
        switch (contents.type) {
            case TYPE_DUANZI:
                itemContentsTvType.setText("段子");
                // 图片、视频、隐藏
                itemContentsIv.setVisibility(View.GONE);
                contentsVideoplayer.setVisibility(View.GONE);
                contentsGvImg.setVisibility(View.GONE);
                itemContentsTvDesc.setVisibility(View.VISIBLE);

                String content_desc = contents.contentDesc;
                if (content_desc.contains("<br/>")) {
                    String joke = contents.contentDesc.replace("<br/>", "");
                    itemContentsTvDesc.setText(joke);
                } else {
                    itemContentsTvDesc.setText(content_desc);
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
                meiNvOrManHuaUrls.add(contents.imgUrlOrContent);

                Glide.with(this)
                        .load(contents.imgUrlOrContent)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                String imgUrlOrContent = contents.imgUrlOrContent;
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

                contentsVideoplayer.setUp(contents.imgUrlOrContent, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, contents.title);
                // 添加缩略图
                Glide.with(this)
                        .load(R.mipmap.ic_launcher)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(contentsVideoplayer.thumbImageView);

                break;
            case TYPE_MEINV:
                itemContentsTvType.setText("美女搞清图");

                itemContentsIv.setVisibility(View.GONE);
                itemContentsTvDesc.setVisibility(View.GONE);
                contentsVideoplayer.setVisibility(View.GONE);
                contentsGvImg.setVisibility(View.VISIBLE);

                String img = contents.imgUrlOrContent;
                if (img.contains(";")) {
                    String[] split = img.split(";");
                    for (String aSplit : split) {
                        meiNvOrManHuaUrls.add(aSplit);
                    }
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
                String img1 = contents.imgUrlOrContent;
                if (img1.contains(";")) {
                    String[] split = img1.split(";");
                    for (String aSplit : split) {
                        meiNvOrManHuaUrls.add(aSplit);
                    }
                } else {
                    meiNvOrManHuaUrls.add(img1);
                }

                Glide.with(this)
                        .load(meiNvOrManHuaUrls.get(0))
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(itemContentsIv);
                break;
            default:
                break;
        }

        // 点赞、点踩、评论、转发数量
        itemContentsTvGood.setText(contents.goodCount);
        itemContentsTvBad.setText(contents.badCount);
        itemContentsTvComment.setText(contents.commentCount);
        itemContentsTvSkip.setText(contents.shareCount);

        return headerView;
    }

    @Override
    public void showErrorMessage(String errorMsg) {
        includeSwipe.setRefreshing(false);

        Toast.makeText(ContentsDetailsActivity.this, R.string.toast_error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求更多数据
     */
    @Override
    public void onLoadMoreRequested() {
        String contentsId = contents.contentsId;

        // 如果list数量大于2个，说明有数据；这2个是title;
        if (list.size() > 2) {
            CommentInfo comments = commentsAdapter.getItem((list.size() - 1)).t;
            String createTime = comments.createTime;

            mPresenter.requestMoreComments(contentsId, createTime);
        } else {
            // 取消加载更多View
            if (commentsAdapter.isLoadMoreEnable()) {
                commentsAdapter.loadMoreEnd(true);
            }
        }

    }

    /**
     * 显示更多数据
     * @param comments
     */
    @Override
    public void showMoreData(Comments comments) {
        commentsAdapter.loadMoreComplete();

        List<CommentInfo> data = comments.data;
        if (data.isEmpty()) {
            commentsAdapter.loadMoreEnd(false);
        } else {
            // 先添加到集合中，在添加到adapter中
            int size = data.size();
            for (int j = 0; j < size; j++) {
                list.add(new CommentsSection(data.get(j)));
            }

            commentsAdapter.addData(list);
        }
    }

    /**
     * 评论成功
     *
     * @param faBuComment
     */
    @Override
    public void showFaBuComment(FaBuComment faBuComment) {
        bottomDialog.dismissView();
        inputManager.hideSoftInputFromWindow(dialog_et_comment.getWindowToken(), 0);

        /**
         * 无数据时，不显示最热，只显示最新评论：自己添加的评论
         */
        if (hasCommentData) {
            int newPosition = hotComments.size() + 2;
            commentsAdapter.addData(newPosition, new CommentsSection(faBuComment.data));

        } else {
            list.add(new CommentsSection(true, "最新评论", true));
            list.add(new CommentsSection(faBuComment.data));

            contentTypeLl.removeView(tvChildEmptyComment);
        }

        Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();

    }

    /**
     * 评论失败
     * @param errorMsg
     */
    @Override
    public void showFaBuCommentError(String errorMsg) {
        bottomDialog.dismissView();
        inputManager.hideSoftInputFromWindow(dialog_et_comment.getWindowToken(), 0);


        Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点赞成功
     * @param goodOrBadCount
     */
    @Override
    public void showUpdateGoodCount(GoodOrBadCount goodOrBadCount) {

    }

    /**
     * 点踩成功
     * @param goodOrBadCount
     */
    @Override
    public void showUpdateBadCount(GoodOrBadCount goodOrBadCount) {

    }

    /**
     * 点击ButterKnife注解控件
     * @param view
     */
    @OnClick({R.id.root_iv_back, R.id.root_tv_desc, R.id.details_tv_comment_edit})
    public void onButterKnifeClick(View view) {
        switch (view.getId()) {
            /**
             * 返回
             */
            case R.id.root_iv_back:
                finish();
                break;
            /**
             * 投诉
             */
            case R.id.root_tv_desc:
                Toast.makeText(this, "投诉", Toast.LENGTH_SHORT).show();
                break;
            /**
             * 评论:需要登录：未处理第三方输入法的表情输入
             */
            case R.id.details_tv_comment_edit:
                initBottomDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 点击控件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_iv_user_photo:
            case R.id.details_tv_user_name:
            case R.id.details_tv_user_desc:
                Toast.makeText(this, "用户信息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.details_tv_care:
                Toast.makeText(this, "关注", Toast.LENGTH_SHORT).show();
                break;
            /**
             * 图片
             *
             */
            case R.id.item_contents_iv:
                Intent intent = new Intent(ContentsDetailsActivity.this, MultiImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imgList", meiNvOrManHuaUrls);
                bundle.putInt("position", 0);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            /**
             * 赞
             */
            case R.id.item_contents_tv_good:
                // 判断当前是否踩过:被踩过，不能点赞
                boolean isCheckBad = sparseList.get(0).get(1);
                if (!isCheckBad) {
                    // 该条内容没有点赞过才能点赞
                    boolean isClickGood = sparseList.get(0).get(0);
                    if (!isClickGood) {
                        // 设置当前为true
                        sparseList.get(0).put(0, true);

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

                        // 更新服务器数据
                        mPresenter.requestUpdateGoodCount(contents.contentsId);
                    } else {
                        String title = getResources().getString(R.string.count_clicked);
                        CustomToast.show(this, title, CustomToast.LENGTH_SHORT);
                    }
                }
                break;
            /**
             * 踩
             */
            case R.id.item_contents_tv_bad:
                boolean isCheckGood = sparseList.get(0).get(0);
                if (!isCheckGood) {
                    boolean isClickBad = sparseList.get(1).get(1);
                    if (!isClickBad) {
                        sparseList.get(0).put(1, true);
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

                        // 更新服务器数据
                        mPresenter.requestUpdateBadCount(contents.contentsId);
                    } else {
                        String title = getResources().getString(R.string.count_clicked);
                        CustomToast.show(this, title, CustomToast.LENGTH_SHORT);
                    }
                }
                break;
            /**
             * 评论
             */
            case R.id.item_contents_tv_comment:
                initBottomDialog();
                break;
            /**
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
     *
     */
    private void initBottomDialog() {
        bottomDialog = new BottomDialog(this, R.layout.dialog_bottom_send_comment);
        View convertView = bottomDialog.getConvertView();
        dialog_et_comment = (EditText) convertView.findViewById(R.id.dialog_et_comment);
        dialog_tv_send = (TextView) convertView.findViewById(R.id.dialog_tv_send);
        bottomDialog.showView();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //设置可获得焦点
                dialog_et_comment.setFocusable(true);
                dialog_et_comment.setFocusableInTouchMode(true);
                //请求获得焦点
                dialog_et_comment.requestFocus();

                //调用系统输入法
                inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    dialog_tv_send.setBackground(getResources().getDrawable(R.drawable.gray_tv_bg_corner));
                    dialog_tv_send.setClickable(false);
                }
            }
        });

        dialog_tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = dialog_et_comment.getText().toString();
                int userId = SharedUtils.getSharedInt(ContentsDetailsActivity.this, SharedUtils.USERID, 1);
                
                mPresenter.requestFaBuComment(contents.contentsId, String.valueOf(userId), content);
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

}
