package com.example.corelibrary.net.retrofit.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 类名称: GsonConverterFactory.java
 * <p>
 * 类描述: GsonConverterFactory 解析工厂类
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/8/29 15:48
 */
public class GsonConverterFactory extends Converter.Factory
{

    private final Gson gson;

    private GsonConverterFactory(Gson gson)
    {
        if (null == gson)
        {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    public static GsonConverterFactory create()
    {
        return create(new Gson());
    }

    public static GsonConverterFactory create(Gson gson)
    {
        return new GsonConverterFactory(gson);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations,
                                                          Retrofit retrofit)
    {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations, Retrofit retrofit)
    {
        // TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, TypeToken.get(type));
    }

}
