package com.example.corelibrary.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类名称: BaseResponse.java
 * <p>
 * 类描述: 与服务端约定的基本数据格式类型
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/8/29 15:43
 */
public class BaseResponse<T> implements Parcelable
{
    
    public static final Creator<BaseResponse> CREATOR =
        new Creator<BaseResponse>()
        {
            @Override
            public BaseResponse createFromParcel(Parcel in)
            {
                return new BaseResponse(in);
            }

            @Override
            public BaseResponse[] newArray(int size)
            {
                return new BaseResponse[size];
            }
        };
    /**
     * 响应状态码
     */
    private int Code;
    /**
     * 响应数据
     */
    private T Data;
    /**
     * 响应提示
     */
    private String Message;
    
    public BaseResponse()
    {
    }
    
    protected BaseResponse(Parcel in)
    {
        Code = in.readInt();
        Message = in.readString();
    }
    
    public int getCode()
    {
        return Code;
    }
    
    public void setCode(int code)
    {
        Code = code;
    }
    
    public T getData()
    {
        return Data;
    }
    
    public void setData(T data)
    {
        Data = data;
    }
    
    public String getMessage()
    {
        return Message;
    }
    
    public void setMessage(String message)
    {
        Message = message;
    }
    
    @Override
    public int describeContents()
    {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(Code);
        dest.writeString(Message);
    }
    
    /**
     * 判断请求是否成功 根据服务端约定
     * 
     * @return bool
     */
    public boolean isSuccess()
    {
        return getCode() == ApiStatusCode.CODE_SUCCESS;
    }
}
