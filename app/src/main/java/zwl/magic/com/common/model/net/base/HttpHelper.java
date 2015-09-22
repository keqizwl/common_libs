package zwl.magic.com.common.model.net.base;

import com.ishowedu.child.peiyin.model.net.simplehttp.SimpleHttpHelper;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by weilong zhou on 2015/9/1.
 * Email:zhouwlong@gmail.com
 */
public abstract class HttpHelper {
    private static HttpHelper instance;

    protected HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new SimpleHttpHelper();
                }
            }
        }
        return instance;
    }

    public abstract String httpPostRequestJson(String servAddr, List<NameValuePair> params) throws HttpException;

    public abstract String httpGetRequestJson(String servAddr) throws HttpException;
}
