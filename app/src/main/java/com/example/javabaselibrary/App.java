package com.example.javabaselibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import com.example.corelibrary.app.context.IAppConfig;
import com.example.corelibrary.app.context.IApplication;
import com.example.corelibrary.app.context.ObjectFactory;
import com.example.corelibrary.db.mmkv.MmkvHelper;
import com.example.corelibrary.utils.DeviceUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zthx.crashlibrary.core.Recovery;


import android.app.Application;
import android.content.Context;
import android.os.Looper;

/**
 * 类名称: .java
 * <p>
 * 类描述:
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/12/16 13:24
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        Recovery.getInstance()
                .debug(true)
                .showDebugInfo(true)
                // 当应用在后台时发生Crash，是否需要进行恢复
                .recoverInBackground(true)
                // 是否恢复整个Activity Stack，否则将恢复栈顶Activity
                .recoverStack(true)
                // 回退的界面
//                .mainPage(HomeActivity.class)
                // 正式版本不显示false
                .recoverEnabled(true)
                .callback(new CrashCallback())
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                // .silent(true,Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                // .skip(HomeActivity.class)
                .init(this);
        ObjectFactory.setApplication(new IApplication() {
            @Override
            public Context getApplicationContext() {
                return App.this.getApplicationContext();
            }

            @Override
            public Context getBaseContext() {
                return App.this.getBaseContext();
            }

            @Override
            public Looper getMainLooper() {
                return App.this.getMainLooper();
            }
        });

        ObjectFactory.setAppConfig(new IAppConfig() {
            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }

            @Override
            public int getVersionCode() {
                return 0;
            }

            @Override
            public boolean isSaveDebugLog() {
                return false;
            }

            @Override
            public void setSaveDebugLog(boolean save) {

            }

            @Override
            public String getBaseUrl() {
                return "http://core.xiaotibang.com";
            }

            @Override
            public String getStartDate() {
                return null;
            }

            @Override
            public String getStartTime() {
                return null;
            }

            @Override
            public String getIndexDir() {
                return null;
            }

            @Override
            public void setIndexDir() {

            }

            @Override
            public String getProjectName() {
                return null;
            }

            @Override
            public void setProjectName(String projectName) {

            }

            @Override
            public String getAppChannel() {
                return null;
            }
        });

        MmkvHelper.getInstance().initialize(this);

    }
}
