package com.xiao91.sky.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiao91.sky.R;
import com.xiao91.sky.content.ContentMultipleItem;

import java.util.List;

/**
 * 多內容的不同item
 * Created by xiao on 2017/3/20.
 */

public class ContentsMultipleItemAdapter extends BaseMultiItemQuickAdapter<ContentMultipleItem, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ContentsMultipleItemAdapter(List<ContentMultipleItem> data) {
        super(data);

//        addItemType(ContentMultipleItem.RECOMMEND, R.layout.item_content_recommend);
//        addItemType(ContentMultipleItem.JOKE, R.layout.item_content_joke);
//        addItemType(ContentMultipleItem.JOKE_IMAGE, R.layout.item_content_joke_image);
//        addItemType(ContentMultipleItem.BEAUTI_GIRL, R.layout.item_content_beauti_girl);
//        addItemType(ContentMultipleItem.STORY, R.layout.item_content_story);
//        addItemType(ContentMultipleItem.CARICATURE, R.layout.item_content_caricature);
//        addItemType(ContentMultipleItem.VIDEO, R.layout.item_content_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentMultipleItem item) {
        int itemViewType = helper.getItemViewType();
        switch (itemViewType) {
            // 推薦
            case ContentMultipleItem.RECOMMEND:
                break;
            // 笑話
            case ContentMultipleItem.JOKE:
                break;
            // 內涵圖
            case ContentMultipleItem.JOKE_IMAGE:
                break;
            // 美女
            case ContentMultipleItem.BEAUTI_GIRL:
                break;
            // 故事
            case ContentMultipleItem.STORY:
                break;
            // 漫畫
            case ContentMultipleItem.CARICATURE:
                break;
            // 視頻
            case ContentMultipleItem.VIDEO:
                break;
            default:
                break;
        }
    }
}
