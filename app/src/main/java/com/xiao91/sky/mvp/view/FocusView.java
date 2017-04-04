package com.xiao91.sky.mvp.view;

import com.xiao91.sky.bean.AddFocus;
import com.xiao91.sky.bean.CancelFocus;

/**
 * 关注
 *
 * Created by xl on 2017/2/17 0017.
 *
 */

public interface FocusView {

    /**
     * 添加关注
     * @param addFocus
     */
    void showAddFocus(AddFocus addFocus);

    /**
     * 取消关注
     * @param cancelFocus
     *
     */
    void showCancelFocus(CancelFocus cancelFocus);

}
