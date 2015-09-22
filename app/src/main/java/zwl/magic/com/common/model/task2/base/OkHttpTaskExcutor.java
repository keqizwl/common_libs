package zwl.magic.com.common.model.task2.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feizhu.publicutils.SystemUtils;
import com.feizhu.publicutils.ToastUtils;
import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.ishowedu.child.peiyin.R;
import com.ishowedu.child.peiyin.common.state.TaskState;
import com.ishowedu.child.peiyin.model.net.okhttp.OkHttpStack;
import com.ishowedu.child.peiyin.model.net.request.NetInterface;
import com.ishowedu.child.peiyin.util.CLog;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weilong zhou on 2015/9/16.
 * Email:zhouwlong@gmail.com
 */
public class OkHttpTaskExcutor extends BaseTaskExcutor {
    private static final String TAG = "RequestManager";

    private static final String HEAD_NAME_USER_AGENT = "User-Agent";
    private static final String HEAD_NAME_VERSION_CODE = "versionCode";
    private static final String USER_AGENT = "android";
    private static final String HEAD_NAME_APP_VERSION = "App-Version";
    private static final String HEAD_NAME_CLIENT_OS = "Client-OS";
    private static final String HEAD_NAME_DEVICE_MODEL = "Device-Model";
    private static final String HEAD_NAME_CHANNEL = "Umeng-Channel";
    private static final String UMENG_CHANNEL = "UMENG_CHANNEL";
    private static final String APPLICATION_NAME = "englishtalk_child";
    private static final String HEAD_APP_NAME = "Application_name";

    private Context mContext;
    public RequestQueue mRequestQueue;

    protected OkHttpTaskExcutor() {
        mContext = IShowDubbingApplication.getInstance();
        mRequestQueue = newRequestQueue(mContext);
    }

    private RequestQueue newRequestQueue(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context, new OkHttpStack());
        requestQueue.start();
        return requestQueue;
    }

    @Override
    public void excute(final BaseTask task, final Type type) {
        if (task == null || task.getmUrl() == null) {
            CLog.v(TAG, "task null");
            return;
        }

        task.setState(TaskState.STATE_TASK_UNDO);
        task.preLoading();

        Request request = new StringRequest(getHttpTypeFromTaskType(task.getmType()), task.mUrl, 0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        task.setState(TaskState.STATE_TASK_DONE);
                        //检测
                        if (response == null) {
                            CLog.v(TAG, "response null");
                            ToastUtils.show(mContext, R.string.web_response_null);
                            return;
                        }

                        //解析
                        Object result = null;
                        String errorMsg = null;
                        try {
                            String json = getDataFromResponse(response);
                            result = NetInterface.getGson().fromJson(json, type);
                        } catch (Throwable throwable) {
                            CLog.v(TAG, throwable.getMessage());
                            errorMsg = throwable.getMessage();
                        }

                        //提示
                        if (result == null) {
                            ToastUtils.show(mContext, (errorMsg == null ? mContext.getString(R.string.web_response_parse_error) : errorMsg));
                        }

                        //处理结果
                        task.endLoading(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        task.setState(TaskState.STATE_TASK_DONE);
                        //提示
                        ToastUtils.show(mContext, R.string.error_no_network_text);
                        task.endLoading(null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return task.mParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };
        mRequestQueue.add(request);
        task.setState(TaskState.STATE_TASK_DOING);
    }

    private String getDataFromResponse(String response) throws Exception {
        JSONObject jsonObject = new JSONObject(response);

        if (jsonObject != null) {
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("msg");
            if (!"1".equals(status)) {
                throw new Exception(msg);
            }

            if (response.contains("data")) {
                return jsonObject.getString("data");
            } else {
                return response;
            }
        } else {
            return null;
        }
    }

    protected int getHttpTypeFromTaskType(int taskType) {
        int type = Request.Method.GET;
        switch (taskType) {
            case BaseTask.REQUEST_TYPE_POST:
                type = Request.Method.POST;
                break;
        }

        return type;
    }

    private Map<String, String> getHttpHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HEAD_NAME_APP_VERSION, SystemUtils.getAppVersionName(mContext));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_APP_VERSION + ":" + SystemUtils.getAppVersionName(mContext));
        headers.put(HEAD_NAME_CLIENT_OS, Build.VERSION.RELEASE);
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_CLIENT_OS + ":" + Build.VERSION.RELEASE);
        headers.put(HEAD_NAME_DEVICE_MODEL, Build.MODEL);
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_DEVICE_MODEL + ":" + Build.MODEL);
        headers.put(HEAD_NAME_CHANNEL, getMetaData(mContext, UMENG_CHANNEL));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_CHANNEL + ":" + getMetaData(mContext, UMENG_CHANNEL));
        headers.put(HEAD_NAME_USER_AGENT, USER_AGENT);
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_USER_AGENT + ":" + USER_AGENT);
        headers.put(HEAD_NAME_VERSION_CODE, SystemUtils.getAppVersionCode(mContext) + "");
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_VERSION_CODE + ":" + SystemUtils.getAppVersionCode(mContext));
        headers.put(HEAD_APP_NAME, APPLICATION_NAME);
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_APP_NAME + ":" + APPLICATION_NAME);
        return headers;
    }

    private String getMetaData(Context context, String metaName) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS | PackageManager.GET_META_DATA);
            Object myChannel = info.applicationInfo.metaData.get(metaName);

            if ((myChannel != null) && (myChannel instanceof String)) {
                return (String) myChannel;
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
