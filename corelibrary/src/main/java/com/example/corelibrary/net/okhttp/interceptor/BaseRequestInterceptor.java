package com.example.corelibrary.net.okhttp.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 应用模块: 网络请求
 * <p>
 * 类名称: RequestInterceptor.java
 * <p>
 * 类描述: 请求拦截器
 * <p>
 *
 * @author darryrzhong
 * @since 2019/8/31 9:13
 */
public abstract class BaseRequestInterceptor implements Interceptor
{
    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain)
        throws IOException
    {
        // 拦截原始的请求
        Request originalRequest = chain.request();
        // 建立新的请求
        Request.Builder newBuilder = originalRequest.newBuilder();
        
        // 拦截请求,添加公共请求参数
        newBuilder.addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .method(originalRequest.method(), originalRequest.body());
        
        Request newRequest = setBuilder(originalRequest, newBuilder);

        //发送新的请求
        return chain.proceed(newRequest);
    }
    
    /**
     * 设置新的请求参数
     * 
     *
     * @param originalRequest originalRequest
     * @param newBuilder newBuilder
     * @return Request
     */
    protected abstract Request setBuilder(Request originalRequest,
                                          Request.Builder newBuilder);
    
}
