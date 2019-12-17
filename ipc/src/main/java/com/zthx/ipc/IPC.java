package com.zthx.ipc;

import android.content.Context;

import com.zthx.ipc.annotation.ServiceId;
import com.zthx.ipc.model.Request;
import com.zthx.ipc.model.Response;

import java.lang.reflect.Proxy;

/**
 * 类名称: IPC.java
 * <p>
 * 类描述: 整个框架的入口
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/11/12 10:04
 */
public class IPC
{
    
    /**
     * 注册接口 服务端需要暴露出去的服务
     * @param service
     */
    public static void register(Class<?> service)
    {
        Registry.getInstance().register(service);
    }
    
    /**
     * 连接本App其他进程(服务)
     * @param context
     * @param service
     */
    public static void connect(Context context,
        Class<? extends IPCService> service)
    {
        connect(context, null, service);
    }
    
    /**
     * 连接其他App进程服务
     * @param packageName apk包名
     */
    public static void connect(Context context, String packageName,
        Class<? extends IPCService> service)
    {
        Channel.getInstance().bind(context.getApplicationContext(),packageName,service);
    }

    /**
    * 断开连接
    * */
    public static void disConnect(Context context, Class<? extends IPCService> service){
        Channel.getInstance().unBind(context.getApplicationContext(),service);
    }

    /**
     * 获取客户端的代理对象
     * */
    public static  <T> T getInstanceFromName(Class<? extends IPCService> service,Class<T> classType,String methodNmae,Object... parameters){
        if (!classType.isInterface()){
            throw  new IllegalArgumentException("必须以接口进行通信");
        }
        //获取注解
        ServiceId serviceId = classType.getAnnotation(ServiceId.class);
        //服务端响应
        Response response = Channel.getInstance().send(Request.GET_INSTANCE,service,serviceId.value(),methodNmae,parameters);
        if (response.isSuccess()){
            //返回一个动态代理对象
            return getProxy(classType,serviceId,service);
        }
         return null;
    }

    /**
     * 获取代理对象
     * */
    public static <T> T getProxy(Class<T> classType, ServiceId serviceId, Class<? extends IPCService> service){
        ClassLoader classLoader = classType.getClassLoader();
        return (T) Proxy.newProxyInstance(classLoader,new Class[]{classType},new IPCInvocationHandler(service,serviceId.value()));

    }

}
