package zwl.magic.com.common.model.task2.base;

import android.content.Context;

import com.ishowedu.child.peiyin.activity.Room.Dub.WaitDialog;

/**
 * Created by weilong zhou on 2015/9/16.
 * Email:zhouwlong@gmail.com
 */
public class SimpleProgressLoadingWaitter implements LoadingWaitter {
    private WaitDialog mWaitDialog;

    public SimpleProgressLoadingWaitter(Context context, String msg) {
        mWaitDialog = new WaitDialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        mWaitDialog.setMessage(msg);
    }

    @Override
    public void preLoading() {
        mWaitDialog.show();
    }

    @Override
    public void endLoading() {
        mWaitDialog.dismiss();
    }
}
