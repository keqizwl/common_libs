package zwl.magic.com.common.image;


/**
 * Created by weilong zhou on 2015/9/18.
 * Email:zhouwlong@gmail.com
 */
public interface ImageListener {
    public abstract void onResponse(ImageContainer imageContainer);

    public abstract void onErrorResponse(ImageContainer imageContainer);
}
