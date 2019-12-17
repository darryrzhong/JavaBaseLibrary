package com.example.corelibrary.net.okhttp.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 应用模块: 网络请求<p>
 * 类名称:BaseResponseInterceptor.java<p>
 * 类描述: 响应拦截器<p>
 *
 * @author darryrzhong
 * @since 2019/8/31  9:42
 */
public  abstract class BaseResponseInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return newResponse(chain,response);
    }

    /**
     * 创建新的Response
     * @param chain chain
     * @param response 原始的response
     * @return 新的response
     * */
    protected abstract Response newResponse(Chain chain, Response response);

}
