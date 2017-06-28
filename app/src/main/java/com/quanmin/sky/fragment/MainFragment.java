package com.quanmin.sky.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.quanmin.sky.R;
import com.quanmin.sky.adapter.TabLayoutAdapter;
import com.quanmin.sky.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 *
 * @author xiao 2017.03.16
 */
public class MainFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.main_tab)
    TabLayout mTab;
    @BindView(R.id.iv_right_navigation)
    ImageView ivRightNavigation;
    @BindView(R.id.main_viewpager)
    ViewPager mViewpager;

    private String mParam1;
    private String mParam2;

    public MainFragment() {

    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View baseView) {
        ivRightNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTab.scrollBy(mTab.getWidth(), 0);
            }
        });
    }

    @Override
    protected void initData() {
        Resources resources = getActivity().getResources();
        final String[] mainTitles = resources.getStringArray(R.array.main_tab);
        final int[] mainTabType = resources.getIntArray(R.array.main_tab_type);
        for (String title : mainTitles) {
            mTab.addTab(mTab.newTab().setText(title));
        }

        List<Fragment> fragments = new ArrayList<>();
        int length = mainTabType.length;
        for (int i = 0; i < length; i++) {
            fragments.add(ContentFragment.newInstance(i, ""));
        }

        TabLayoutAdapter adapter = new TabLayoutAdapter(getChildFragmentManager(), fragments, mainTitles);
        mViewpager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewpager);
    }

}
