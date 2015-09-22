package zwl.magic.com.common.control.authority;

import android.text.TextUtils;

import com.ishowedu.child.peiyin.control.authority.listener.OnAuthorFinishListener;
import com.ishowedu.child.peiyin.model.entity.User;

/**
 * Created by weilong zhou on 2015/8/26.
 * Email:zhouwlong@gmail.com
 */
public class UserAuthority extends BaseAuthority<User> {
    public static final int AUTHORITY_VISITOR_USER = 0;
    public static final int AUTHORITY_THIRD_PARTY_USER = 1;
    public static final int AUTHORITY_NORMAL_USER = 2;

    private static UserAuthority instance;

    public static UserAuthority getInstance() {
        if (instance == null) {
            synchronized (UserAuthority.class) {
                if (instance == null) {
                    instance = new UserAuthority();
                }
            }
        }
        return instance;
    }

    private UserAuthority() {

    }

    public int getAuthority(User user) {
        if (user == null || TextUtils.isEmpty(user.type)) {
            return AUTHORITY_VISITOR_USER;
        }

        String type = user.type;
        if (type.equals(User.TYPE_GUESTER)) {
            return AUTHORITY_VISITOR_USER;
        } else if (type.contains(User.TYPE_MOBILE)) {
            return AUTHORITY_NORMAL_USER;
        } else {
            return AUTHORITY_THIRD_PARTY_USER;
        }
    }

    public boolean checkAuthority(User user, int requireAuthority, OnAuthorFinishListener listener) {
        int authoerType = getAuthority(user);

        boolean allow = requireAuthority <= authoerType;
        if (listener != null) {
            listener.onAuthorFinish(authoerType, allow);
        }
        return allow;
    }
}
