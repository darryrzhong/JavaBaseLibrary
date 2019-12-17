package com.example.corelibrary.net;

/**
 * 应用模块: 网络层
 * <p>
 * 类名称:LoadingListener.java
 * <p>
 * 类描述: 网络请求提示dialog
 * <p>
 *
 * @author darryrzhong
 * @since 2019/9/1 22:37
 */
public interface LoadingListener
{
    
    /**
     * 加载dialog
     */
    void showDialog();
    
    /**
     * 关闭dialog
     */
    void dismissDialog();
}
