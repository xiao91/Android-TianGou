package com.xiao91.heiboy.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiao91.heiboy.R;
import com.xiao91.heiboy.adapter.TabLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 */
public class MainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TabLayout mainTab;
    private ViewPager mainViewPager;
    private FloatingActionButton fab;

    public MainFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mainTab = (TabLayout) view.findViewById(R.id.main_tab);
        mainViewPager = (ViewPager) view.findViewById(R.id.main_viewpager);
        ImageView iv_right_navigation = (ImageView) view.findViewById(R.id.iv_right_navigation);
        iv_right_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainTab.scrollBy(mainTab.getWidth(), 0);
            }
        });

        fab = (FloatingActionButton) view.findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "点击了Fab", Toast.LENGTH_SHORT).show();
            }
        });

        final String[] mainTitles = getActivity().getResources().getStringArray(R.array.main_tab);
        final int[] mainTabType = getActivity().getResources().getIntArray(R.array.main_tab_type);
        for (String title : mainTitles) {
            mainTab.addTab(mainTab.newTab().setText(title));
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ContentsFragment.newInstance(mainTabType[0], ""));
        fragments.add(ContentsFragment.newInstance(mainTabType[1], ""));
        fragments.add(ContentsFragment.newInstance(mainTabType[2], ""));
        fragments.add(ContentsFragment.newInstance(mainTabType[3], ""));
        fragments.add(ContentsFragment.newInstance(mainTabType[4], ""));
        fragments.add(ContentsFragment.newInstance(mainTabType[5], ""));
        fragments.add(ContentsFragment.newInstance(mainTabType[6], ""));

        TabLayoutAdapter adapter = new TabLayoutAdapter(getChildFragmentManager(), fragments, mainTitles);
        mainViewPager.setAdapter(adapter);
        mainTab.setupWithViewPager(mainViewPager);

    }

}
