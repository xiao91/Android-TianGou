package com.quanmin.sky.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quanmin.sky.R;
import com.quanmin.sky.activity.ContentsDetailsActivity;
import com.quanmin.sky.activity.MultiImageActivity;
import com.quanmin.sky.activity.UserCenterActivity;
import com.quanmin.sky.adapter.ContentAdapter;
import com.quanmin.sky.bean.ContentEntry;
import com.quanmin.sky.domain.ShareDomain;
import com.quanmin.sky.fragment.base.BaseMvpFragment;
import com.quanmin.sky.listener.OnClickGridImageItemListener;
import com.quanmin.sky.mvp.presenter.ContentsPresenter;
import com.quanmin.sky.mvp.view.ContentsView;
import com.quanmin.sky.utils.DeviceUtils;
import com.quanmin.sky.utils.SharedUtils;
import com.quanmin.sky.view.CustomLoadMoreView;
import com.xl91.ui.flypopup.FlyPopupWindow;
import com.xl91.ui.toast.CustomToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 内容显示
 * <p>
 *
 * @author xiao 2017-01-07
 */
public class ContentFragment extends BaseMvpFragment<ContentsView, ContentsPresenter> implements ContentsView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TYPE = "type";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefresh;

    private int type;

    private AlertDialog dialog;

    private ContentAdapter contentsAdapter;
    private int currentCount;
    private int totalCount;

    // 点赞或点踩的view
    private View goodOrBadView;
    private int goodOrBadPosition;

    // 是否点击的是评论
    private boolean isClickComment;

    public ContentFragment() {

    }

    public static ContentFragment newInstance(int type, String param2) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }

    @Override
    protected ContentsPresenter createPresenter() {
        return new ContentsPresenter(this);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_contents;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 请求数据
        mPresenter.requestData(type);
    }

    @Override
    protected void initView(View baseView) {
        // 加载进度条开始到结束，4种变换颜色
        mSwipeRefresh.setProgressViewOffset(false, 0, 100);
        mSwipeRefresh.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4);
        // 下拉刷新监听
        mSwipeRefresh.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        // 刷新时延迟2秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "正在完善中...", Toast.LENGTH_SHORT).show();

                mSwipeRefresh.setRefreshing(false);
            }
        }, 2000);
    }

    /**
     * 加载更多数据
     */
    @Override
    public void onLoadMoreRequested() {
        Log.e("content", "3当前数量：currentCount=" + currentCount);
        Log.e("content", "3总数量：totalCount=" + totalCount);

        if (currentCount < totalCount) {
            mPresenter.requestMoreData(type, currentCount);
        }

    }

    /**
     * 显示数据：第一次请求的数据
     */
    @Override
    public void onShowData(ContentEntry result) {
        ContentEntry.ContentData contentData = result.data;
        // 总数量
        totalCount = Integer.parseInt(contentData.total);
        // 当前数量
        currentCount = contentData.currentCount;

        Log.e("content", "1当前数量：currentCount=" + currentCount);
        Log.e("content", "1总数量：totalCount=" + totalCount);

        List<ContentEntry.ContentData.Content> contentList = contentData.content;
        contentsAdapter = new ContentAdapter(R.layout.item_contents_view, contentList);

        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(contentsAdapter);
        }

        // 设置加载更多
        if (currentCount < totalCount) {
            contentsAdapter.setLoadMoreView(new CustomLoadMoreView());
            contentsAdapter.setEnableLoadMore(true);
            contentsAdapter.setOnLoadMoreListener(this, mRecyclerView);
        }

        /*
         * 九宫图点击item跳转
         *
         */
        contentsAdapter.setOnClickGridImageItemListener(new OnClickGridImageItemListener() {
            @Override
            public void onClickGridImageItemListener(View view, int position, ArrayList<String> url) {
                Intent intent = new Intent(getActivity(), MultiImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imgList", url);
                bundle.putInt("position", position);
                bundle.putString("title", contentsAdapter.getItem(position).contentTitle);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        contentsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intoContentDetailActivity(position, (ContentEntry.ContentData.Content) adapter.getItem(position));
            }
        });

        contentsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ContentEntry.ContentData.Content item = (ContentEntry.ContentData.Content) adapter.getItem(position);
                switch (view.getId()) {
                    /*
                     * 用户中心
                     */
                    case R.id.item_contents_iv_user_photo:
                        Intent intent = new Intent(getActivity(), UserCenterActivity.class);
                        intent.putExtra("uid", item.userId);
                        startActivity(intent);
                        break;
                    /*
                     * 段子、单张图片点击
                     *
                     */
                    case R.id.item_contents_tv_desc:
                        intoContentDetailActivity(position, (ContentEntry.ContentData.Content) adapter.getItem(position));
                        break;
                    /*
                     * 内涵图或者是漫画
                     *
                     */
                    case R.id.item_contents_iv:
                        intoContentDetailActivity(position, (ContentEntry.ContentData.Content) adapter.getItem(position));
                        break;
                    /*
                     * 赞:还需要判断是否登陆
                     *
                     */
                    case R.id.item_contents_tv_good:
                        goodOrBadView = view;
                        goodOrBadPosition = position;

                        // 判断当前是否踩过:被踩过，不能点赞
                        boolean isCheckedBad = contentsAdapter.getIsCheckedBad(position);
                        if (!isCheckedBad) {
                            // 该条内容没有点赞过才能点赞
                            boolean isCheckedGood = contentsAdapter.getIsCheckedGood(position);
                            if (!isCheckedGood) {
                                // 更新服务器数据
                                String userId = SharedUtils.getSharedString(getActivity(), SharedUtils.USER_ID, "");
                                String deviceId = DeviceUtils.getDeviceId();
                                mPresenter.requestUpdateGoodCount(item.countId, userId, deviceId);
                            } else {
                                String title = getActivity().getResources().getString(R.string.count_clicked);
                                CustomToast.showCenterToast(getActivity(), title);
                            }
                        }
                        break;
                    /*
                     * 踩
                     *
                     */
                    case R.id.item_contents_tv_bad:
                        goodOrBadView = view;
                        goodOrBadPosition = position;

                        // 判断当前是否赞过:被赞过，不能点踩
                        boolean isCheckedGood = contentsAdapter.getIsCheckedGood(position);
                        if (!isCheckedGood) {
                            // 该条内容没有点踩
                            isCheckedBad = contentsAdapter.getIsCheckedBad(position);
                            if (!isCheckedBad) {
                                // 更新服务器数据
                                String userId = SharedUtils.getSharedString(getActivity(), SharedUtils.USER_ID, "");
                                String deviceId = DeviceUtils.getDeviceId();
                                mPresenter.requestUpdateBadCount(item.countId, userId, deviceId);
                            } else {
                                String title = getActivity().getResources().getString(R.string.count_clicked);
                                CustomToast.showCenterToast(getActivity(), title);
                            }
                        }
                        break;
                    /*
                     * 评论,也是跳转到详情页面，但会滑动到底部，用comment标记
                     */
                    case R.id.item_contents_tv_comment:
                        isClickComment = true;
                        intoContentDetailActivity(position, (ContentEntry.ContentData.Content) adapter.getItem(position));
                        break;
                    /*
                     * 分享转发
                     *
                     *
                     */
                    case R.id.item_contents_tv_skip:
                        ShareDomain shareDomain = new ShareDomain(getActivity());
                        shareDomain.startShare();
                        break;
                    /*
                     *
                     * 删除
                     *
                     */
                    case R.id.item_contents_delete:
                        initDialog(position);
                        break;
                    default:
                        break;

                }
            }
        });
    }

    /**
     * 跳转到详情
     * @param position
     * @param content
     */
    private void intoContentDetailActivity(int position, ContentEntry.ContentData.Content content) {
        boolean isBad = contentsAdapter.getIsCheckedBad(position);
        boolean isGood = contentsAdapter.getIsCheckedGood(position);
        Intent intent = new Intent(getActivity(), ContentsDetailsActivity.class);
        Bundle bundleJoke = new Bundle();
        bundleJoke.putParcelable("content", content);
        bundleJoke.putBoolean("isGood", isGood);
        bundleJoke.putBoolean("isBad", isBad);
        bundleJoke.putBoolean("isClickComment", isClickComment);
        intent.putExtras(bundleJoke);
        startActivity(intent);
    }

    /**
     * 第一次请求数据错误
     *
     * @param code 错误码
     */
    @Override
    public void onShowError(int code) {
        showToastError(code);
    }

    /**
     * 空数据
     */
    @Override
    public void onShowEmptyData() {
        Toast.makeText(getActivity(), R.string.toast_empty_data, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示刷新进度条
     */
    @Override
    public void onShowProgressbar() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(true);
        }
    }

    /**
     * 隐藏刷新进度条
     */
    @Override
    public void onHideProgressbar() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    /**
     * 显示更多数据
     *
     * @param contentEntry 内容
     */
    @Override
    public void onShowMoreData(ContentEntry contentEntry) {
        ContentEntry.ContentData data = contentEntry.data;
        List<ContentEntry.ContentData.Content> contentsMore = data.content;
        // 总数量
        totalCount = Integer.parseInt(data.total);
        // 当前数量
        currentCount += data.currentCount;

        if (currentCount >= totalCount) {
            // 加载更多完成
            contentsAdapter.loadMoreEnd(false);
        }

        Log.e("content", "2当前数量：currentCount=" + currentCount);
        Log.e("content", "2总数量：totalCount=" + totalCount);

        // 添加点赞与点踩的数据
        contentsAdapter.setGoodState(contentsMore);
        contentsAdapter.addData(contentsMore);
    }

    /**
     * 加载更多数据出错
     *
     * @param errorCode 错误码
     */
    @Override
    public void onShowMoreDataError(int errorCode) {
        contentsAdapter.loadMoreEnd();

        showToastError(errorCode);
    }

    /**
     * 点赞
     * @param code 提示码，0:没有更新，1：更新成功，-1：更新失败
     */
    @Override
    public void onShowUpdateGoodCount(Integer code) {
        if (code == 1) {
            // 取消动画
            ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            // 设置点赞
            contentsAdapter.setIsCheckedGood(goodOrBadPosition);
            contentsAdapter.setGoodTextView(goodOrBadPosition);

            Resources resources = getActivity().getResources();
            int blue = resources.getColor(R.color.color_good_bad_comment_skip);
            // 添加+1动画
            FlyPopupWindow flyPopupWindow = new FlyPopupWindow(getActivity());
            flyPopupWindow.setTextInfo("+ 1", blue, 20);
            flyPopupWindow.show(goodOrBadView);
        }
    }

    /**
     * 点踩
     * @param code 提示码，0:没有更新，1：更新成功，-1：更新失败
     */
    @Override
    public void onShowUpdateBadCount(Integer code) {
        if (code == 1) {
            // 取消动画
            ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            // 设置点踩
            contentsAdapter.setIsCheckedBad(goodOrBadPosition);
            contentsAdapter.setBadTextView(goodOrBadPosition);

            Resources resources = getActivity().getResources();
            int blue = resources.getColor(R.color.color_good_bad_comment_skip);
            // 添加+1动画
            FlyPopupWindow flyPopupWindow = new FlyPopupWindow(getActivity());
            flyPopupWindow.setTextInfo("+ 1", blue, 20);
            flyPopupWindow.show(goodOrBadView);
        }
    }

    /**
     * dialog处理删除
     *
     * @param position 第几个数据
     */
    private void initDialog(int position) {
        View viewDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_contents_delete, null, false);
        dialog = new AlertDialog.Builder(getContext())
                .setView(viewDialog)
                .show();

        TextView dialog_tv_no = (TextView) viewDialog.findViewById(R.id.dialog_tv_no);
        TextView dialog_tv_ok = (TextView) viewDialog.findViewById(R.id.dialog_tv_ok);

        dialog_tv_no.setOnClickListener(this);
        dialog_tv_ok.setOnClickListener(this);

    }

    /**
     * view控件点击
     *
     * @param view 控件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_tv_no:
                dialog.dismiss();

                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dialog_tv_ok:
                dialog.dismiss();

                Toast.makeText(getActivity(), "确定", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    /**
     * home键点击
     *
     * @param item 菜单
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
