package com.bb.kanjuzi.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bb.kanjuzi.global.App;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Boby on 2019/6/18.
 */

public class GlideUtils {
    private static Context mContext = App.getContext();
    public static void play(ImageView view, String url) {
        Glide.with(mContext).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.e("onLoadFailed", "onLoadFailed: " + e.getMessage() );
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(view);
    }

    public static void blur(ImageView view, int url, int radius) {
        Glide.with(mContext).load(url).transform(new BlurTransformation(radius)).into(view);
    }

    public static void blur(ImageView view, String url, int radius, final Runnable runnable) {
        Glide.with(mContext).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (runnable != null) {
                    runnable.run();
                }
                return false;
            }
        }).transform(new BlurTransformation(radius)).into(view);
    }

    public static void round(ImageView imageView, int url, int radius, boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        RoundCornersTransform transform = new RoundCornersTransform(mContext, UiUtils.dp2Px(radius));
        transform.setNeedCorner(leftTop, rightTop, leftBottom, rightBottom);
        RequestOptions options = new RequestOptions().transform(transform);
        Glide.with(mContext).load(url).apply(options).into(imageView);
    }
}
