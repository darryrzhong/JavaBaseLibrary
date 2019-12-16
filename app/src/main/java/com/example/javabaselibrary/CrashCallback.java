package com.example.javabaselibrary;

import com.orhanobut.logger.Logger;
import com.zthx.crashlibrary.callback.ICrashCallback;

/**
 * Created by darryrzhong on 2019/5/7
 */

public class CrashCallback implements ICrashCallback
{

    @Override
    public void stackTrace(String exceptionMessage)
    {
        Logger.e("zxy   "+"exceptionMessage:" + exceptionMessage);
    }

    @Override
    public void cause(String cause)
    {
        Logger.e("zxy   "+"cause:" + cause);
    }

    @Override
    public void exception(String exceptionType, String throwClassName,
        String throwMethodName, int throwLineNumber)
    {
        Logger.e("zxy   "+"exceptionClassName:" + exceptionType);
        Logger.e("zxy   "+"throwClassName:" + throwClassName);
        Logger.e("zxy   "+ "throwMethodName:" + throwMethodName);
        Logger.e("zxy   "+"throwLineNumber:" + throwLineNumber);
    }

    @Override
    public void throwable(Throwable throwable)
    {

    }
}
