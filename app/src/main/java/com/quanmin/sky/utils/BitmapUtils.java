package com.quanmin.sky.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bitmap工具类
 *
 * @author xiao 2017-05-24
 */

public class BitmapUtils {

    private static BitmapUtils bitmapUtils = null;

    // 分辨率480 * 800；宽*高
    public static final int Config_480P = 1;
    // 分辨率720 * 1280
    public static final int Config_720P = 2;
    // 分辨率1080 * 1920
    public static final int Config_1080P = 3;
    // 分辨率1440 * 2560
    public static final int Config_2K = 4;

    private BitmapUtils() {
    }

    /**
     * 单例模式
     *
     * @return bitmapUtils BitmapUtils Bitmap管理工具类
     */
    public static BitmapUtils getInstance() {
        synchronized (BitmapUtils.class) {
            if (bitmapUtils == null) {
                bitmapUtils = new BitmapUtils();
            }
        }
        return bitmapUtils;
    }

    /**
     * 获取设置的尺寸
     *
     * @param config int 分辨率设置
     * @return size int 尺寸大小
     */
    private int getSize(int config) {
        int size = 0;
        switch (config) {
            case Config_480P:
                size = 480;
                break;
            case Config_720P:
                size = 720;
                break;
            case Config_1080P:
                size = 1080;
                break;
            case Config_2K:
                size = 1440;
                break;
            default:
                break;
        }
        return size;
    }

    /**
     * 返回适应屏幕尺寸的位图
     *
     * @param bit    Bitmap 原位图
     * @param config int 设置分辨率
     * @return bitmap Bitmap 返回设置分辨率大小的位图
     */
    public Bitmap getRightSizeBitmap(Bitmap bit, int config) {
        // 得到理想宽度
        int ww = getSize(config);
        // 获取图片宽度
        int w = bit.getWidth();
        // 计算缩放率
        float rate = 1f;
        if (w > ww) {
            rate = (float) ww / (float) w;
        }
        // 重新绘图
        Bitmap bitmap = Bitmap.createBitmap((int) (bit.getWidth() * rate),
                (int) (bit.getHeight() * rate), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Rect rect = new Rect(0, 0, (int) (bit.getWidth() * rate),
                (int) (bit.getHeight() * rate));
        canvas.drawBitmap(bit, null, rect, null);
        return bitmap;
    }

    /**
     * 返回适应屏幕的位图，更节省内存
     *
     * @param fileName String 文件名称
     * @param config   int 设置图片分辨率
     * @return bitmap Bitmap 返回设置分辨率大小的位图
     */
    public Bitmap getRightSizeBitmap(String fileName, int config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);
        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int ww = getSize(config);
        if ((ww * 2) < w) {
            options.inSampleSize = 2;
        }
        // 重新绘图
        Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);
        return getRightSizeBitmap(bitmap, config);
    }


    /**
     * 图片去色,返回灰度图片
     *
     * @param bmpOriginal Bitmap 原图片
     * @return bmpGray Bitmap 去色后的图片
     */
    public Bitmap toGrayBitmap(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGray = Bitmap.createBitmap(width, height,
                Config.RGB_565);
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGray;
    }

    /**
     * 去色同时加圆角
     *
     * @param bmpOriginal Bitmap 原图
     * @param pixels      int   圆角弧度
     * @return bitmap Bitmap 修改后的图片
     */
    public Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
        return toRoundCorner(toGrayBitmap(bmpOriginal), pixels);
    }

    /**
     * 把图片变成圆角
     *
     * @param bitmap Bitmap 需要修改的图片
     * @param pixels int 圆角的弧度
     * @return bitmap Bitmap 圆角图片
     */
    public Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 使圆角功能支持BitampDrawable
     *
     * @param bitmapDrawable Bitmap 需要修改的图片
     * @param pixels         int 圆角的弧度
     * @return bitmap Bitmap 圆角图片
     */
    @SuppressWarnings("deprecation")
    public BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
                                        int pixels) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    /**
     * 读取路径中的图片，然后将其转化为缩放后的bitmap返回
     *
     * @param path String 文件路径
     * @return bitmap Bitmap 位图
     */
    public Bitmap saveBefore(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
        options.inJustDecodeBounds = false;
        // 计算缩放比
        int be = (int) (options.outHeight / (float) 200);
        if (be <= 0)
            be = 1;
        options.inSampleSize = 2; // 图片长宽各缩小二分之一
        // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
        bitmap = BitmapFactory.decodeFile(path, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        System.out.println(w + "   " + h);
        // savePNG_After(bitmap,path);
        saveJPGE_After(bitmap, path, 90);
        return bitmap;
    }

    /**
     * 将Bitmap转换成指定大小
     *
     * @param bitmap Bitmap 位图
     * @param width  int 指定的宽度
     * @param height int 指定的高度
     * @return bitmap Bitmap 位图
     */
    public Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * 图片按比例大小压缩方法
     *
     * @param srcPath   String 图片路径
     * @param maxWidth  float 指定的最大宽度
     * @param maxHeight float 指定的最大高度
     * @return bitmap Bitmap 位图
     */
    public Bitmap getImageFromPath(String srcPath, float maxWidth, float maxHeight) {
        /*if (!isFileAtPath(srcPath)) {
            return null;
        }*/
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            Log.d("getImageFromPath", "bSize:newOpts.out.w=" + w + " h=" + h);

            float aBili = (float) maxHeight / (float) maxWidth;
            float bBili = (float) h / (float) w;
            // be=1表示不缩放，be=2代表大小变成原来的1/2，注意be只能是2的次幂，即使算出的不是2的次幂，使用时也会自动转换成2的次幂
            int be = 1;
            if (aBili > bBili) {
                if (w > maxWidth) {
                    be = (int) (w / maxWidth);
                }
            } else {
                if (h > maxHeight) {
                    be = (int) (h / maxHeight);
                }
            }
            if (be <= 1) {//如果是放大，则不放大
                be = 1;
            }
            newOpts.inSampleSize = be;// 设置缩放比例
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

            int degree = readPictureDegree(srcPath);
            if (degree != 0) {
                bitmap = rotatingBitmap(degree, bitmap);
            }
            if (bitmap == null) {
                return null;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从资源文件中获取图片，按比例大小压缩方法
     *
     * @param res       Resources 资源对象
     * @param resId     int 图片路径
     * @param reqWidth  float 指定的目标宽度
     * @param reqHeight float 指定的目标高度
     * @return bitmap Bitmap 位图
     */
    public Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //可以只获取宽高而不加载
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //计算压缩比例
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 图片压缩处理（使用Options的方法）
     *
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @使用方法 首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片。
     * 然后将Options连同期望的宽度和高度一起传递到到本方法中。
     * 之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。
     * @explain BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
     * ，这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数
     * ，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
     * ，返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、
     * outHeight和outMimeType属性都会被赋值。
     */
    public BitmapFactory.Options calculateInSampleSize(
            final BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        // 设置压缩比例
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }

    /**
     * 提取图像Alpha位图
     *
     * @param mBitmap Bitmap 原位图
     * @param mColor  int 指定的颜色
     * @return bitmap Bitmap 位图
     */
    public Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
        // BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
        // 注意这两个方法的区别
        //Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(mColor);
        // 从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        // 在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path String 图片绝对路径
     * @return int degree 旋转的角度
     */
    public int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 保存图片为PNG
     *
     * @param bitmap Bitmap 原图
     * @param name   String 文件名称
     */
    public void savePNG_After(Bitmap bitmap, String name) {
        File file = new File(name);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片为JPEG
     *
     * @param bitmap  Bitmap 原图
     * @param path    String 文件路径
     * @param quality int 质量0-100
     * @return false boolean 保存是否成功
     */
    public boolean saveJPGE_After(Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 保存图片为JPEG
     *
     * @param context Context 上下文
     * @param bitmap  Bitmap 原图
     * @param path    String 文件路径
     * @param quality int 质量0-100
     */
    public void saveJPGE_After(Context context, Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
            updateResources(context, file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片为PNG
     *
     * @param context Context 上下文
     * @param bitmap  Bitmap 原图
     * @param path    String 文件路径
     * @param quality int 质量0-100
     */
    public void saveJPGE_After_PNG(Context context, Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, out)) {
                out.flush();
                out.close();
            }
            updateResources(context, file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片为PNG
     *
     * @param context Context 上下文
     * @param bitmap  Bitmap 原图
     * @param path    String 文件路径
     * @param quality int 质量0-100
     */
    public void saveJPGE_After_WebP(Context context, Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.WEBP, quality, out)) {
                out.flush();
                out.close();
            }
            updateResources(context, file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件
     *
     * @param file File 文件对象
     */
    private void makeDir(File file) {
        File tempPath = new File(file.getParent());
        if (!tempPath.exists()) {
            tempPath.mkdirs();
        }
    }

    /**
     * 图片合成
     *
     * @param src       Bitmap 原图
     * @param watermark Bitmap 原图
     * @return bitmap Bitmap 合成后的图片
     */
    public Bitmap createBitmap(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

    /**
     * Bitmap转Drawable
     *
     * @param bitmap Bitmap 位图
     * @return drawable Drawable 图片
     */
    public Drawable bitmapToDrawableByBD(Bitmap bitmap) {
        @SuppressWarnings("deprecation")
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    /**
     * 将图片转换成byte[]以便能将其存到数据库
     *
     * @param bitmap Bitmap 位图
     * @return b byte 字节数组
     */
    public byte[] getByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Log.e(TAG, "transform byte exception");
        }
        return out.toByteArray();
    }

    /**
     * 将二进制图片转换成位图
     *
     * @param temp byte 字节数组
     * @return bitmap Bitmap 位图
     */
    public Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            // Bitmap bitmap=BitmapFactory.decodeResource(getResources(),
            // R.drawable.contact_add_icon);
            return null;
        }
    }

    /**
     * 将文件转换为Bitmap类型
     *
     * @param file File 文件对象
     * @return bitmap Bitmap 位图
     */
    public Bitmap getBitmapFromFile(File file) {
        if (!file.exists())
            return null;

        try {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取图片字节
     *
     * @param path 文件路径
     * @return bytes 字节数组
     */
    public byte[] getBytes(String path) {
        File file = new File(path);
        if (!file.exists())
            return null;

        Bitmap bitmap = getBitmapFromFile(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        return bytes;
    }

    /**
     * 镜像水平翻转
     *
     * @param bmp Bitmap 原位图
     * @return bitmap Bitmap 翻转后的位图
     */
    public Bitmap convertMirrorBmp(Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 镜像水平翻转
        Bitmap convertBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);

        return convertBmp;
    }

    /**
     * 垂直翻转
     *
     * @param bmp Bitmap 原位图
     * @return bitmap Bitmap 翻转后的位图
     */
    public Bitmap convertVertical(Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(1, -1); // 镜像垂直翻转
        Bitmap convertBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);

        return convertBmp;
    }

    /**
     * 将图片文件转换为Bitmap类型
     *
     * @param path         期望宽高
     * @param screenWidth  int 设定的宽度
     * @param screenHeight int 设定的高度
     * @return bitmap Bitmap 翻转后的位图
     */
    public Bitmap decodeFile(String path, int screenWidth, int screenHeight) {
        Bitmap bm = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        //这个isjustdecodebounds很重要
        opt.inJustDecodeBounds = true;
        bm = BitmapFactory.decodeFile(path, opt);

        //获取到这个图片的原始宽度和高度
        int picWidth = opt.outWidth;
        int picHeight = opt.outHeight;

        //isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
        opt.inSampleSize = 1;
        //根据屏的大小和图片大小计算出缩放比例
        if (picWidth > picHeight) {
            if (picWidth > screenWidth)
                opt.inSampleSize = picWidth / screenWidth;
        } else {
            if (picHeight > screenHeight)
                opt.inSampleSize = picHeight / screenHeight;
        }

        //这次再真正地生成一个有像素的，经过缩放了的bitmap
        opt.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, opt);
        return bm;
    }

    /**
     * 把资源图片转换成Bitmap
     *
     * @param drawable Drawable 资源图片对象
     * @return bitmap Bitmap 翻转后的位图
     */
    public Bitmap getBitmapFromDrawable(Drawable drawable) {
        int width = drawable.getBounds().width();
        int height = drawable.getBounds().height();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 把被系统旋转了的图片，转正
     *
     * @param angle int 旋转角度
     * @return bitmap Bitmap 图片
     */
    public Bitmap rotatingBitmap(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 翻转
     *
     * @param bitmap Bitmap 原位图
     * @return bitmap Bitmap 翻转后的位图
     */
    public Bitmap flip(Bitmap bitmap) {
        // 点中了翻转
        Matrix m = new Matrix();
        m.postScale(-1, 1);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        return bitmap;
    }

    /**
     * 截图操作，将View转为Bitmap
     *
     * @param view View 视图控件
     * @return bitmap Bitmap 位图
     */
    public Bitmap getViewBitmap(View view) {
        view.clearFocus(); // 清除视图焦点
        view.setPressed(false);// 将视图设为不可点击

        boolean willNotCache = view.willNotCacheDrawing();// 返回视图是否可以保存他的画图缓存
        view.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation //将视图在此操作时置为透明
        int color = view.getDrawingCacheBackgroundColor();// 获得绘制缓存位图的背景颜色
        view.setDrawingCacheBackgroundColor(0);// 设置绘图背景颜色

        if (color != 0) {// 如果获得的背景不是黑色的则释放以前的绘图缓存
            view.destroyDrawingCache();// 释放绘图资源所使用的缓存
        }
        view.buildDrawingCache();// 重新创建绘图缓存，此时的背景色是黑色
        Bitmap cacheBitmap = view.getDrawingCache();// 将绘图缓存得到的,注意这里得到的只是一个图像的引用
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(cacheBitmap);// 将位图实例化

        } catch (OutOfMemoryError e) {
            while (bitmap == null) {
                System.gc();
                System.runFinalization();
                bitmap = Bitmap.createBitmap(cacheBitmap);// 将位图实例化
            }
        }


        view.destroyDrawingCache();// Restore the view //恢复视图
        view.setWillNotCacheDrawing(willNotCache);// 返回以前缓存设置
        view.setDrawingCacheBackgroundColor(color);// 返回以前的缓存颜色设置

        return bitmap;
    }


    /**
     * 将View转为Bitmap
     *
     * @param view View 视图控件
     * @return bitmap Bitmap 位图
     */
    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 将View转为Bitmap
     *
     * @param view View 视图控件
     * @return bitmap Bitmap 位图
     */
    public Bitmap getBitmapFromView(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return bitmap;
    }

    /**
     * 普通视图截图View
     *
     * @param activity Activity 上下文
     * @param view     View 需要截图的view
     * @return bitmap Bitmap 输出位图
     */
    public Bitmap screenshotView(Activity activity, View view) {
        // 提交时，进行一个截图
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache(true);
        // 开始截图
        Bitmap drawingCache = decorView.getDrawingCache();

        // 获取实际显示范围
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);

        // 指定区域范围
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Bitmap bitmap = Bitmap.createBitmap(drawingCache, location[0], location[1], view.getWidth(), view.getHeight());

        // 清除缓存
        decorView.destroyDrawingCache();

        return bitmap;

    }

    /**
     * ListView截图View
     *
     * @param activity Activity 上下文
     * @param listview ListView 视图控件
     * @return bitmap Bitmap 输出位图
     */
    public Bitmap getWholeListViewItemsToBitmap(Activity activity, ListView listview) {
        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<Bitmap>();

        for (int i = 0; i < itemscount; i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();
            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
        }

        Bitmap bigbitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);
        bigcanvas.drawColor(Color.WHITE);

        listview.draw(bigcanvas);

//        Paint paint = new Paint();
//        int iHeight = 0;
//
//        for (int i = 0; i < bmps.size(); i++) {
//            Bitmap bmp = bmps.get(i);
//            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
//            iHeight += bmp.getHeight();
//
//            bmp.recycle();
//            bmp = null;
//        }


        return bigbitmap;
    }

    /**
     * 获取视频的缩略图
     *
     * @param path String 视频路径
     * @return bitmap Bitmap 位图
     */
    public Bitmap getBitmapFromVideo(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

    /**
     * 更新资源
     *
     * @param context Context 上下文
     * @param path    String 文件路径
     */
    public void updateResources(Context context, String path) {
        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
    }

    /**
     * 设置bitmap饱和度
     *
     * @param context      Context 上下文
     * @param bitmap       Bitmap 原位图
     * @param screenWidth  int 指定宽度
     * @param screenHeight int 指定高度
     * @return bitmap Bitmap 位图
     */
    public Bitmap saturationBitmap(Context context, Bitmap bitmap, int screenWidth, int screenHeight) {
        Bitmap bmp = null;
/*
        int maxWidth = MyApplication.getInstance().getScreenWidth() - SystemUtils.dp2px(context, 20);
        int maxHeight = maxWidth * 4 / 3;*/

        int reqWidth = screenWidth - SystemUtils.dp2px(context, 20);
        int reqHeight = reqWidth * 4 / 3;

        bmp = createBitmap(bitmap, reqWidth, reqHeight);

        ColorMatrix cMatrix = new ColorMatrix();
        // 设置饱和度
        cMatrix.setSaturation(0.0f);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bmp;
    }

    /**
     * 创建期望大小的bitmap
     *
     * @param bitmap   int 期望宽度
     * @param reqWidth int 期望高度
     * @return bitmap Bitmap 输出位图
     */
    public Bitmap createBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        Bitmap bmp = null;
        int inSampleSize = 0;

        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();

        if (bHeight > reqHeight || bWidth > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) bHeight
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) bWidth / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        try {
            if (inSampleSize != 0) {
                bmp = Bitmap.createBitmap(bWidth / inSampleSize, bHeight / inSampleSize, Config.ARGB_8888);
            } else {
                bmp = Bitmap.createBitmap(bWidth, bHeight, Config.ARGB_8888);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            while (bmp == null) {
                System.gc();
                System.runFinalization();
                if (inSampleSize != 0) {
                    bmp = Bitmap.createBitmap(bWidth / inSampleSize, bHeight / inSampleSize, Config.ARGB_8888);
                } else {
                    bmp = Bitmap.createBitmap(bWidth, bHeight, Config.ARGB_8888);
                }
            }
        }

        return bmp;
    }

    /**
     * 圆形Bitmap
     *
     * @param bitmap Bitmap 原位图
     * @return bitmap Bitmap 输出圆形位图
     */
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPX = bitmap.getWidth() / 2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outBitmap;
    }

    /**
     * 改变bitmap 对比度
     *
     * @param bitmap   Bitmap 原位图
     * @param progress int 对比度0-100
     * @return bitmap Bitmap 输出圆形位图
     */
    public Bitmap returnContrastBitmap(Bitmap bitmap, int progress) {
        // 曝光度
        Bitmap contrast_bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Config.ARGB_8888);
        // int brightness = progress - 127;
        float contrast = (float) ((progress + 64) / 128.0);
        ColorMatrix contrast_cMatrix = new ColorMatrix();
        contrast_cMatrix.set(new float[]{contrast, 0, 0, 0, 0, 0,
                contrast, 0, 0, 0,// 改变对比度
                0, 0, contrast, 0, 0, 0, 0, 0, 1, 0});

        Paint contrast_paint = new Paint();
        contrast_paint.setColorFilter(new ColorMatrixColorFilter(contrast_cMatrix));

        Canvas contrast_canvas = new Canvas(contrast_bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        contrast_canvas.drawBitmap(bitmap, 0, 0, contrast_paint);

        return contrast_bmp;
    }

    /**
     * 把文字画在图片的右下角
     * @param activity 上下文
     * @param bitmap 原位图
     * @param text 文字
     * @return bitmap 新位图
     */
    public Bitmap drawTextToBitmap(Activity activity, Bitmap bitmap, String text) {
        if (TextUtils.isEmpty(text))
            return bitmap;

        Resources resources = activity.getResources();
        float scale = resources.getDisplayMetrics().density;

        Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = Config.ARGB_8888;
        }

        // 获取图片的宽高
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();

        Log.e("manager", "srcWidth=" + srcWidth + ";srcHeight=" + srcHeight);

        Bitmap copyBitmap = Bitmap.createBitmap(srcWidth, srcHeight, bitmapConfig);
        Canvas canvas = new Canvas(copyBitmap);
        // 在 0，0坐标载入图片
        canvas.drawBitmap(bitmap, 0, 0, null);

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        // 起始位置是左边
        textPaint.setTextAlign(Paint.Align.LEFT);
        // 这里没有做粉分辨率适配，在有的手机上显示很小
        textPaint.setTextSize((int) (12 * scale));
        textPaint.setAntiAlias(true);

        // 分割字符串
        String[] split = text.split(";");
        String text1 = split[0];
        String text2 = split[1];

        // 计算字符串占的矩形大小
        Rect rect = new Rect();
        textPaint.getTextBounds(text1, 0, text1.length(), rect);
        int textWidth1 = rect.width();
        int textHeight1 = rect.height();

        // 第二行的宽高
        textPaint.getTextBounds(text2, 0, text2.length(), rect);
        int textWidth2 = rect.width();
        int textHeight2 = rect.height();

        // X坐标
        int dx = 0;
        // 比较哪行占的宽度更多
        if (textWidth1 > textWidth2) {
            dx = srcWidth - textWidth1 - 10;
        } else {
            dx = srcWidth - textWidth2 - 10;
        }

        int dy1 = srcHeight - 100;
        int dy2 = srcHeight + textHeight1 - 80;

        canvas.drawText(text1, dx, dy1, textPaint);
        canvas.drawText(text2, dx, dy2, textPaint);

        Log.e("manager", "dx=" + dx + ";dy1=" + dy1);

        // 保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();

        return copyBitmap;
    }

}