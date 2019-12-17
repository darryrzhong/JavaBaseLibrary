package com.example.corelibrary.app.context;

/**
 * 应用模块 app: <p>
 * 类名称: ObjectFactory.java<p>
 * 类描述: 全局静态对象<p>
 *
 * @author darryrzhong
 * @since 2019/12/17 15:53
 */
public class ObjectFactory {
    /**
     * 全局变量
     */
    private static IApplication mApplication;

    /**
     * 退出
     */
    private static IExitApplication mExitApplication;

    /**
     * App 配置
     */
    private static IAppConfig mAppConfig;


    /**
     * 获取 application
     *
     * @return application
     */
    public static IApplication getApplication()
    {
        return mApplication;
    }

    /**
     * 设置 application
     */
    public static void setApplication(IApplication application)
    {
        if (mApplication == null)
        {
            mApplication = application;
        }
    }

    /**
     * 获取 ExitApplication
     *
     * @return ExitApplication
     */
    public static IExitApplication getExitApplication()
    {
        return mExitApplication;
    }

    /**
     * 设置 ExitApplication
     *
     * @param exitApplication exitApplication
     */
    public static void setExitApplication(IExitApplication exitApplication)
    {
        if (mExitApplication == null)
        {
            mExitApplication = exitApplication;
        }
    }

    /**
     * 获取APP参数
     *
     * @return APP参数
     */
    public static IAppConfig getAppConfig()
    {
        return mAppConfig;

    }

    /**
     * 设置APP参数
     *
     * @param appConfig app参数
     */
    public static void setAppConfig(IAppConfig appConfig)
    {
        if (mAppConfig == null)
        {
            mAppConfig = appConfig;
        }
    }

}
