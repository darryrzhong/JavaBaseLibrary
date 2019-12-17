package com.example.corelibrary.net;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * 类名称: ExceptionEngine.java
 * <p>
 * 类描述: 请求异常状态码以及异常拦截
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/9/20 17:08
 */
public class ExceptionEngine
{
    
    /**
     * 无网络
     */
    public static final int NOT_NET = -100;
    
    /**
     * 无网络描述
     */
    public static final String NOT_NET_DES = "当前网络不可用，请检查网络情况";
    
    /**
     * 网络错误
     */
    private static final int HTTP_ERROR = -101;
    
    /**
     * 网络错误描述 401
     */
    private static final int HTTP_ERROR_UNAUTHORIZED = 401;
    
    /**
     * 网络错误描述 403
     */
    private static final int HTTP_ERROR_FORBIDDEN = 403;
    
    /**
     * 网络错误描述 404
     */
    private static final int HTTP_ERROR_NOT_FOUND = 404;
    
    /**
     * 网络错误描述 408
     */
    private static final int HTTP_ERROR_REQUEST_TIMEOUT = 408;
    
    /**
     * 网络错误描述 500
     */
    private static final int HTTP_ERROR_SERVER_ERROR = 500;
    
    /**
     * 网络错误描述 502
     */
    private static final int HTTP_ERROR_BAD_GATEWAY = 502;
    
    /**
     * 网络错误描述 503
     */
    private static final int HTTP_ERROR_UNAVAILABLE = 503;
    
    /**
     * 网络错误描述 504
     */
    private static final int HTTP_ERROR_GATEWAY_TIMEOUT = 504;
    
    /**
     * 网络错误描述
     */
    private static final String HTTP_ERROR_DES = "网络出错,请稍后再试!";
    
    /**
     * 未知错误
     */
    private static final int UNKNOWN = -102;
    
    /**
     * 未知错误描述
     */
    private static final String UNKNOWN_DES = "未知错误";
    
    /**
     * 解析错误
     */
    private static final int PARSE_ERROR = -103;
    
    /**
     * 解析错误描述
     */
    private static final String PARSE_ERROR_DES = "解析错误";
    
    /**
     * 连接超时
     */
    private static final int CONNECT_TIME_ERROR = -104;
    
    /**
     * 连接超时错误描述
     */
    private static final String CONNECT_TIME_ERROR_DES = "请求超时,请稍后再试";
    
    /**
     * 连接出错
     */
    private static final int CONNECT_ERROR = -105;
    
    /**
     * 连接出错错误秒数
     */
    private static final String CONNECT_ERROR_DES = "网络连接超时,请稍后再试";
    
    /**
     * 证书出错
     */
    private static final int SSL_ERROR = -106;
    
    /**
     * 证书出错描述
     */
    private static final String SSL_ERROR_DES = "证书出错";
    
    /**
     * API 错误码
     */
    private static final int API_ERROR = -107;
    
    /**
     * 错误信息解析
     *
     * @param e 异常信息
     * @return ErrMessage
     */
    public static ErrMessage handleException(Throwable e)
    {
        
        if (e instanceof HttpException)
        {
            HttpException httpException = (HttpException)e;
            switch (httpException.code())
            {
                case HTTP_ERROR_UNAUTHORIZED:
                case HTTP_ERROR_FORBIDDEN:
                case HTTP_ERROR_NOT_FOUND:
                case HTTP_ERROR_REQUEST_TIMEOUT:
                case HTTP_ERROR_GATEWAY_TIMEOUT:
                case HTTP_ERROR_SERVER_ERROR:
                case HTTP_ERROR_BAD_GATEWAY:
                case HTTP_ERROR_UNAVAILABLE:
                default:
                    return new ErrMessage(HTTP_ERROR, HTTP_ERROR_DES);
            }
        }
        else if (e instanceof SocketTimeoutException)
        {
            return new ErrMessage(CONNECT_TIME_ERROR, CONNECT_TIME_ERROR_DES);
        }
        else if (e instanceof ConnectException)
        {
            return new ErrMessage(CONNECT_ERROR, CONNECT_ERROR_DES);
        }
        else if (e instanceof SSLHandshakeException)
        {
            return new ErrMessage(SSL_ERROR, SSL_ERROR_DES);
        }
        else if (e instanceof JSONException)
        {
            return new ErrMessage(PARSE_ERROR, PARSE_ERROR_DES);
        }
        else if (e instanceof FailException)
        {
            return new ErrMessage(API_ERROR, e.getMessage());
        }
        else
        {
            // 未知错误
            return new ErrMessage(UNKNOWN, UNKNOWN_DES);
        }
        
    }
    
    /**
     * 错误信息体
     */
    public static class ErrMessage
    {
        /**
         * 错误码
         */
        private int code;
        
        /**
         * 错误描述
         */
        private String message;
        
        public ErrMessage(int code, String message)
        {
            this.code = code;
            this.message = message;
        }
        
        public int getCode()
        {
            return code;
        }
        
        public void setCode(int code)
        {
            this.code = code;
        }
        
        public String getMessage()
        {
            return message;
        }
        
        public void setMessage(String message)
        {
            this.message = message;
        }
    }

}
