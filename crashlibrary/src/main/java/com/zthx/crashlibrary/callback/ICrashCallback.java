package com.zthx.crashlibrary.callback;

/**
 * 应用模块 :crash 捕获
 * <p>
 * 类名称: ICrashCallback.java
 * <p>
 * 类描述: crash信息回调
 * <p>
 *
 * @author darryrzhong
 * @since 2019/12/16 18:16
 */
public interface ICrashCallback
{
    void stackTrace(String stackTrace);
    
    void cause(String cause);
    
    void exception(String throwExceptionType, String throwClassName,
        String throwMethodName, int throwLineNumber);
    
    void throwable(Throwable throwable);
}
