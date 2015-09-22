package zwl.magic.com.common.model.net.simplehttp;

import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.ishowedu.child.peiyin.model.net.base.HttpException;
import com.ishowedu.child.peiyin.model.net.base.HttpHelper;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by weilong zhou on 2015/9/1.
 * Email:zhouwlong@gmail.com
 */
public class SimpleHttpHelper extends HttpHelper {
    private HttpUtil mHttpUtil;

    public SimpleHttpHelper() {
        mHttpUtil = HttpUtil.getInstace(IShowDubbingApplication.getInstance());
    }

    @Override
    public String httpPostRequestJson(String servAddr, List<NameValuePair> params) throws HttpException {
        return mHttpUtil.httpPostRequestJson(servAddr, params);
    }

    @Override
    public String httpGetRequestJson(String servAddr) throws HttpException {
        return mHttpUtil.httpGetRequestJson(servAddr);
    }

}
