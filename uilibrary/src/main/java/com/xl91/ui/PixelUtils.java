package com.xl91.ui;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 像素转换
 * Created by xiao on 2017/1/11 0011.
 */

public class PixelUtils {
    /**
     * 把dip单位转成px单位
     *
     * @param context context对象
     * @param dip     dip数值
     * @return
     */
    public static int formatDipToPx(Context context, int dip) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return (int) Math.ceil(dip * dm.density);
    }

    /**
     * 把px单位转成dip单位
     *
     * @param context context对象
     * @param px      px数值
     * @return
     */
    public static int formatPxToDip(Context context, int px) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return (int) Math.ceil(((px * 160) / dm.densityDpi));
    }
}
