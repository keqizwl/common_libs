/* 
 * @ProjectName VideoGo
 * @Copyright HangZhou Hikvision System Technology Co.,Ltd. All Right Reserved
 * 
 * @FileName VideoGoNetSDKException.java
 * @Description 这里对文件进行描述
 * 
 * @author Dengshihua
 * @data 2012-9-27
 * 
 * @note 这里写本文件的详细功能描述和注释
 * @note 历史记录
 * 
 * @warning 这里写本文件的相关警告
 */
package zwl.magic.com.common.model.net.base;

import com.ishowedu.child.peiyin.util.ExceptionUtils;

/**
 * VideoGoNetSDKException
 *
 * @author dengsh
 * @data 2012-9-27
 */
public class HttpException extends Exception {
    /**
     * 变量/常量说明
     */
    private static final long serialVersionUID = 1L;

    /**
     * no error(const value:0)
     */
    public static final int MPUNETSDK_NO_ERROR = 0;

    /**
     * server address error(const value:2)
     */
    public static final int MPUNETSDK_SERVADDR_EMPTY_EEROR = 1;
    /**
     * server address error(const value:2)
     */
    public static final int MPUNETSDK_UERNAME_EMPTY_ERROR = 2;
    /**
     * server address error(const value:2)
     */
    public static final int MPUNETSDK_PASSWORD_EMPTY_ERROR = 3;
    public static final int MPUNETSDK_SERVERINFO_NULL_ERROR = 4;
    public static final int MPUNETSDK_INPUT_PARAM_ERROR = 5;
    public static final int MPUNETSDK_MSP_UNKNOWN_ERROR = 6;
    public static final int MPUNETSDK_HTTP_REQUEST_RETURN_NULL = 7;
    public static final int MPUNETSDK_NETWORD_EXCEPTION = 8;

    /**
     * server address error(const value:0)
     */
    public static final int HTTP_NET_RESPONE_NULL = 0;
    /**
     * server address error(const value:1)
     */
    public static final int HTTP_NET_FORMAT_WRONG = 1;
    /**
     * server address error(const value:2)
     */
    public static final int HTTP_NET_SERVER_ERROR = 2;
    /**
     * token过期(const value:3)
     */
    public static final int HTTP_NET_TOKEN_OVER = 3;
    /**
     * 重新请求接口(const value:4)
     */
    public static final int HTTP_NET_REPEAT_REQUEST = 4;
    public static final int HTTP_TRY_AGAIN = 5;


    /**
     * 错误码
     */
    private int mErrorCode = MPUNETSDK_NO_ERROR;

    /**
     * 获取错误码
     *
     * @return 错误码
     * @since V1.0
     */
    public int getErrorCode() {
        return mErrorCode;
    }

    public HttpException(String desc, int errorCode) {
        super(desc);
        mErrorCode = errorCode;
    }

    public HttpException(Exception e) {
        super(ExceptionUtils.formatExceptionMessage(e));

        if (e instanceof HttpException) {
            mErrorCode = ((HttpException) e).getErrorCode();
        }
    }
}
