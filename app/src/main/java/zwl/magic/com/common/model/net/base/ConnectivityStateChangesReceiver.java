package zwl.magic.com.common.model.net.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by weilong zhou on 2015/9/7.
 * Email:zhouwlong@gmail.com
 */
public class ConnectivityStateChangesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
//            ToastUtils.show(context, "CONNECTIVITY_ACTION");
            ConnectStateHelper.getInstance().onConnectedChanged();
        }
    }
}
