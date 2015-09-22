package zwl.magic.com.common.control.authority.listener;

import android.app.Activity;
import android.content.Intent;

import com.feizhu.publicutils.ToastUtils;
import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.ishowedu.child.peiyin.R;
import com.ishowedu.child.peiyin.activity.login.LoginActivity;

/**
 * Created by weilong zhou on 2015/8/26.
 * Email:zhouwlong@gmail.com
 */
public class SimpleVisitorUserAuthorListener implements OnAuthorFinishListener {

    @Override
    public void onAuthorFinish(int authorCode, boolean allow) {
        if(allow){
            return;
        }
        
        final Activity activity = IShowDubbingApplication.getInstance().getCurActivity();
        if (activity != null) {
            IShowDubbingApplication.getInstance().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(activity, R.string.visitor_toast);
                    activity.startActivity(new Intent(activity, LoginActivity.class).putExtra(LoginActivity.EXTRA_IS_FINISH, true));
                    activity.overridePendingTransition(R.anim.in_from_buttom_quick,
                            R.anim.fading_in_1_0);
                }

            });
        }
    }
}
