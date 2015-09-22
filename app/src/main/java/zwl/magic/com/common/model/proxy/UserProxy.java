package zwl.magic.com.common.model.proxy;

import com.google.gson.reflect.TypeToken;
import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.ishowedu.child.peiyin.common.Constants;
import com.ishowedu.child.peiyin.model.entity.User;
import com.ishowedu.child.peiyin.util.OtherUtils;

/**
 * Created by weilong zhou on 2015/8/26.
 * Email:zhouwlong@gmail.com
 */
public class UserProxy {
    private static UserProxy instance;

    private User mUser;

    private UserProxy() {

    }

    public static UserProxy getInstance() {
        if (instance == null) {
            synchronized (UserProxy.class) {
                if (instance == null) {
                    instance = new UserProxy();
                }
            }
        }

        return instance;
    }

    public User getUser() {
        if (mUser == null) {
            try {
                mUser = (User) OtherUtils.getObjectFromJsonSharePrefs(IShowDubbingApplication.getInstance(), Constants.FILE_JSON_CACHE, Constants.KEY_USER,
                        new TypeToken<User>() {
                        }.getType());
            } catch (Exception e) {
            }
        }

        return mUser;
    }

    public void setUser(User user) {
        if (user != null) {
            getUser();
            if (mUser == null) {
                mUser = new User();
            }
            mUser.copyFromOtherUser(user);
        }else{
            mUser = null;
        }

        OtherUtils.switchObjectToJsonAndSaveToSharePrefs(IShowDubbingApplication.getInstance()
                , Constants.FILE_JSON_CACHE, Constants.KEY_USER,
                user);
    }
}
