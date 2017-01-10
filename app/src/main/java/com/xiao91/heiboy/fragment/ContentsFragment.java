package com.xiao91.heiboy.fragment;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.xiao91.heiboy.R;
import com.xiao91.heiboy.adapter.ContentsItemAdapter;
import com.xiao91.heiboy.bean.Contents;
import com.xiao91.heiboy.impl.OnClickGridImageItemListener;
import com.xiao91.heiboy.mvp_p.ContentsPresenter;
import com.xiao91.heiboy.mvp_v.ContentsView;
import com.xiao91.heiboy.view.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;

/**
 * 内容显示
 * <p>
 * 2017-01-07
 */
public class ContentsFragment extends MVPAbsFragment<ContentsView, ContentsPresenter> implements ContentsView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPE = "type";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int type;
    private String mParam2;

    private RecyclerView recyclerView;

    private AlertDialog dialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ContentsItemAdapter contentsAdapter;
    private List<SparseBooleanArray> list;
    private int currentCount;

    public ContentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type   Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentsFragment newInstance(int type, String param2) {
        ContentsFragment fragment = new ContentsFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected ContentsPresenter createPresenter() {
        return new ContentsPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contents, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.content_swipe_refresh);
        // 加载进度条开始到结束，4种变换颜色
        swipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4);
        // 下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(this);
        // 设置显示
        swipeRefreshLayout.setRefreshing(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.content_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 请求数据
        mPresenter.requestData(type);
    }

    @Override
    public void showData(Contents result) {
        swipeRefreshLayout.setRefreshing(false);

        List<Contents.Data.ContentsInfo> data = result.data.contents;
        currentCount = result.data.currentCount;

        contentsAdapter = new ContentsItemAdapter(R.layout.item_contents_view, data);
        contentsAdapter.setEnableLoadMore(true);
        contentsAdapter.setLoadMoreView(new CustomLoadMoreView());
        contentsAdapter.setOnLoadMoreListener(this);

        recyclerView.setAdapter(contentsAdapter);

        /**
         * 键：0赞 1踩：点赞不能点踩，点踩不能点赞；默认没有点击；循环设置数据
         *
         * 添加、删除数据均要操作
         *
         */
        list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            sparseBooleanArray.put(0, false);
            sparseBooleanArray.put(1, false);
            list.add(i, sparseBooleanArray);
        }

        contentsAdapter.setOnClickGridImageItemListener(new OnClickGridImageItemListener() {
            @Override
            public void onRecyclerViewItemClick(View view, int parentPosition, int childPosition) {
                Log.e("Contents", "点击的parentPosition=" + (parentPosition + 1));

                Toast.makeText(getActivity(), "点击九宫图的第" + (childPosition + 1) + "个", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_contents_iv_user_photo:
                        Toast.makeText(getActivity(), "点击了头像~", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_contents_tv_desc:
                        Toast.makeText(getActivity(), "点击了文本内容~", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_contents_iv:
                        Toast.makeText(getActivity(), "点击了图片~", Toast.LENGTH_SHORT).show();
                        break;
                    /**
                     * 赞
                     *
                     */
                    case R.id.item_contents_tv_good:
                        // 判断当前是否踩过:被踩过，不能点赞
                        boolean isCheckBad = list.get(position).get(1);
                        if (!isCheckBad) {
                            // 设置当前为true
                            list.get(position).put(0, true);

                            TextView item_contents_tv_good = (TextView) view;
                            Resources resources = getActivity().getResources();
                            Drawable drawable = resources.getDrawable(R.mipmap.good_blue);
                            int blue = resources.getColor(R.color.color_good_bad_comment_skip);
                            // 设置边界
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            // 画在左边
                            item_contents_tv_good.setCompoundDrawables(drawable, null, null, null);
                            item_contents_tv_good.setTextColor(blue);
                        }
                        break;
                    /**
                     * 踩
                     *
                     */
                    case R.id.item_contents_tv_bad:
                        boolean isCheckGood = list.get(position).get(0);
                        if (!isCheckGood) {
                            list.get(position).put(1, true);

                            TextView item_contents_tv_bad = (TextView) view;
                            Resources resources = getActivity().getResources();
                            Drawable drawableBad = resources.getDrawable(R.mipmap.good_blue);
                            int blue = resources.getColor(R.color.color_good_bad_comment_skip);
                            drawableBad.setBounds(0, 0, drawableBad.getMinimumWidth(), drawableBad.getMinimumHeight());
                            item_contents_tv_bad.setCompoundDrawables(drawableBad, null, null, null);
                            item_contents_tv_bad.setTextColor(blue);
                        }
                        break;
                    /**
                     * 跳转评论页
                     *
                     *
                     */
                    case R.id.item_contents_tv_comment:
                        Toast.makeText(getActivity(), "点击了评论~", Toast.LENGTH_SHORT).show();
                        break;
                    /**
                     * 弹出转发对话框
                     *
                     *
                     */
                    case R.id.item_contents_tv_skip:
                        Toast.makeText(getActivity(), "点击了转发~", Toast.LENGTH_SHORT).show();
                        break;
                    /**
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

    @Override
    public void showErrorMessage(String errorMsg) {
        swipeRefreshLayout.setRefreshing(false);

        Toast.makeText(getActivity(), "加载出错了", Toast.LENGTH_SHORT).show();
    }

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
        if (JCVideoPlayerManager.getFirstFloor() != null) {
            JCVideoPlayer.releaseAllVideos();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
                Toast.makeText(getActivity(), "正在完善中...", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    /**
     * 加载更多数据
     */
    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestMoreData(type, currentCount);
    }

    /**
     * 显示更多数据
     *
     * @param contents
     */
    @Override
    public void showMoreData(Contents contents) {
        contentsAdapter.loadMoreComplete();

        List<Contents.Data.ContentsInfo> contentsMore = contents.data.contents;

        if (contentsMore.isEmpty()) {
            contentsAdapter.loadMoreEnd();

            return;

        }
        contentsAdapter.loadMoreEnd(false);
        // 当前个数加上加载更多的个数
        currentCount += contents.data.currentCount;

        contentsAdapter.addData(contentsMore);

        int size = contentsMore.size();
        for (int i = 0; i < size; i++) {
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            sparseBooleanArray.put(0, false);
            sparseBooleanArray.put(1, false);
            list.add(size, sparseBooleanArray);
        }

    }

    @Override
    public void showMoreDataError(String errorMsg) {
        contentsAdapter.loadMoreComplete();

        Toast.makeText(getActivity(), "加载出错了", Toast.LENGTH_SHORT).show();
    }
}
