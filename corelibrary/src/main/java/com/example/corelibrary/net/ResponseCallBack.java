package com.example.corelibrary.net;

/**
 * 应用模块: 网络层
 * <p>
 * 类名称: ResponseCallBack.java
 * <p>
 * 类描述: 网络请求响应回调
 * <p>
 *
 * @author darryrzhong
 * @since 2019/9/1 22:40
 */
public interface ResponseCallBack<T>
{
    
    /**
     * 请求成功
     * 
     * @param t 最终需要的数据类型
     */
    void onSuccess(T t);
    
    /**
     * 请求失败
     * 
     * @param errorMsg 失败信息
     */
    void onFault(int errorCode, String errorMsg);
}
