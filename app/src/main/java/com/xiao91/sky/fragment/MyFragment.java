package com.xiao91.sky.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiao91.sky.R;
import com.xiao91.sky.adapter.TabLayoutAdapter;
import com.xiao91.sky.utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心
 * <p>
 * 2017-01-03
 */
public class MyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean isLogin;
    private int userId;

    private ViewPager mViewPager;
    private List<Fragment> mFragments;

    private String[] mTitles = new String[]{"投稿", "直播", "收藏", "评论"};
    private TabLayout mTabLayout;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        // 第一步，初始化ViewPager和TabLayout
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        setupViewPager();

        return view;
    }

    private void setupViewPager() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            MyChildFragment listFragment = MyChildFragment.newInstance(mTitles[i], "");
            mFragments.add(listFragment);
        }
        // 第二步：为ViewPager设置适配器
        TabLayoutAdapter adapter =
                new TabLayoutAdapter(getChildFragmentManager(), mFragments, mTitles);

        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    private void initData() {
        isLogin = SharedUtils.getSharedBoolean(getActivity(), SharedUtils.LOGIN, false);
        userId = SharedUtils.getSharedInt(getActivity(), SharedUtils.USERID, 0);
    }
}
