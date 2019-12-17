package com.example.javabaselibrary;

import java.io.Serializable;

/**
 * Created by darryrzhong
 * on 2019/4/18
 */
public class Phone implements Serializable {


    /**
     * Code : 1
     * Data : {}
     * Message : 成功
     */

    private int Code;
    private DataBean Data;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public static class DataBean {
    }
}
