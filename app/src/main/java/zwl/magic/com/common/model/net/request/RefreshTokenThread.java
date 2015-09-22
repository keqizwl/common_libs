package zwl.magic.com.common.model.net.request;


import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.ishowedu.child.peiyin.model.entity.RefreshToken;
import com.ishowedu.child.peiyin.model.net.base.HttpException;

/**
 * Created by weilong zhou on 2015/7/30.
 * Email:zhouwlong@gmail.com
 */
public abstract class RefreshTokenThread extends Thread {

    public RefreshTokenThread() {
    }

    @Override
    public void run() {
        RefreshToken refreshToken = null;
        try {
            refreshToken = NetInterface.getInstance().refreshToken(IShowDubbingApplication.getInstance());
        } catch (HttpException e) {
            e.printStackTrace();
            OnRefreshError(e.getErrorCode(), e.getMessage());
        }

        if (refreshToken != null) {
            OnRefreshSuccess(refreshToken);
        }
    }

    public abstract void OnRefreshSuccess(RefreshToken refreshToken);

    public abstract void OnRefreshError(int errorCode, String msg);
}
