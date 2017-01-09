package com.xiao91.heiboy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao91.heiboy.LoginActivity;
import com.xiao91.heiboy.R;
import com.xiao91.heiboy.utils.SharedUtils;

/**
 * 个人中心
 *
 * 2017-01-03
 *
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeFragment() {
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
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        LinearLayout ll_login = (LinearLayout) view.findViewById(R.id.ll_login);
        ImageView me_iv_set = (ImageView) view.findViewById(R.id.me_iv_set);
        TextView tv_fensi = (TextView) view.findViewById(R.id.tv_fensi);
        TextView tv_guanzhu = (TextView) view.findViewById(R.id.tv_guanzhu);
        TextView tv_jifen = (TextView) view.findViewById(R.id.tv_jifen);
        ll_login.setOnClickListener(this);
        me_iv_set.setOnClickListener(this);
        tv_fensi.setOnClickListener(this);
        tv_guanzhu.setOnClickListener(this);
        tv_jifen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_login:
                boolean isLogin = SharedUtils.getSharedBoolean(getActivity(), SharedUtils.LOGIN, false);
                if (isLogin) {
                    Toast.makeText(getActivity(), "已经登陆，进入我的详情界面，修改信息", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.me_iv_set:
                Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_fensi:
                Toast.makeText(getActivity(), "粉丝", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_guanzhu:
                Toast.makeText(getActivity(), "关注", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_jifen:
                Toast.makeText(getActivity(), "积分", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
