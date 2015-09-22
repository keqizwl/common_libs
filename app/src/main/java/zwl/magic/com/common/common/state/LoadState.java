package zwl.magic.com.common.common.state;

/**
 * Created by weilong zhou on 2015/9/8.
 * Email:zhouwlong@gmail.com
 */
public class LoadState extends BaseState {
   public static final int STATE_PRE_LOADING = 0;
   public static final int STATE_LOADING = 1;
   public static final int STATE_LOAD_CANCEL = 2;
   public static final int STATE_LOAD_SUCCESS = 3;
   public static final int STATE_LOAD_FAILED = 4;
}
