package zwl.magic.com.common.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Administrator on 2015/7/2.
 */
public class UniversalImageLoader extends com.ishowedu.child.peiyin.activity.image.ImageLoader {
    private ImageLoader mImageLoader;

    public UniversalImageLoader() {
        initImageLoader();
    }

    @Override
    public void cancel(ImageContainer imageContainer) {

    }

    @Override
    public void cancelAll() {

    }

    @Override
    protected void load(ImageContainer imageContainer) {
        mImageLoader.displayImage(imageContainer.getUrl(), imageContainer.getImageView(), getOptionsFromResIds(imageContainer), getImageloadListener(imageContainer));
    }

    private DisplayImageOptions getOptionsFromResIds(ImageContainer imageContainer) {
        return new DisplayImageOptions.Builder().showStubImage(imageContainer.getImageLoadOption().getDefaultDrawableId())
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(imageContainer.getImageLoadOption().getDefaultDrawableId())
                        // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(imageContainer.getImageLoadOption().getErrorDrawableId())
                        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)
                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public ImageLoadingListener getImageloadListener(final ImageContainer imageContainer) {
        if (imageContainer == null) {
            return null;
        }
        return new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                imageContainer.preLoad();
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                imageContainer.onLoadFinish(null);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                BitmapDrawable drawable = null;
                if (bitmap != null) {
                    drawable = new BitmapDrawable(bitmap);
                }
                imageContainer.onLoadFinish(drawable);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        };
    }

    public void initImageLoader() {
        mImageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(IShowDubbingApplication.getInstance()).threadPriority(Thread.NORM_PRIORITY - 2)
                        .threadPoolSize(3)
                        .denyCacheImageMultipleSizesInMemory()
                        .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                                //.writeDebugLogs() // Remove for release app
                        .memoryCache(new WeakMemoryCache())
                        .build();
        // Initialize ImageLoader with configuration.
        mImageLoader.init(config);
    }
}
