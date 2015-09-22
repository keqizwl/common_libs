package zwl.magic.com.common.common.bundlehelper;

/**
 * Created by weilong zhou on 2015/7/27.
 * Email:zhouwlong@gmail.com
 */
public abstract class BaseBundleHelper<T> {
    public abstract T get();

    public abstract void put(T t);
}
