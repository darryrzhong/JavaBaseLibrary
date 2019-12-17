package com.example.corelibrary.net.retrofit.convert;

import com.example.corelibrary.net.BaseResponse;
import com.example.corelibrary.net.FailException;
import com.example.corelibrary.net.retrofit.ParameterizedTypeImpl;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 类名称: GsonResponseBodyConverter.java
 * <p>
 * 类描述: ResponseBody处理
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/8/29 16:07
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T>
{
    
    private final Gson gson;
    
    // private final TypeAdapter<T> adapter;
    
    private final TypeAdapter<BaseResponse<T>> adapter;
    
    GsonResponseBodyConverter(Gson gson, TypeToken<T> typeToken)
    {
        
        this.gson = gson;
        // 将基本泛型类型指定为BaseResponse，
        ParameterizedTypeImpl parameterizedType =
                new ParameterizedTypeImpl(null,
                      BaseResponse.class, typeToken.getType());
        // noinspection unchecked
        adapter = (TypeAdapter<BaseResponse<T>>)gson
            .getAdapter(TypeToken.get(parameterizedType));
        // this.adapter = adapter;
    }
    
    @Override
    public T convert(ResponseBody value)
        throws IOException
    {
        
        // 将Response 转换为String
        String response = value.string();
        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
        Logger.d(baseResponse.getCode()+baseResponse.getMessage());
        // 检测是否为与服务端规定响应码(根据具体业务)
        if (!baseResponse.isSuccess())
        {
            value.close();
            // 如果是服务端返回的错误码，则抛出自定义异常
            throw new FailException(baseResponse.getMessage());
        }
        try
        {
            BaseResponse<T> result = adapter.fromJson(response);
            return result.getData();
            
        }
        finally
        {
            value.close();
        }
    }
}
