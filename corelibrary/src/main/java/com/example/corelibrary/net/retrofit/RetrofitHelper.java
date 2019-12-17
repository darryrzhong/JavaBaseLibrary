package com.example.corelibrary.net.retrofit;

import com.example.corelibrary.app.context.ObjectFactory;
import com.example.corelibrary.net.okhttp.OkHttpClientHelper;
import com.example.corelibrary.net.retrofit.convert.GsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 应用模块: 网络请求<p>
 * 类名称: RetrofitHelper.java'<p>
 * 类描述: Retrofit帮助类<p>
 *
 * @author darryrzhong
 * @since 2019/9/21  14:03
 */
public class RetrofitHelper {

    private final Retrofit mRetrofit;

    /**
     * OkHttpClient
     */
    private OkHttpClient mClient;

    private RetrofitHelper()
    {

        mClient = OkHttpClientHelper.getInstance().getOkHttpClient();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ObjectFactory.getAppConfig().getBaseUrl())
                // 添加自定义Gson解析
                .addConverterFactory(GsonConverterFactory.create())
                // 添加Rxjava支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 配置Okhttp
                .client(mClient)
                .build();

    }

    /**
     * 单例模式
     *
     * @return RetrofitHelper
     */
    public static RetrofitHelper getInstance()
    {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 静态内部持有类
     * */
    private static class SingletonHolder
    {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }


    /**
     * 获取对应的Service实例
     *
     * @param service Service 的 class
     * @param <T> Service
     * @return T Service
     */
    public <T> T create(Class<T> service)
    {
        return mRetrofit.create(service);
    }



}
