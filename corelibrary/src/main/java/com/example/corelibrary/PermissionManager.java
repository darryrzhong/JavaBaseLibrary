package com.example.corelibrary;

import android.Manifest;
import android.os.Build;

/**
 * 应用模块 : <p>
 * 类名称: .java<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/16 14:45
 */
public final class PermissionManager {

    /**
     * 读写日历。
     * */
    public static final String[] CALENDAR;
    /**
     * 相机。
     * */
    public static final String[] CAMERA;
    /**
     * 读写联系人。
     * */
    public static final String[] CONTACTS;
    /**
     * 读位置信息。
     * */
    public static final String[] LOCATION;
    /**
     * 使用麦克风。
     * */
    public static final String[] MICROPHONE;
    /**
     * 读电话状态、打电话、读写电话记录。
     * */
    public static final String[] PHONE;
    /**
     * 传感器。
     * */
    public static final String[] SENSORS;
    /**
     * 读写短信、收发短信。
     * */
    public static final String[] SMS;
    /**
     * 读写存储卡。
     * */
    public static final String[] STORAGE;

    /**
     * App运行时所需的全部权限
     * */
//    public static final String[] RUN_PERMISSIONS;

    static {
        //Android 6.0 以下权限自动授予
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            CALENDAR = new String[]{};
            CAMERA = new String[]{};
            CONTACTS = new String[]{};
            LOCATION = new String[]{};
            MICROPHONE = new String[]{};
            PHONE = new String[]{};
            SENSORS = new String[]{};
            SMS = new String[]{};
            STORAGE = new String[]{};
//            RUN_PERMISSIONS = new String[]{};
        } else {
            //运行时权限申请
            CALENDAR = new String[]{
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR};

            CAMERA = new String[]{
                    Manifest.permission.CAMERA};

            CONTACTS = new String[]{
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.GET_ACCOUNTS};

            LOCATION = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};

            MICROPHONE = new String[]{
                    Manifest.permission.RECORD_AUDIO};

            PHONE = new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.USE_SIP,
                    Manifest.permission.PROCESS_OUTGOING_CALLS};

            SENSORS = new String[]{
                    Manifest.permission.BODY_SENSORS};

            SMS = new String[]{
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_WAP_PUSH,
                    Manifest.permission.RECEIVE_MMS};

            STORAGE = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
    }
}
