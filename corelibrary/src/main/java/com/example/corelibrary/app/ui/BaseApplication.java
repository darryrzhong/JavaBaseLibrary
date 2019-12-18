package com.example.corelibrary.app.ui;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 应用模块 : <p>
 * 类名称: .java<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/18 15:14
 */
public  abstract class BaseApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
