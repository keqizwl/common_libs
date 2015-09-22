package zwl.magic.com.common.model.task;

import android.content.Context;

import com.ishowedu.child.peiyin.R;
import com.ishowedu.child.peiyin.model.entity.Result;
import com.ishowedu.child.peiyin.model.net.request.NetInterface;

public class AddWordTask extends ProgressTask<Result> {
    public static final String TASK_NAME = "AddWordTask";
    private int uid;
    private String content;
    private OnLoadFinishListener iResuleSuccess;

    public AddWordTask(Context context, String content, int uid,
                       OnLoadFinishListener iResuleSuccess) {
        super(context, TASK_NAME);
        setProgressDialog(R.string.submitting);
        this.uid = uid;
        this.content = content;
        this.iResuleSuccess = iResuleSuccess;
    }

    @Override
    protected Result getData() throws Exception {
        return NetInterface.getInstance().addWords(context, uid, 1,
                content);
    }

    @Override
    protected void onTaskFinishedBase(Result t) {
        if (Result.CheckResult(t, context)) {
            if (iResuleSuccess != null) {
                iResuleSuccess.onLoadFinished(taskName, t);
            }
        }
    }
}
