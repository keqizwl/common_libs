package zwl.magic.com.common.common;

import android.os.AsyncTask;

import com.ishowedu.child.peiyin.util.CLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weilong zhou on 2015/8/7.
 * Email:zhouwlong@gmail.com
 */
public class AsynTaskManager {

    public AsynTaskManager() {
    }

    private List<AsyncTask> mBaseTaskList;

    public void addNewTask(AsyncTask task) {
        CLog.v("AsynTaskManager", "addNewTask");
        if (mBaseTaskList == null) {
            mBaseTaskList = new ArrayList<AsyncTask>();
        }

        mBaseTaskList.add(task);
    }

    public void stopTasks() {
        CLog.v("AsynTaskManager", "stopTasks");
        if (mBaseTaskList != null) {
            for (AsyncTask task : mBaseTaskList) {
                if (task != null) {
                    task.cancel(true);
                }
            }

            mBaseTaskList.clear();
            mBaseTaskList = null;
        }
    }
}
