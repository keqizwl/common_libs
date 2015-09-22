package zwl.magic.com.common.model.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.feizhu.publicutils.ToastUtils;
import com.ishowedu.child.peiyin.R;
import com.ishowedu.child.peiyin.activity.Room.Dub.WaitDialog;
import com.ishowedu.child.peiyin.activity.baseclass.BaseActivity;
import com.ishowedu.child.peiyin.activity.baseclass.BaseFragmentActivity;
import com.ishowedu.child.peiyin.model.net.base.HttpException;

public abstract class ProgressTask<T> extends AsyncTask<Void, Void, T> {
    protected WaitDialog progressDialog;

    protected Context context;
    protected String taskName;
    private boolean toShowProgressDialog = true;
    private boolean showToast = true;

    private String errorMessage;

    protected ProgressTask(Context context) {
        this(context, true);
    }

    protected ProgressTask(Context context, String taskName) {
        this(context, true, taskName);
    }

    protected ProgressTask(Context context, boolean flag) {
        this(context, flag, "");
    }

    protected ProgressTask(Context context, boolean flag, String taskName) {
        this.context = context;
        this.taskName = taskName;
        toShowProgressDialog = flag;
        init(context);
    }

    private void init(Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                if (toShowProgressDialog) {
                    progressDialog = new WaitDialog(context, android.R.style.Theme_Translucent_NoTitleBar);
                }

                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).addNewTask(this);
                }

                if (context instanceof BaseFragmentActivity) {
                    ((BaseFragmentActivity) context).addNewTask(this);
                }
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (toShowProgressDialog && progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    protected T doInBackground(Void... arg0) {
        try {
            return getData();
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = e.getMessage();
            if (e instanceof HttpException && ((HttpException) e).getErrorCode() == HttpException.HTTP_TRY_AGAIN) {
                errorMessage = null;
                return doInBackground();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        if (context instanceof Activity) {
            if (context == null || ((Activity) context).isFinishing()) {
                return;
            }
        }

        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (checkShowToast()) {
            ToastUtils.show(context, errorMessage);
        }

        onTaskFinishedBase(result);
    }

    protected abstract T getData() throws Exception;

    protected abstract void onTaskFinishedBase(T t);

    private boolean checkShowToast() {
        return !TextUtils.isEmpty(errorMessage) && showToast && !errorMessage.contains(context.getString(R.string.no_more_data)) && !errorMessage.contains(context.getString(R.string.error_user));
    }

    public void setProgressDialog(String msg) {
        if (progressDialog != null) {
            progressDialog.setMessage(msg);
        }
    }

    public void setProgressDialog(int msgResId) {
        if (progressDialog != null) {
            progressDialog.setMessage(context.getResources()
                    .getString(msgResId));
        }
    }

    public void setShowProgressDialog(boolean flag) {
        toShowProgressDialog = flag;
    }

    public void setShowToast(boolean flag) {
        showToast = flag;
    }
}
