package com.zthx.ipc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类名称: Response.java<p>
 * 类描述: 服务端返回的数据 json<p>
 * Company: 江苏智体互享科技有限公司<p>
 *
 * @author darryrzhong
 * @since 2019/11/12 11:14
 */
public class Response implements Parcelable {

    /**
     * 服务端返回的数据  json
     * */
    private String source;

    /**
     * 是否执行成功
     * */
    private boolean isSuccess;


    protected  Response(Parcel in){
        source = in.readString();
        isSuccess = in.readByte() != 0;
    }

    public Response(String source, boolean isSuccess) {
        this.source = source;
        this.isSuccess = isSuccess;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(source);
        parcel.writeByte((byte) (isSuccess ? 1 : 0));
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

}
