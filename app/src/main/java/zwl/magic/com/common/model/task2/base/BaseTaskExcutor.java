package zwl.magic.com.common.model.task2.base;

import java.lang.reflect.Type;

/**
 * Created by weilong zhou on 2015/9/16.
 * Email:zhouwlong@gmail.com
 */
public abstract class BaseTaskExcutor {
    private static BaseTaskExcutor instance;

    public static BaseTaskExcutor getInstance() {
        if (instance == null) {
            synchronized (BaseTaskExcutor.class) {
                if (instance == null) {
                    instance = new OkHttpTaskExcutor();
                }
            }
        }
        return instance;
    }

    protected BaseTaskExcutor() {

    }

    public abstract void excute(BaseTask task, Type type);

    protected abstract int getHttpTypeFromTaskType(int taskType);
}
