package zwl.magic.com.common.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.volley.toolbox.*;
import com.ishowedu.child.peiyin.util.CLog;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by weilong zhou on 2015/9/18.
 * Email:zhouwlong@gmail.com
 */
public class ImageContainer {
    private static final String TAG = "ImageContainer";

    private Object tag;

    private String mUrl;
    private ImageView miv;

    private ImageLoadOption mImageLoadOption;
    private List<ImageListener> mImageListeners;

    private boolean isCancel;

    public ImageContainer(ImageView imageView, String url, int defaultDrawableId, int errorDrawableId) {
        miv = imageView;
        mUrl = url;
        mImageLoadOption = ImageLoadOption.createOption().setDefaultDrawableId(defaultDrawableId).setErrorDrawableId(errorDrawableId);
    }

    public void preLoad() {
        if (miv != null)
            miv.setImageResource(mImageLoadOption.getDefaultDrawableId());
    }

    public void onLoadFinish(Drawable drawable) {
        CLog.v(TAG, "isCancel = " + isCancel);
        if (!isCancel && miv != null) {
            if (drawable == null) {
                miv.setImageResource(mImageLoadOption.getErrorDrawableId());
            } else {
                miv.setImageDrawable(drawable);
            }
        }
        if (mImageListeners != null) {
            for (ImageListener imageListener : mImageListeners) {
                if (drawable != null) {
                    imageListener.onResponse(this);
                } else {
                    imageListener.onErrorResponse(this);
                }
            }
        }
    }

    public final void cancel() {
        isCancel = true;
    }

    public final boolean isCancel() {
        return isCancel;
    }

    public String getUrl() {
        return mUrl;
    }

    public ImageView getImageView() {
        return miv;
    }

    public ImageLoadOption getImageLoadOption() {
        return mImageLoadOption;
    }

    public void addImageListener(ImageListener listener) {
        if (listener != null) {
            if (mImageListeners == null) {
                mImageListeners = new LinkedList<ImageListener>();
            }
            mImageListeners.add(listener);
        }
    }

    public void setImageLoadOption(ImageLoadOption imageLoadOption) {
        if (imageLoadOption != null) {
            mImageLoadOption = imageLoadOption;
        }
    }

    public void setTag(Object o) {
        tag = o;
    }

    public Object getTag() {
        return tag;
    }
}
