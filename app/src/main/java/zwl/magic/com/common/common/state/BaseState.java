package zwl.magic.com.common.common.state;

/**
 * Created by weilong zhou on 2015/8/12.
 * Email:zhouwlong@gmail.com
 */
public abstract class BaseState {
    private int state;

    public final int getState() {
        return state;
    }

    public final void setState(int state) {
        this.state = state;
    }
}
