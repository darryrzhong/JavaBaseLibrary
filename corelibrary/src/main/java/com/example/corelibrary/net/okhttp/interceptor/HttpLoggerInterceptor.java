package com.example.corelibrary.net.okhttp.interceptor;

import android.util.Log;

import com.example.corelibrary.utils.JsonUtil;
import com.orhanobut.logger.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 类名称: HttpLogger.java
 * <p>
 * 类描述: 网络请求日志拦截器
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/8/21 11:01
 */

public class HttpLoggerInterceptor implements HttpLoggingInterceptor.Logger
{
    
    private StringBuilder mMessage = new StringBuilder();
    
    @Override
    public void log(String message)
    {
        
        // 请求或者响应开始
        if (message.startsWith("--> POST"))
        {
            mMessage.setLength(0);
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        boolean isJson = (message.startsWith("{") && message.endsWith("}"))
            || (message.startsWith("[") && message.endsWith("]"));
        if (isJson)
        {
            message = JsonUtil.formatJson(message);
        }
        mMessage.append(message.concat("\n"));
        // 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP"))
        {
            Logger.d(mMessage.toString());
        }
    }
    
}
