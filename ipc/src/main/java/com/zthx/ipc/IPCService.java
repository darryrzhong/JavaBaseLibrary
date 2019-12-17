package com.zthx.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.zthx.ipc.model.Parameters;
import com.zthx.ipc.model.Request;
import com.zthx.ipc.model.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类名称: IPCService.java<p>
 * 类描述: 服务端用来进行通信的Service<p>
 * Company: 江苏智体互享科技有限公司<p>
 *
 * @author darryrzhong
 * @since 2019/11/12 10:09
 */
public  abstract class IPCService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new IIPCService.Stub() {
            @Override
            public Response send(Request request) throws RemoteException {
                String methodName = request.getMethodName();
                Parameters[] parameters = request.getParameters();
                String serviceId = request.getServiceId();
                Object[] objects = restoreParameters(parameters);

                //从方法表中取得对应的 Method对象
                Method method = Registry.getInstance().findMethod(serviceId,methodName,objects);
                //客户端请求的类型
                switch (request.getType()){
                    //执行单例方法 静态方法
                    case Request.GET_INSTANCE:
                        try {
                            Object result = method.invoke(null,objects);
                            //单例类的serviceId 与单例对象保存
                            Registry.getInstance().putInstanceObject(serviceId,result);
                            return new Response(null,true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new Response(null,false);
                        }
                        //执行普通方法
                        case Request.GET_METHOD:
                            //获得执行结果
                            try {
                                Object instanceObject = Registry.getInstance().getInstanceObject(serviceId);
                                Object result = method.invoke(instanceObject,objects);
                                return  new Response(GsonUtils.toJson(result),true);

                            } catch (Exception e) {
                                e.printStackTrace();
                                return new Response(null,false);
                            }
                            default:
                                return new Response(null,false);
                }
            }
        };
    }

    /**
     * 将方法参数反序列化回去
     * */
    protected  Object[] restoreParameters(Parameters[] parameters){
        Object[] objects = new Object[parameters.length];
        for (int i = 0;i<parameters.length;i++){
            Parameters parameter = parameters[i];
            //还原
            try {
                objects[i] = GsonUtils.fromJson(parameter.getValue(),Class.forName(parameter.getType()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public static class IPCService0 extends IPCService {
    }

    public static class IPCService1 extends IPCService {
    }

    public static class IPCService2 extends IPCService {
    }

    public static class IPCService3 extends IPCService {
    }

}
