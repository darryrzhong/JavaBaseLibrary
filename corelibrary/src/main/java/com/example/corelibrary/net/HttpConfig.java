package com.example.corelibrary.net;

/**
 * 应用模块: 网络层
 * <p>
 * 类名称: HttpConfig.java
 * <p>
 * 类描述: http请求配置
 * <p>
 *
 * @author darryrzhong
 * @since 2019/9/1 22:29
 */
public interface HttpConfig
{
    /**
     * 重试最大次数 假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
     */
    int RETRY_MAX_NUM = 1;
    
    /**
     * 连接超时 （单位 秒）
     */
    long CONNECT_TIME_OUT = 10;
    
    /**
     * 读取超时
     */
    long READ_TIMEOUT = 10;
    
    /**
     * 写入超时
     */
    long WRITE_TIMEOUT = 10;
    
}
