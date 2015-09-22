package zwl.magic.com.common.control.authority;

import com.ishowedu.child.peiyin.control.authority.listener.OnAuthorFinishListener;

/**
 * Created by weilong zhou on 2015/8/26.
 * Email:zhouwlong@gmail.com
 */
public abstract class BaseAuthority<T> {
    public abstract int getAuthority(T t);

    public abstract boolean checkAuthority(T t, int requireAuthority, OnAuthorFinishListener listener);
}
