package com.zthx.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.zthx.ipc.model.Parameters;
import com.zthx.ipc.model.Request;
import com.zthx.ipc.model.Response;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 类名称: Channel.java
 * <p>
 * 类描述: 框架通信通道
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/11/12 13:06
 */
public class Channel
{
    private static final Channel ourInstance = new Channel();
    
    /**
     * 已经绑定的服务对应的ServiceConnect
     */
    private final ConcurrentHashMap<Class<? extends IPCService>, IPCServiceConnection> mServiceConnections =
        new ConcurrentHashMap<>();
    
    /**
     * 已经绑定过的服务对应的代理对象 IIPCService
     */
    private final ConcurrentHashMap<Class<? extends IPCService>, IIPCService> mBinders =
        new ConcurrentHashMap<>();
    
    /**
     * 已经绑定过的服务
     */
    private ConcurrentHashMap<Class<? extends IPCService>, Boolean> hadBindsMap =
        new ConcurrentHashMap<>();
    
    /**
     * 正在绑定的服务
     */
    private ConcurrentHashMap<Class<? extends IPCService>, Boolean> haveBindingMap =
        new ConcurrentHashMap<>();

    public static Channel getInstance() {
        return ourInstance;
    }
    private Channel() {
    }

    /**
     * 绑定服务
     * */
    public void bind(Context context,String packageName,Class<? extends  IPCService> service){
        IPCServiceConnection ipcServiceConnection;
        //判断是否已经绑定
        Boolean isBind = hadBindsMap.get(service);
        if (null != isBind && isBind){
            return;
        }
        //是否正在绑定
        Boolean isBinding = haveBindingMap.get(service);
        if (null != isBinding && isBinding){
            return;
        }

        //开始绑定了
        haveBindingMap.put(service,true);
        ipcServiceConnection = new IPCServiceConnection(service);
        mServiceConnections.put(service,ipcServiceConnection);

        Intent intent;
        if (!TextUtils.isEmpty(packageName)){
            //进行跨App的绑定

            intent = new Intent();
            intent.setClassName(packageName,service.getName());
        }else {
            intent = new Intent(context,service);
        }
        context.bindService(intent,new IPCServiceConnection(service),Context.BIND_AUTO_CREATE);

    }

    /**
     * 解绑服务
     * */
    public void unBind(Context context,Class<? extends IPCService> service){
        Boolean bound = hadBindsMap.get(service);
        if (null != bound && bound){
            IPCServiceConnection connection = mServiceConnections.get(service);
            if (connection != null){
                context.unbindService(connection);
            }
            hadBindsMap.put(service,false);
        }
    }



    class IPCServiceConnection implements ServiceConnection{

        private final Class<? extends IPCService> mService;

        public IPCServiceConnection(Class<? extends IPCService> mService) {
            this.mService = mService;
        }


        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
           //建立连接
            IIPCService binder = IIPCService.Stub.asInterface(iBinder);
            mBinders.put(mService,binder);
            hadBindsMap.put(mService,true);
            haveBindingMap.remove(mService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
              mBinders.remove(mService);
              hadBindsMap.remove(mService);
        }
    }

    /**
     * 向服务端发送执行请求
     * */
    public Response send(int type,Class<? extends IPCService> service,String serviceId,String methodName,Object[] parameters){
        Request request = new Request(type,serviceId,methodName,makeParameters(parameters));
        IIPCService binder = mBinders.get(service);
        try {
            return binder.send(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("执行失败",false);
        }

    }

    /**
     * 序列化参数信息
     * */
    private Parameters[] makeParameters(Object[] objects) {
       Parameters[] parameters;
       if (null != objects){
           parameters = new Parameters[objects.length];
           for (int i= 0;i<objects.length;i++){
               parameters[i] = new Parameters(objects[i].getClass().getName(),GsonUtils.toJson(objects[i]));
           }
       }else {
           parameters = new Parameters[0];
       }
       return parameters;
    }

}
