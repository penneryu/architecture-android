package com.penner.architecture.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.penner.architecture.util.LogUtils;

import java.util.Locale;

/**
 * Created by penneryu on 16/9/12.
 */
public class AsyncImageView extends AppCompatImageView {

    protected Context mContext;
    protected GlideLoadListener mListener;

    public AsyncImageView(Context context) {
        super(context, null);
    }

    public AsyncImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsyncImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;
        mListener = new GlideLoadListener();
    }

    public void setImageUrl(String imageUri) {
        setImageUrl(imageUri, 0);
    }

    public void setImageUrl(final String imageUri, int defaultImage) {
        Activity activity = (Activity) this.mContext;
        if (activity.isFinishing()) return;
        AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
        Drawable defaultDrawable = defaultImage > 0 ? drawableManager.getDrawable(this.mContext, defaultImage) : null;

        if (TextUtils.isEmpty(imageUri)) {
            Glide.with(this.mContext).load("").placeholder(defaultDrawable).error(defaultDrawable).into(this);
        } else {
            loadImg(imageUri, defaultDrawable);
        }
    }

    protected void loadImg(final String imageUri, Drawable defaultDrawable) {
        Glide.with(mContext).load(imageUri).listener(mListener)
                .placeholder(defaultDrawable).error(defaultDrawable).thumbnail(0.3f).into(this);
    }

    public static class GlideLoadListener implements RequestListener<String, GlideDrawable> {

        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            if (target == null)  {
                return true;
            }

            LogUtils.e("GLIDE", String.format(Locale.ROOT,
                    "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean
                isFromMemoryCache, boolean isFirstResource) {
            LogUtils.d("GLIDE", String.format(Locale.ROOT,
                    "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
            return false;
        }
    }
}
