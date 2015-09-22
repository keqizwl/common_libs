/**
 * @FileName Utils.java
 * @Description This class is the Utils of other classes.
 * @author wlf
 * @data 2012-10-10
 * @note
 * @note
 * @warning
 */
package zwl.magic.com.common.model.net.simplehttp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ParseException;
import android.os.Build;

import com.feizhu.publicutils.SystemUtils;
import com.ishowedu.child.peiyin.R;
import com.ishowedu.child.peiyin.model.net.base.HttpException;
import com.ishowedu.child.peiyin.util.CLog;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * This class is the Utils of other classes.
 *
 * @author wlf
 * @data 2012-10-10
 */
public class HttpUtil {
    /**
     * 变量/常量说明
     */
    private static final String TAG = "HttpUtils";
    private static final String HEAD_NAME_USER_AGENT = "User-Agent";
    private static final String HEAD_NAME_VERSION_CODE = "versionCode";
    private static final String USER_AGENT = "android";
    private static final String HEAD_NAME_APP_VERSION = "App-Version";
    private static final String HEAD_NAME_CLIENT_OS = "Client-OS";
    private static final String HEAD_NAME_DEVICE_MODEL = "Device-Model";
    private static final String HEAD_NAME_CHANNEL = "Umeng-Channel";
    private static final String UMENG_CHANNEL = "UMENG_CHANNEL";
    private static final String APPLICATION_NAME = "englishtalk_child";
    private static final String HEAD_APP_NAME = "Application_name";

    /**
     * 超时时间
     */
    private static int mTimeOut = 20000;
    /**
     * 使用协议
     */
    private static final String CLIENT_AGREEMENT = "TLS";
    /**
     * 变量/常量说明
     */
    private static HttpUtil mHttpUtils = null;
    private Context context;

    /**
     * 获取HPPS对象实例
     *
     * @param
     * @return
     * @since V1.0
     */
    public static HttpUtil getInstace(Context context) {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtil(context);
        }
        return mHttpUtils;
    }

    /**
     * @param
     */
    private HttpUtil(Context context) {
        this.context = context;
    }

    /**
     * http post请求
     *
     * @param servAddr
     * @param params
     * @return
     * @throws HttpException
     */

    public String httpPostRequestJson(String servAddr, List<NameValuePair> params) throws HttpException {

        long beginT = 0;
        if (CLog.DEBUG) {
            beginT = System.currentTimeMillis();
            CLog.d(TAG, "httpPostRequestJson beginT:" + beginT);
            CLog.d(TAG, "httpPostRequestJson servAddr:" + servAddr);
            CLog.d(TAG, "httpPostRequestJson params:" + params.toString());
        }

        boolean ret = true;
        String strResult = "";
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            /** 客服端向服务器发送请求 **/
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
                    "UTF-8");
            /** 新建一个post请求 **/
            HttpPost post = new HttpPost(servAddr);
            setHttpRequestHeader(post);
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(response.getEntity());
            }
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            ret = false;
        } catch (ClientProtocolException e) {

            e.printStackTrace();
            ret = false;
        } catch (IllegalStateException e) {

            e.printStackTrace();
            ret = false;
        } catch (ParseException e) {

            e.printStackTrace();
            ret = false;
        } catch (IOException e) {

            e.printStackTrace();
            ret = false;
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
            ret = false;
        } catch (Exception e) {

            e.printStackTrace();
            ret = false;
        }

        // 抛出异常
        if (!ret) {
            throw new HttpException(context.getString(R.string.intl_cache_useble),
                    HttpException.MPUNETSDK_NETWORD_EXCEPTION);
        }

        if (CLog.DEBUG) {
            long requestTime = System.currentTimeMillis() - beginT;
            CLog.d(TAG, "httpPostRequestJson request Time:" + requestTime);
            CLog.d(TAG, strResult);
        }

        return strResult;

    }


    /**
     * get请求
     *
     * @param servAddr
     * @return
     * @throws HttpException
     */

    public String httpGetRequestJson(String servAddr) throws HttpException {
        long beginT;
        if (CLog.DEBUG) {
            beginT = System.currentTimeMillis();
            CLog.d(TAG, "httpGetRequestJson beginT:" + beginT);
            CLog.d(TAG, "httpGetRequestJson servAddr:" + servAddr);
        }

        boolean ret = true;
        String strResult = "";
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            /** 新建一个get请求 **/
            HttpGet get = new HttpGet(servAddr);
            setHttpRequestHeader(get);
            HttpResponse response = client.execute(get);
            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            ret = false;
        } catch (ClientProtocolException e) {

            e.printStackTrace();
            ret = false;
        } catch (IllegalStateException e) {

            e.printStackTrace();
            ret = false;
        } catch (ParseException e) {

            e.printStackTrace();
            ret = false;
        } catch (IOException e) {

            e.printStackTrace();
            ret = false;
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
            ret = false;
        } catch (Exception e) {

            e.printStackTrace();
            ret = false;
        }

        // 抛出异常
        if (!ret) {
            throw new HttpException(context.getString(R.string.intl_cache_useble),
                    HttpException.MPUNETSDK_NETWORD_EXCEPTION);
        }

        if (CLog.DEBUG) {
            long requestTime = System.currentTimeMillis() - beginT;
            CLog.d(TAG, "httpGetRequestJson request Time:" + requestTime);
            CLog.d(TAG, strResult);
        }

        return strResult;

    }

    /**
     * 这里对方法做描述
     *
     * @return
     * @throws HttpException
     * @see
     * @since V1.0
     */
    public String httpsGetRequest(String servHttpsAddr) {
        long beginT = 0;
        if (CLog.DEBUG) {
            beginT = System.currentTimeMillis();
            CLog.d(TAG, "httpsGetRequest beginT:" + beginT);
            CLog.d(TAG, "httpsGetRequest servAddr:" + servHttpsAddr);
        }

        if (servHttpsAddr == null || servHttpsAddr.equals("")) {
            CLog.d(TAG, "sslGetRequest servHttpsAddr == null");
            return "";
        }

        boolean bRet = verifyHttpsUrl(servHttpsAddr);
        if (!bRet) {
            CLog.d(TAG, "sslGetRequest verifyHttpsUrl fail");
            return "";
        }

        String response = "";
        try {
            response = getSslRequest(servHttpsAddr);
        } catch (HttpException e) {
            e.printStackTrace();
            CLog.d(TAG, "sslGetRequest verifyHttpsUrl fail");
            return "";
        }

        if (CLog.DEBUG) {
            long requestTime = System.currentTimeMillis() - beginT;
            CLog.d(TAG, "httpsGetRequest request Time:" + requestTime);
            CLog.d(TAG, "httpsGetRequest strResult:" + response);
        }

        return response;
    }

    /**
     * httpsPost方式发送
     *
     * @return
     * @throws HttpException
     * @see
     * @since V1.0
     */
    public String httpsPostRequest(String servHttpsAddr, String xmlBody)
            throws HttpException {

        long beginT = 0;
        if (CLog.DEBUG) {
            beginT = System.currentTimeMillis();
            CLog.d(TAG, "httpsGetRequest beginT:" + beginT);
            CLog.d(TAG, "httpsGetRequest servAddr:" + servHttpsAddr);
        }

        if (servHttpsAddr == null || servHttpsAddr.equals("")) {
            CLog.d(TAG, "postHttpsRequest servHttpsAddr == null");
            return "";
        }

        if (xmlBody == null || xmlBody.equals("")) {
            CLog.d(TAG, "postHttpsRequest xmlBody == null");
            return "";
        }

        boolean bRet = verifyHttpsUrl(servHttpsAddr);
        if (!bRet) {
            CLog.d(TAG, "postHttpsRequest verifyHttpsUrl fail");
            return "";
        }

        String response = "";
        try {
            response = postSslRequest(servHttpsAddr, xmlBody);
        } catch (HttpException e) {
            e.printStackTrace();
            CLog.d(TAG, "postHttpsRequest postSslRequest fail");
            return "";
        }

        if (CLog.DEBUG) {
            long requestTime = System.currentTimeMillis() - beginT;
            CLog.d(TAG, "httpsGetRequest request Time:" + requestTime);
            CLog.d(TAG, "httpsGetRequest strResult:" + response);
        }

        return response;
    }

    /**
     * 把输入流转化成string
     *
     * @param is
     * @return
     * @see
     * @since V1.0
     */
    private static String inputStream2String(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                in = null;
            }
        }
        return buffer.toString();
    }

    /**
     * 这里对方法做描述
     *
     * @param servHttpsAddr
     * @param
     * @return
     * @throws HttpException
     * @since V1.0
     */
    private String getSslRequest(String servHttpsAddr) throws HttpException {
        // create connection
        String response = null;
        HttpURLConnection conn = null;
        boolean ret = false;
        InputStream in = null;
        try {
            URL url = new URL(servHttpsAddr);
            trustAllHosts();
            conn = (HttpsURLConnection) url.openConnection();
            ((HttpsURLConnection) conn).setHostnameVerifier(DO_NOT_VERIFY);// 不进行主机名确认

            conn.setConnectTimeout(mTimeOut);
            conn.setReadTimeout(mTimeOut);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "text/xml; charset=utf-8");

            if (conn.getResponseCode() == 200) {
                // getCookie(conn);
                in = new BufferedInputStream(conn.getInputStream());
            } else {
                in = new BufferedInputStream(conn.getErrorStream());
            }

            response = inputStream2String(in);

            in.close();
            in = null;
            ret = true;
        } catch (MalformedURLException e) {
            ret = false;
            e.printStackTrace();
        } catch (ProtocolException e) {
            ret = false;
            e.printStackTrace();
        } catch (IOException e) {
            ret = false;
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                in = null;
            }
            if (conn != null) {
                // 断开连接
                conn.disconnect();
                conn = null;
            }
        }

        // 抛出异常
        if (!ret) {
            throw new HttpException(context.getString(R.string.intl_cache_useble),
                    HttpException.MPUNETSDK_NETWORD_EXCEPTION);
        }

        return response;
    }

    /**
     * 验证https地址
     *
     * @since V1.0
     */
    private boolean verifyHttpsUrl(String httpsAddr) {

        if (httpsAddr == null || httpsAddr.equals("")) {
            CLog.e(TAG, "verifyHttpsUrl httpsAddr == null");
            return false;
        }

        URL httpsUurl;
        try {
            httpsUurl = new URL(httpsAddr);
        } catch (MalformedURLException e) {

            e.printStackTrace();
            CLog.e(TAG, "verifyHttpsUrl httpsAddr not url, error url:"
                    + httpsAddr);
            return false;
        }

        if (!httpsUurl.getProtocol().toLowerCase().equals("https")) {
            CLog.e(TAG, "verifyHttpsUrl httpsAddr not https, error url:"
                    + httpsAddr);
            return false;
        }

        return true;
    }

    /**
     * post ssl请求
     *
     * @param servHttpsAddr
     * @param xmlBody
     * @return
     * @throws HttpException
     * @since V1.0
     */
    private String postSslRequest(String servHttpsAddr, String xmlBody)
            throws HttpException {
        // 回复信令
        String response = null;
        //
        boolean ret = false;
        // 输入流
        InputStream in = null;

        HttpsURLConnection httpsConn = null;
        try {
            URL url = new URL(servHttpsAddr);

            // solution: javax.net.ssl.SSLException: Not trusted server
            // certificate
            trustAllHosts();

            // 打开连接
            httpsConn = (HttpsURLConnection) url.openConnection();
            // 不进行主机名确认
            httpsConn.setHostnameVerifier(DO_NOT_VERIFY);
            httpsConn.setConnectTimeout(mTimeOut);
            httpsConn.setReadTimeout(mTimeOut);
            httpsConn.setDoInput(true);
            httpsConn.setDoOutput(true);

            // send xml contant to server
            DataOutputStream os = new DataOutputStream(
                    httpsConn.getOutputStream());
            os.write(xmlBody.getBytes(), 0, xmlBody.getBytes().length);
            os.flush();
            os.close();

            if (httpsConn.getResponseCode() == 200) {
                // getCookie(conn);
                in = new BufferedInputStream(httpsConn.getInputStream());
            } else {
                in = new BufferedInputStream(httpsConn.getErrorStream());
            }

            response = inputStream2String(in);

            in.close();
            in = null;
            ret = true;
        } catch (MalformedURLException e) {
            ret = false;
            e.printStackTrace();
        } catch (ProtocolException e) {
            ret = false;
            e.printStackTrace();
        } catch (IOException e) {
            ret = false;
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                in = null;
            }
            if (httpsConn != null) {
                // 断开连接
                httpsConn.disconnect();
                httpsConn = null;
            }
        }

        // 抛出异常
        if (!ret) {
            throw new HttpException(context.getString(R.string.intl_cache_useble),
                    HttpException.MPUNETSDK_NETWORD_EXCEPTION);
        }

        return response;
    }

    /**
     * always verify the host - dont check for certificate
     */
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * Trust every server - dont check for any certificate
     *
     * @since V1.0
     */
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance(CLIENT_AGREEMENT);
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在此对类做相应的描述
     *
     * @author weilinfeng
     * @Data 2013-10-23
     */
    public static class _FakeX509TrustManager implements X509TrustManager {

        /**
         * 变量/常量说明
         */
        private static TrustManager[] trustManagers;
        /**
         * 变量/常量说明
         */
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        /**
         * 这里对方法做描述
         *
         * @param chain
         * @return
         * @since V1.0
         */
        public boolean isClientTrusted(X509Certificate[] chain) {
            return true;
        }

        /**
         * 这里对方法做描述
         *
         * @param chain
         * @return
         * @since V1.0
         */
        public boolean isServerTrusted(X509Certificate[] chain) {
            return true;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return _AcceptedIssuers;
        }

        /**
         * 这里对方法做描述
         *
         * @since V1.0
         */
        public static void allowAllSSL() {
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname,
                                              SSLSession session) {
                            return true;
                        }

                    });

            SSLContext context = null;
            if (trustManagers == null) {
                trustManagers = new TrustManager[]{new _FakeX509TrustManager()};
            }

            try {
                context = SSLContext.getInstance(CLIENT_AGREEMENT);
                context.init(null, trustManagers, new SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            SSLSocketFactory defaultSSLSocketFactory = context
                    .getSocketFactory();
            if (defaultSSLSocketFactory != null) {
                HttpsURLConnection
                        .setDefaultSSLSocketFactory(defaultSSLSocketFactory);
            }
        }
    }

    /**
     * 设置默认添加信息
     *
     * @param request
     */
    private void setHttpRequestHeader(HttpRequestBase request) {
        if (null == request) {
            return;
        }

        request.addHeader(HEAD_NAME_APP_VERSION, SystemUtils.getAppVersionName(context));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_APP_VERSION + ":" + SystemUtils.getAppVersionName(context));
        request.addHeader(HEAD_NAME_CLIENT_OS, Build.VERSION.RELEASE);
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_CLIENT_OS + ":" + Build.VERSION.RELEASE);
        request.addHeader(HEAD_NAME_DEVICE_MODEL, Build.MODEL);
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_DEVICE_MODEL + ":" + Build.MODEL);
        request.addHeader(HEAD_NAME_CHANNEL, getMetaData(context, UMENG_CHANNEL));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_CHANNEL + ":" + getMetaData(context, UMENG_CHANNEL));
        request.setHeader(HEAD_NAME_USER_AGENT, USER_AGENT);
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_USER_AGENT + ":" + USER_AGENT);
        request.setHeader(HEAD_NAME_VERSION_CODE, SystemUtils.getAppVersionCode(context) + "");
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_VERSION_CODE + ":" + SystemUtils.getAppVersionCode(context));
        request.setHeader(HEAD_APP_NAME, APPLICATION_NAME);
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_APP_NAME + ":" + APPLICATION_NAME);
    }


    /**
     * @return
     */
    public List<Header> getHttpRequestHeader() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader(HEAD_NAME_APP_VERSION, SystemUtils.getAppVersionName(context)));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_APP_VERSION + ":" + SystemUtils.getAppVersionName(context));

        headers.add(new BasicHeader(HEAD_NAME_CLIENT_OS, Build.VERSION.RELEASE));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_CLIENT_OS + ":" + Build.VERSION.RELEASE);

        headers.add(new BasicHeader(HEAD_NAME_DEVICE_MODEL, Build.MODEL));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_DEVICE_MODEL + ":" + Build.MODEL);

        headers.add(new BasicHeader(HEAD_NAME_CHANNEL, getMetaData(context, UMENG_CHANNEL)));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_CHANNEL + ":" + getMetaData(context, UMENG_CHANNEL));

        headers.add(new BasicHeader(HEAD_NAME_USER_AGENT, USER_AGENT));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_USER_AGENT + ":" + USER_AGENT);

        headers.add(new BasicHeader(HEAD_NAME_VERSION_CODE, SystemUtils.getAppVersionCode(context) + ""));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_NAME_VERSION_CODE + ":" + SystemUtils.getAppVersionCode(context));

        headers.add(new BasicHeader(HEAD_APP_NAME, APPLICATION_NAME));
        CLog.d(TAG, "setHttpRequestHeader " + HEAD_APP_NAME + ":" + APPLICATION_NAME);

        return headers;
    }

    /**
     * 获取meta数据
     *
     * @param context
     * @param metaName
     * @return
     */
    private String getMetaData(Context context, String metaName) {
        PackageManager manager = context.getPackageManager();
        try {
            // 设置PackageManager.GET_META_DATA标识位是必须的
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS | PackageManager.GET_META_DATA);
            Object myChannel = info.applicationInfo.metaData.get(metaName);

            if ((myChannel != null) && (myChannel instanceof String)) {
                return (String) myChannel;
            }

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
