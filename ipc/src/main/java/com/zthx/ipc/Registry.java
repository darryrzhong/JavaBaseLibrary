package com.zthx.ipc;

import android.telephony.mbms.MbmsErrors;

import com.zthx.ipc.annotation.ServiceId;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类名称: Registry.java<p>
 * 类描述: 负责记录 服务端注册的信息<p>
 * Company: 江苏智体互享科技有限公司<p>
 *
 * @author darryrzhong
 * @since 2019/11/12 14:11
 */
public class Registry {

    /**
     * 服务表
     * */
    private ConcurrentHashMap<String,Class<?>> mServices = new ConcurrentHashMap<>();

    /**
     * 方法表
     * */
    private ConcurrentHashMap<Class<?>,ConcurrentHashMap<String, Method>> mMethods = new ConcurrentHashMap<>();

    /**
     * 实例对象
     * */
    private ConcurrentHashMap<String, Object> objects =
            new ConcurrentHashMap<>();

    /**
     * 单例
     * */
    private Registry(){

    }

    private static class RegistryHolder{
        private static volatile Registry mInstance = new Registry();
    }

    public static Registry getInstance(){
        return RegistryHolder.mInstance;
    }

    /**
     * 注册服务
     * @param service 通过ServiceId注解的服务的类
     *
     * */
    public void register(Class<?> service){
        //服务id与class对应的表
        ServiceId serviceId = service.getAnnotation(ServiceId.class);
        if (null == serviceId){
            throw  new RuntimeException("必须使用ServiceId注解的服务进行注册");
        }
        String value = serviceId.value();
        mServices.put(value,service);

        //class与方法对应的表
        ConcurrentHashMap<String,Method> methods = mMethods.get(service);
        if (null == methods){
            methods = new ConcurrentHashMap<>();
            mMethods.put(service,methods);
        }
        for (Method method :service.getMethods()){
            //因为可能有重载的方法存在,所以不能直接以方法名作为Key! 采用方法名加参数作为Key;
            StringBuilder builder = new StringBuilder(method.getName());
            builder.append("(");

            //方法的参数类型数组
            Class<?> [] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 0){
                builder.append(parameterTypes[0].getName());
            }

            for (int i = 1; i<parameterTypes.length;i++){
                builder.append(",").append(parameterTypes[i].getName());
            }

            builder.append(")");

            methods.put(builder.toString(),method);
        }

//        Set<Map.Entry<String, Class<?>>> entries = mServices.entrySet();
//        for (Map.Entry<String, Class<?>> entry : entries) {
//            System.out.println("服务表:" + entry.getKey() + " = " + entry.getValue());
//        }
//
//        Set<Map.Entry<Class<?>, ConcurrentHashMap<String, Method>>> entrySet = mMethods.entrySet();
//        for (Map.Entry<Class<?>, ConcurrentHashMap<String, Method>> m : entrySet) {
//            System.out.println("方法表：" + m.getKey());
//            ConcurrentHashMap<String, Method> value1 = m.getValue();
//            for (Map.Entry<String, Method> stringMethodEntry : value1.entrySet()) {
//                System.out.println(" " + stringMethodEntry.getKey());
//            }
//        }

    }


    /**
     * 查找对应的Method
     * */
    public Method findMethod(String serviceId,String methodName,Object[] objects){
        Class<?> service = mServices.get(serviceId);
        if (null == service){
            return null;
        }
        ConcurrentHashMap<String, Method> methods = mMethods.get(service);
        if (null == methods){
            return null;
        }
        StringBuilder builder = new StringBuilder(methodName);
        builder.append("(");
        if (objects.length != 0) {
            builder.append(objects[0].getClass().getName());
        }
        for (int i = 1; i < objects.length; i++) {
            builder.append(",").append(objects[i].getClass().getName());
        }
        builder.append(")");
        return methods.get(builder.toString());
    }


    public void putInstanceObject(String serviceId, Object object) {
        objects.put(serviceId, object);
    }

    public Object getInstanceObject(String serviceId) {
        return objects.get(serviceId);
    }


}
