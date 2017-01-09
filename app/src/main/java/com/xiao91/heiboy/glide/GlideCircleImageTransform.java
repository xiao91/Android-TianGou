package com.xiao91.heiboy.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Glide加载图片转换
 *
 * xiao
 *
 * 2017-01-07
 *
 */
public class GlideCircleImageTransform extends BitmapTransformation {

    public GlideCircleImageTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return toCropImage(pool, toTransform);
    }

    /**
     * 转换
     * @param pool
     * @param toTransform
     * @return
     */
    private Bitmap toCropImage(BitmapPool pool, Bitmap toTransform) {
        if (toTransform == null) return null;

        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
        int width = (toTransform.getWidth() - size) / 2;
        int height = (toTransform.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(toTransform, width, height, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        BitmapShader bitmapShader = new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);

        if (width != 0 || height != 0) {
            // source isn't square, move viewport to center
            Matrix matrix = new Matrix();
            matrix.setTranslate(-width, -height);
            bitmapShader.setLocalMatrix(matrix);
        }

        paint.setShader(bitmapShader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
