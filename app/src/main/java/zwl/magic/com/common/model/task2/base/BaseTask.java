package zwl.magic.com.common.model.task2.base;

import android.os.Looper;

import com.ishowedu.child.peiyin.common.state.TaskState;
import com.ishowedu.child.peiyin.model.entity.User;
import com.ishowedu.child.peiyin.model.net.request.Server;
import com.ishowedu.child.peiyin.model.proxy.UserProxy;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by weilong zhou on 2015/9/16.
 * Email:zhouwlong@gmail.com
 */
public abstract class BaseTask {
    private static final String DEFALUT_REFRESH_TOKEN = "MTQzNDUzNzcyNLCHyGKAnqKh";

    public static final int REQUEST_TYPE_GET = 0;
    public static final int REQUEST_TYPE_POST = 1;

    private LoadingWaitter mLoadingWaitter;
    private OnLoadFinishListener mOnLoadFinishListener;
    protected String taskName;
    protected int mType;
    protected String mUrl;
    protected Map<String, String> mParams;

    private TaskState mState;

    private boolean isTaskCanceled;

    public BaseTask(int type, String url, Map<String, String> params) {
        mType = type;
        mUrl = url;
        mParams = params;
        mState = new TaskState();
    }

    public final void preLoading() {
        isTaskCanceled = false;

        if (checkIsMainLooper()) {
            if (mLoadingWaitter != null) {
                mLoadingWaitter.preLoading();
            }
        }
    }

    public final void endLoading(Object t) {
        if (checkIsMainLooper()) {
            if (mLoadingWaitter != null) {
                mLoadingWaitter.endLoading();
            }

            if (!isTaskCanceled) {
                if (mOnLoadFinishListener != null) {
                    mOnLoadFinishListener.onLoadFinishListener(taskName, t);
                }
            }
        }
    }

    private boolean checkIsMainLooper() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public void setmOnLoadFinishListener(OnLoadFinishListener mOnLoadFinishListener) {
        this.mOnLoadFinishListener = mOnLoadFinishListener;
    }

    public void setmLoadingWaitter(LoadingWaitter mLoadingWaitter) {
        this.mLoadingWaitter = mLoadingWaitter;
    }

    public void cancel() {
        isTaskCanceled = true;

        endLoading(null);
    }

    public String getTaskName() {
        return taskName;
    }

    public int getmType() {
        return mType;
    }

    public String getmUrl() {
        return mUrl;
    }

    public Map<String, String> getmParams() {
        return mParams;
    }


    public void setState(int stateCode) {
        mState.setState(stateCode);
    }

    public static final String getUrlFromGetUrlAndParams(String url, List<NameValuePair> params) {
        if (params == null) {
            params = new ArrayList<NameValuePair>();
        }
        User user = UserProxy.getInstance().getUser();
        params.add(new BasicNameValuePair("uid", String.valueOf(user.uid)));
        params.add(new BasicNameValuePair("auth_token", user.auth_token));
        return Server.getUrl(url, params);
    }
}
