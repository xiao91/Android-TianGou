package com.xiao91.heiboy.mvp_v;

import com.xiao91.heiboy.bean.Contents;
import com.xiao91.heiboy.bean.GoodOrBadCount;

/**
 *
 * Created by xiao on 2017/1/5 0005.
 */

public interface ContentsView extends IBaseView<Contents> {

    void showMoreData(Contents contents);

    void showMoreDataError(String errorMsg);

    void showUpdateGoodCount(GoodOrBadCount goodOrBadCount);

    void showUpdateBadCount(GoodOrBadCount goodOrBadCount);

}
