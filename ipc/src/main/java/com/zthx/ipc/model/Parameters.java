package com.zthx.ipc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类名称: Parameters.java<p>
 * 类描述: 执行方法的参数<p>
 * Company: 江苏智体互享科技有限公司<p>
 *
 * @author darryrzhong
 * @since 2019/11/12 11:27
 */
public class Parameters implements Parcelable {

    /**
     * 参数类型
     * */
    private String type;

    /**
     * 参数值 (json序列化)
     * */
    private String value;

    protected Parameters(Parcel in){
        type = in.readString();
        value = in.readString();
    }

    public Parameters(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(value);
    }

    public static final Creator<Parameters> CREATOR = new Creator<Parameters>() {
        @Override
        public Parameters createFromParcel(Parcel in) {
            return new Parameters(in);
        }

        @Override
        public Parameters[] newArray(int size) {
            return new Parameters[size];
        }
    };

}
