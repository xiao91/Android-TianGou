package com.quanmin.sky.adapter.content;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.quanmin.sky.bean.ContentEntry;

/**
 * 內容分類
 * Created by xiao on 2017/3/20.
 */

public class ContentMultipleItem implements MultiItemEntity {

    // 类型:1.推荐
    public static final int RECOMMEND = 0;
    // 2.段子
    public static final int JOKE = 1;
    // 3.内涵图
    public static final int JOKE_IMAGE = 2;
    // 4.美女高清图
    public static final int BEAUTI_GIRL = 3;
    // 5.故事
    public static final int STORY = 4;
    // 6.漫画
    public static final int CARICATURE = 5;
    // 7.视频
    public static final int VIDEO = 6;

    // item類型
    private int itemType;
    private ContentEntry contentEntry;

    public ContentMultipleItem(int itemType, ContentEntry contentEntry) {
        this.itemType = itemType;
        this.contentEntry = contentEntry;
    }

    public ContentEntry getContentEntry() {
        return contentEntry;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
