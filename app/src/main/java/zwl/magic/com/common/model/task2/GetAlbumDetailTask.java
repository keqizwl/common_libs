package zwl.magic.com.common.model.task2;

import android.content.Context;

import com.ishowedu.child.peiyin.model.task2.base.BaseTask;
import com.ishowedu.child.peiyin.model.task2.base.OnLoadFinishListener;
import com.ishowedu.child.peiyin.model.task2.base.SimpleProgressLoadingWaitter;

public class GetAlbumDetailTask extends BaseTask {
    private long album_id;
    private OnLoadFinishListener params;

    public GetAlbumDetailTask(Context context, String url, OnLoadFinishListener params) {
        super(BaseTask.REQUEST_TYPE_GET, url, null);
        setmLoadingWaitter(new SimpleProgressLoadingWaitter(context, "正在加载专辑数据..."));
        setmOnLoadFinishListener(params);
    }

}
