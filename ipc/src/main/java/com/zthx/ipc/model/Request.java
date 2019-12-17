package com.zthx.ipc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类名称: Request.java<p>
 * 类描述: 客户端请求服务端执行的方法参数<p>
 * Company: 江苏智体互享科技有限公司<p>
 *
 * @author darryrzhong
 * @since 2019/11/12 11:14
 */
public class Request implements Parcelable {

    /**
     * 获得单例对象
     * */
    public static final int GET_INSTANCE = 0;
    /**
     * 执行方法
     * */
    public static final int GET_METHOD = 1;

    /**
     * 执请求类型
     * */
    private int type;

    /**
     * 请求的服务
     * */
    private String serviceId;

    /**
     * 请求的方法名
     * */
    private String methodName;

    /**
     * 执行的方法的参数
     * */
    private Parameters[] parameters;

    public Request(int type, String serviceId, String methodName, Parameters[] parameters) {
        this.type = type;
        this.serviceId = serviceId;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    protected Request(Parcel in){
        type = in.readInt();
        serviceId = in.readString();
        methodName = in.readString();
        parameters = in.createTypedArray(Parameters.CREATOR);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Parameters[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameters[] parameters) {
        this.parameters = parameters;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeString(serviceId);
        parcel.writeString(methodName);
        parcel.writeTypedArray(parameters, i);
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

}
