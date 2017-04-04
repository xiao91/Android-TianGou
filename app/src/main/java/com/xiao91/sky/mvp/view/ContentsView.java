package com.xiao91.sky.mvp.view;

import com.xiao91.sky.bean.Contents;
import com.xiao91.sky.bean.GoodOrBadCount;

/**
 *
 * Created by xiao on 2017/1/5 0005.
 */

public interface ContentsView extends MVPBaseView<Contents> {

    void onShowMoreData(Contents contents);

    void onShowMoreDataError(String errorMsg);

    void onShowUpdateGoodCount(GoodOrBadCount goodOrBadCount);

    void onShowUpdateBadCount(GoodOrBadCount goodOrBadCount);

}
