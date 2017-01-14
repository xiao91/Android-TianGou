package com.xiao91.heiboy.domain;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.xiao91.heiboy.R;
import com.xiao91.heiboy.adapter.ShareAdapter;
import com.xiao91.heiboy.bean.Share;
import com.xl91.ui.BottomDialog;

import java.util.List;

/**
 * 分享功能
 * Created by xiao on 2017/1/14 0014.
 *
 */

public class ShareDomain {

    private Context context;

    public ShareDomain(Context context) {
        this.context = context;
    }

    public void startShare() {
        BottomDialog bottomDialog = new BottomDialog(context, R.layout.dialog_share);
        bottomDialog.showView();

        View convertView = bottomDialog.getConvertView();
        GridView dialog_share_gv = (GridView) convertView.findViewById(R.id.dialog_share_gv);

        // 添加图片
        List<Share> shareData = Share.getShareData();
        //添加并且显示
        final ShareAdapter shareAdapter = new ShareAdapter(context, shareData);
        dialog_share_gv.setAdapter(shareAdapter);

        /**
         * 点击转发
         */
        dialog_share_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Share item = (Share) shareAdapter.getItem(i);

                Toast.makeText(context, "点击了" + item.title, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
