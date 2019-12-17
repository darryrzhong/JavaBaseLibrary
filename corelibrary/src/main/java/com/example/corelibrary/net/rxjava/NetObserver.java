package com.example.corelibrary.net.rxjava;

import com.example.corelibrary.net.ExceptionEngine;
import com.example.corelibrary.net.LoadingListener;
import com.example.corelibrary.net.ResponseCallBack;
import com.orhanobut.logger.Logger;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 应用模块: 网络层
 * <p>
 * 类名称: NetObserver.java
 * <p>
 * 类描述: 网络请求专用Observer
 * <p>
 *
 * @author darryrzhong
 * @since 2019/9/1 22:38
 */
public class NetObserver<T> implements Observer<T>
{
    
    /**
     * 请求回调
     */
    private ResponseCallBack responseCallBack;
    
    /**
     * dialog回调管理
     */
    private LoadingListener loadingListener;
    
    private Disposable disposable;
    
    public NetObserver(ResponseCallBack responseCallBack,
        LoadingListener loadingListener)
    {
        this.responseCallBack = responseCallBack;
        this.loadingListener = loadingListener;
    }
    
    @Override
    public void onSubscribe(Disposable d)
    {
        this.disposable = d;
        if (loadingListener != null)
        {
            loadingListener.showDialog();
        }
    }
    
    @Override
    public void onNext(T t)
    {
        responseCallBack.onSuccess(t);
    }
    
    @Override
    public void onError(Throwable e)
    {
        ExceptionEngine.ErrMessage errMessage =
            ExceptionEngine.handleException(e);
        Logger.d(errMessage.getCode() + " " + errMessage.getMessage());
        // 请求失败
        onFailure(errMessage.getCode(), errMessage.getMessage());
    }
    
    @Override
    public void onComplete()
    {
        // 事件完成取消订阅
        if (disposable != null && !disposable.isDisposed())
        {
            disposable.dispose();
        }
        if (loadingListener != null)
        {
            loadingListener.dismissDialog();
            loadingListener = null;
        }
    }
    
    /**
     * 失败时调用
     *
     * @param errorEvent 错误码
     * @param message 错误信息 如果有其他错误码，可重写此方法
     */
    public void onFailure(int errorEvent, String message)
    {
        if (responseCallBack != null)
        {
            responseCallBack.onFault(errorEvent, message);
        }
        // 事件完成取消订阅
        if (disposable != null && !disposable.isDisposed())
        {
            disposable.dispose();
        }
        if (loadingListener != null)
        {
            loadingListener.dismissDialog();
            loadingListener = null;
        }
    }
}
