package com.example.corelibrary.app.context;

/**
 * 应用模块 : app<p>
 * 类名称: IAppConfig.java<p>
 * 类描述: APP运行时上下文<p>
 *
 * @author darryrzhong
 * @since 2019/12/17 15:48
 */
public interface IAppConfig {
    /**
     * 是否为DEBUG 模式
     *
     * @return true false
     */
    boolean isDebug();

    /**
     * 版本号
     *
     * @return app版本号
     */
    int getVersionCode();

    /**
     * 是否保存日志
     *
     * @return true false
     */
    boolean isSaveDebugLog();

    /**
     * 保存Degbug日志
     *
     * @param save true false
     */
    void setSaveDebugLog(boolean save);

    /**
     * 网络请求地址
     *
     * @return 请求地址
     */
    String getBaseUrl();

    /**
     * app启动 -日期
     *
     * @return 日期
     */
    String getStartDate();

    /**
     * app启动 -时间
     *
     * @return 时间
     */
    String getStartTime();

    /**
     * 主目录
     *
     * @return 主目录
     */
    String getIndexDir();

    /**
     * 设置主目录
     */
    void setIndexDir();

    /**
     * 工程名
     *
     * @return 工程名
     */
    String getProjectName();

    /**
     * 设置渠道
     *
     * @param projectName 渠道名
     */
    void setProjectName(String projectName);

    /**
     * 获取渠道
     *
     * @return 渠道
     */
    String getAppChannel();

}
