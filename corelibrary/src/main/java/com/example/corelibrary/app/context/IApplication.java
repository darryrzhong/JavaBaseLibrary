package com.example.corelibrary.app.context;

import android.content.Context;
import android.os.Looper;

/**
 * 应用模块 : app<p>
 * 类名称: IApplication.java<p>
 * 类描述: 全局上下文<p>
 *
 * @author darryrzhong
 * @since 2019/12/17 15:50
 */
public interface IApplication {
    /**
     * getApplicationContext
     *
     * @return ApplicationContext
     */
    Context getApplicationContext();

    /**
     * getBaseContext
     *
     * @return BaseContext
     */
    Context getBaseContext();

    /**
     * getMainLooper
     *
     * @return MainLooper
     */
    Looper getMainLooper();
}
