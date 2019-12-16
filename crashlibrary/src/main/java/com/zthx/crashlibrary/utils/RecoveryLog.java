package com.zthx.crashlibrary.utils;

import com.zthx.crashlibrary.core.Recovery;

import android.util.Log;

/**
 * Created by zhengxiaoyong on 16/8/26.
 */
public class RecoveryLog
{
    
    private static final String TAG = "Recovery";
    
    public static void e(String message)
    {
        if (Recovery.getInstance().isDebug())
            Log.e(TAG, message);
    }
}
