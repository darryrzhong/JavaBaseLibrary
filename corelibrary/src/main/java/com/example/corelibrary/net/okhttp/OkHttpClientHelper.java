package com.example.corelibrary.net.okhttp;

import com.example.corelibrary.net.HttpConfig;
import com.example.corelibrary.net.okhttp.interceptor.HttpLoggerInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 应用模块: 网络请求<p>
 * 类名称: OkHttpClientHelper.java<p>
 * 类描述: OkhttpClient帮助类<p>
 *
 * @author darryrzhong
 * @since 2019/9/1  23:07
 */
public class OkHttpClientHelper {

    /**
     * OkHttpClient
     */
    private OkHttpClient mClient;

    private OkHttpClientHelper()
    {

    }

    /**
     * 单例模式
     */
    public static OkHttpClientHelper getInstance()
    {
        return OkHttpClientHolder.INSTANCE;
    }

    public OkHttpClient getOkHttpClient()
    {
        if (mClient == null)
        {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(HttpConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(HttpConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(HttpConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);
//                    .addNetworkInterceptor(new RequestInterceptor())
//                    .addInterceptor(new ResponseInterceptor());
            // debug模式下打印日志
            if (true)
            {
                HttpLoggingInterceptor loggingInterceptor =
                        new HttpLoggingInterceptor(new HttpLoggerInterceptor());
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addNetworkInterceptor(loggingInterceptor);
            }
            mClient = builder.build();
        }
        return mClient;
    }

    private static class OkHttpClientHolder
    {
        private static final OkHttpClientHelper INSTANCE =
                new OkHttpClientHelper();
    }

}
