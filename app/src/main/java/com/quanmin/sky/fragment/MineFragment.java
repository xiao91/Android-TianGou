package com.quanmin.sky.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.quanmin.sky.R;
import com.quanmin.sky.activity.LoginActivity;
import com.quanmin.sky.adapter.TabLayoutAdapter;
import com.quanmin.sky.utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心
 * <p>
 * 2017-01-03
 */
public class MineFragment extends Fragment implements View.OnClickListener {
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

    private TabLayout mTabLayout;
    private String token;

    public MineFragment() {
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
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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

        LinearLayout rl_user_head = (LinearLayout) view.findViewById(R.id.rl_user_head);
        rl_user_head.setOnClickListener(this);

        // 第一步，初始化ViewPager和TabLayout
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        setupViewPager();

        return view;
    }

    private void setupViewPager() {
        List<Fragment> mFragments = new ArrayList<>();
        String[] mTitles = getActivity().getResources().getStringArray(R.array.arr_user_tab);
        for (String mTitle : mTitles) {
            MyChildFragment listFragment = MyChildFragment.newInstance(mTitle, "");
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
//        isLogin = SharedUtils.getSharedBoolean(getActivity(), SharedUtils.LOGIN, false);
//        userId = SharedUtils.getSharedInt(getActivity(), SharedUtils.USER_ID, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user_head:
                if (TextUtils.isEmpty(token)) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        token = SharedUtils.getSharedString(getActivity(), SharedUtils.TOKEN, "");
    }
}
