package zwl.magic.com.common.model.net.base;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ishowedu.child.peiyin.IShowDubbingApplication;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by weilong zhou on 2015/9/6.
 * Email:zhouwlong@gmail.com
 */
public class ConnectStateHelper {
    public interface OnConnectityChangedListener {
        public void onConnectityChanged(boolean isConnect, int connectType);
    }

    private static ConnectStateHelper instance;

    private Application mApplication;
    private ConnectivityManager mConnectivityManager;
    private List<OnConnectityChangedListener> mListeners;

    private ConnectStateHelper(Application application) {
        mApplication = application;
        mListeners = new LinkedList<OnConnectityChangedListener>();
        initConnectivityManager();
    }

    private void initConnectivityManager() {
        if (mApplication == null) {
            return;
        }
        mConnectivityManager = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static ConnectStateHelper getInstance() {
        if (instance == null) {
            synchronized (ConnectStateHelper.class) {
                if (instance == null) {
                    instance = new ConnectStateHelper(IShowDubbingApplication.getInstance());
                }
            }
        }
        return instance;
    }

    public void onConnectedChanged() {
        IShowDubbingApplication.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                boolean isConnected = isConnected();
                int connectType = getConnectionType();
                for (OnConnectityChangedListener listener : mListeners) {
                    if (listener != null) {
                        listener.onConnectityChanged(isConnected, connectType);
                    }
                }
            }
        });
    }

    public boolean isConnected() {
        if (mConnectivityManager == null) {
            return false;
        }

        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public int getConnectionType() {
        if (isConnected()) {
            return mConnectivityManager.getActiveNetworkInfo().getType();
        }
        return -1;
    }

    public final void addConnectedListener(OnConnectityChangedListener listener) {
        if (listener != null) {
            mListeners.add(listener);
        }
    }

    public void removeConnectedListener(OnConnectityChangedListener listener) {
        mListeners.remove(listener);
    }

    public void clearConnecteListeners() {
        mListeners.clear();
    }
}
