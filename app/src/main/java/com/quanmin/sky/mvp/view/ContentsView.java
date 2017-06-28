package com.quanmin.sky.mvp.view;

import com.quanmin.sky.bean.ContentEntry;
import com.quanmin.sky.mvp.base.IBaseMvpView;

/**
 *
 * Created by xiao on 2017/1/5 0005.
 */

public interface ContentsView extends IBaseMvpView<ContentEntry> {

    void onShowEmptyData();

    void onShowMoreData(ContentEntry contentEntry);

    void onShowMoreDataError(int errorCode);

    /**
     * 点赞
     * @param code 提示码，0:没有更新，1：更新成功，-1：更新失败
     */
    void onShowUpdateGoodCount(Integer code);

    /**
     * 点踩
     * @param code 提示码，0:没有更新，1：更新成功，-1：更新失败
     */
    void onShowUpdateBadCount(Integer code);

}
