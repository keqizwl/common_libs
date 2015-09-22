package zwl.magic.com.common.image;

import android.text.TextUtils;
import android.widget.ImageView;

/**
 * Created by weilong zhou on 2015/9/18.
 * Email:zhouwlong@gmail.com
 */
public abstract class ImageLoader {
    private static ImageLoader instance;

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new UniversalImageLoader();
                }
            }
        }
        return instance;
    }

    ImageLoader() {
    }

    public ImageContainer get(ImageView imageView, String url, int defaultDrawableId, int errorDrawableId) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return null;
        }

        ImageContainer imageContainer = new ImageContainer(imageView, url, defaultDrawableId, errorDrawableId);
        imageContainer.preLoad();
        load(imageContainer);
        return imageContainer;
    }

    public abstract void cancel(ImageContainer imageContainer);

    public abstract void cancelAll();

    protected abstract void load(ImageContainer imageContainer);

}
