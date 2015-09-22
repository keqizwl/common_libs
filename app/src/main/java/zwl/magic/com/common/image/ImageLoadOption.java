package zwl.magic.com.common.image;

/**
 * Created by weilong zhou on 2015/9/18.
 * Email:zhouwlong@gmail.com
 */
public class ImageLoadOption {
    private int defaultDrawableId;
    private int errorDrawableId;

    public int getErrorDrawableId() {
        return errorDrawableId;
    }

    public int getDefaultDrawableId() {
        return defaultDrawableId;
    }

    private ImageLoadOption() {
    }

    public static ImageLoadOption createOption() {
        return new ImageLoadOption();
    }

    public ImageLoadOption setDefaultDrawableId(int defaultDrawableId) {
        this.defaultDrawableId = defaultDrawableId;
        return this;
    }

    public ImageLoadOption setErrorDrawableId(int errorDrawableId) {
        this.errorDrawableId = errorDrawableId;
        return this;
    }
}
