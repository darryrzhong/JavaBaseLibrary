package com.zthx.ipc;

import com.zthx.ipc.model.Request;
import com.zthx.ipc.model.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 类名称: IPCInvocationHandler.java<p>
 * 类描述: 动态代理<p>
 * Company: 江苏智体互享科技有限公司<p>
 *
 * @author darryrzhong
 * @since 2019/11/12 10:22
 */
public class IPCInvocationHandler implements InvocationHandler {

  private final Class<? extends IPCService> service;

  private final String serviceId;

    public IPCInvocationHandler(Class<? extends IPCService> service, String serviceId) {
        this.service = service;
        this.serviceId = serviceId;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //向服务端发起方法执行请求
           Response response = Channel.getInstance().send(Request.GET_METHOD,service,serviceId,method.getName(),args);
           if (response.isSuccess()){
               Class<?> returnType = method.getReturnType();
               if (returnType != void.class && returnType != Void.class){
                   //返回的json字符串
                   String source = response.getSource();
                   //反序列化对应的对象
                   return GsonUtils.fromJson(source,returnType);
               }
           }
        return null;
    }
}
