package zwl.magic.com.common.image;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DefaultImageCache;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.ishowedu.child.peiyin.util.CLog;

/**
 * Created by weilong zhou on 2015/9/18.
 * Email:zhouwlong@gmail.com
 */
public class VolleyImageLoader extends ImageLoader {
    private static final String TAG = "VolleyImageLoader";
    private RequestQueue mQueue;
    private com.android.volley.toolbox.ImageLoader mImageLoader;

    public VolleyImageLoader() {
        mQueue = Volley.newRequestQueue(IShowDubbingApplication.getInstance());
        mImageLoader = new com.android.volley.toolbox.ImageLoader(mQueue, DefaultImageCache.getInstance(new DefaultImageCache.ImageCacheParams()), IShowDubbingApplication.getInstance());
    }

    @Override
    public void cancel(ImageContainer imageContainer) {
        if (imageContainer.getTag() != null && imageContainer.getTag() instanceof com.android.volley.toolbox.ImageLoader.ImageContainer) {
            ((com.android.volley.toolbox.ImageLoader.ImageContainer) imageContainer.getTag()).cancelRequest();
            imageContainer.setTag(null);
        }
        imageContainer.cancel();
    }

    @Override
    public void cancelAll() {
        mQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                if (ImageRequest.class.isInstance(request)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    protected void load(ImageContainer imageContainer) {
        com.android.volley.toolbox.ImageLoader.ImageContainer container = mImageLoader.get(imageContainer.getUrl(), Request.SourceType.SOURCE_TYPE_WEB, createImageListenerFromContainer(imageContainer));
        imageContainer.setTag(container);
    }

    private com.android.volley.toolbox.ImageLoader.ImageListener createImageListenerFromContainer(final ImageContainer imageContainer) {
        return new com.android.volley.toolbox.ImageLoader.ImageListener() {
            @Override
            public void onResponse(com.android.volley.toolbox.ImageLoader.ImageContainer response, boolean isImmediate) {
                CLog.v(TAG, "onResponse");
                if (response != null) {
                    imageContainer.onLoadFinish(response.getBitmapDrawable());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                imageContainer.onLoadFinish(null);
                CLog.v(TAG, error.getMessage());
            }
        };
    }
}
