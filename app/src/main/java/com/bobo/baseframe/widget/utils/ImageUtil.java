package com.bobo.baseframe.widget.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;


/**
 * @ClassName ImageUtil
 * @Description 图片工具
 */
public class ImageUtil {

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, 0, 0, url, imageView);
    }

    public static void loadImage(Context context, @DrawableRes int placeholder,
                                 String url, ImageView imageView) {
        loadImage(context, placeholder, 0, url, imageView);
    }

    public static void loadImage(Context context, @DrawableRes int placeholder,
                                 @DrawableRes int error, String url, ImageView imageView) {
        try {
            Glide.with(context)
                    .load(url)
                    .apply(getCenterCropOptions(placeholder, error))
                    .into(imageView);
        } catch (Exception e) {
            //避免context被回收导致的异常
        }
    }

    public static void clear(ImageView imageView) {
        Glide.with(imageView.getContext()).clear(imageView);
    }

    public static void loadImage(Context context, String url, SimpleTarget<Drawable> taget) {
        loadImage(context, 0, 0, url, taget);
    }

    public static void loadImage(Context context, @DrawableRes int placeholder,
                                 String url, SimpleTarget<Drawable> taget) {
        loadImage(context, placeholder, 0, url, taget);
    }

    public static void loadImage(Context context, @DrawableRes int placeholder,
                                 @DrawableRes int error, String url, SimpleTarget<Drawable> taget) {
        Glide.with(context)
                .load(url)
                .apply(getCenterCropOptions(placeholder, error))
                .into(taget);
    }

    @NonNull
    private static RequestOptions getCenterCropOptions(@DrawableRes int placeholder, @DrawableRes int error) {
        return new RequestOptions()
                // 加载成功之前占位图
                .placeholder(placeholder)
                // 加载错误之后的错误图
                .error(error)
                //                .override(imageView.getMeasuredWidth(), imageView.getMeasuredHeight())
                // 指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者高等于ImageView的宽或者高。）
                //                .fitCenter()
                // 指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
                // 跳过内存缓存
                //                .skipMemoryCache(true)
                // ALL: 缓存所有, NONE: 跳过内存缓存, DATA: 只缓存原来分辨率的图片, RESOURCE: 只缓存最终的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static void loadImageFitCenter(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(getFitCenterOptions(0))
                .into(imageView);
    }

    @NonNull
    private static RequestOptions getFitCenterOptions(@DrawableRes int placeholder) {
        return new RequestOptions()
                .placeholder(placeholder)
                // 指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者高等于ImageView的宽或者高。）
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static void loadImage(Context context, String url, RequestListener<Drawable> requestListener,
                                 ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(context)
                .load(url)
                .listener(requestListener)
                .apply(options)
                .into(imageView);
    }



    public static void loadImageCircle(Context context, @DrawableRes int placeholder,
                                       String url, ImageView imageView) {
        RequestOptions options = RequestOptions.circleCropTransform()
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width == 0 || height == 0 || w == 0 || h == 0) {
            return drawable;
        }
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(null, newbmp);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 缩放图片
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        /*
         * 通过Matrix类的postScale方法进行缩放
         */
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap getBitmapFromView(View v) {
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        v.draw(c);
        return bmp;
    }
}
