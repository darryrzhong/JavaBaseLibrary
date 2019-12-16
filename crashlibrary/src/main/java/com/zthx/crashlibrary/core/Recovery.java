package com.zthx.crashlibrary.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zthx.crashlibrary.callback.CrashActivityLifecycleCallback;
import com.zthx.crashlibrary.callback.ICrashCallback;
import com.zthx.crashlibrary.exception.RecoveryException;
import com.zthx.crashlibrary.utils.RecoveryLog;
import com.zthx.crashlibrary.utils.RecoveryUtil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.Keep;

/**
 * Created by zhengxiaoyong on 16/8/26.
 */
public class Recovery
{
    
    private static final Object LOCK = new Object();
    private volatile static Recovery sInstance;
    private Context mContext;
    
    private boolean isDebug = false;
    
    private boolean isShowDebug = false;
    
    /**
     * The default to restore the stack.
     */
    private boolean isRecoverStack = true;
    
    /**
     * The default is not restore the background process.
     */
    private boolean isRecoverInBackground = false;
    
    private Class<? extends Activity> mMainPageClass;
    
    private ICrashCallback mCallback;
    
    private boolean isSilentEnabled = false;
    
    private SilentMode mSilentMode = SilentMode.RECOVER_ACTIVITY_STACK;
    
    private List<Class<? extends Activity>> mSkipActivities = new ArrayList<>();
    
    /**
     * Whether to enter recovery mode.
     */
    private boolean isRecoverEnabled = true;
    
    private Recovery()
    {
    }
    
    public static Recovery getInstance()
    {
        if (sInstance == null)
        {
            synchronized (LOCK)
            {
                if (sInstance == null)
                {
                    sInstance = new Recovery();
                }
            }
        }
        return sInstance;
    }
    
    public void init(Context context)
    {
        if (context == null)
        {
            throw new RecoveryException("Context can not be null!");
        }
        if (!(context instanceof Application))
        {
            context = context.getApplicationContext();
        }
        mContext = context;
        if (!RecoveryUtil.isMainProcess(context))
        {
            return;
        }
        registerRecoveryHandler();
        registerRecoveryLifecycleCallback();
    }
    
    public Recovery debug(boolean isDebug)
    {
        this.isDebug = isDebug;
        return this;
    }
    
    public Recovery showDebugInfo(boolean isShowDebug)
    {
        this.isShowDebug = isShowDebug;
        return this;
    }
    
    public Recovery recoverStack(boolean isRecoverStack)
    {
        this.isRecoverStack = isRecoverStack;
        return this;
    }
    
    public Recovery recoverInBackground(boolean isRecoverInBackground)
    {
        this.isRecoverInBackground = isRecoverInBackground;
        return this;
    }
    
    public Recovery mainPage(Class<? extends Activity> clazz)
    {
        this.mMainPageClass = clazz;
        return this;
    }
    
    public Recovery callback(ICrashCallback callback)
    {
        this.mCallback = callback;
        return this;
    }
    
    public Recovery silent(boolean enabled, SilentMode mode)
    {
        this.isSilentEnabled = enabled;
        this.mSilentMode =
            mode == null ? SilentMode.RECOVER_ACTIVITY_STACK : mode;
        return this;
    }
    
    @SafeVarargs
    public final Recovery skip(Class<? extends Activity>... activities)
    {
        if (activities == null)
        {
            return this;
        }
        mSkipActivities.addAll(Arrays.asList(activities));
        return this;
    }
    
    public Recovery recoverEnabled(boolean enabled)
    {
        this.isRecoverEnabled = enabled;
        return this;
    }
    
    private void registerRecoveryHandler()
    {
        RecoveryHandler.newInstance(Thread.getDefaultUncaughtExceptionHandler())
            .setCallback(mCallback)
            .register();
    }
    
    private void registerRecoveryLifecycleCallback()
    {
        ((Application)getContext()).registerActivityLifecycleCallbacks(
            new CrashActivityLifecycleCallback());
    }
    
    @Keep
    private void registerRecoveryProxy()
    {
        // OS version in the 5.0 ~ 6.0 don`t register agent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return;
        }
        if (mMainPageClass == null)
        {
            return;
        }
        if (!RecoveryUtil.isMainProcess(RecoveryUtil.checkNotNull(getContext(),
            "The context is not initialized")))
        {
            return;
        }
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    boolean isSuccess =
                        RecoveryComponentHook.hookActivityManagerProxy();
                    RecoveryLog.e("hook is success:" + isSuccess);
                    if (isSuccess)
                    {
                        break;
                    }
                }
            }
        }).start();
    }
    
    public Context getContext()
    {
        if (mContext == null)
        {
            mContext = ApplicationLoader.get();
        }
        return RecoveryUtil.checkNotNull(mContext,
            "The context is not initialized");
    }
    
    public boolean isDebug()
    {
        return isDebug;
    }
    
    public boolean isShowDebug()
    {
        return isShowDebug;
    }
    
    boolean isRecoverInBackground()
    {
        return isRecoverInBackground;
    }
    
    boolean isRecoverStack()
    {
        return isRecoverStack;
    }
    
    boolean isRecoverEnabled()
    {
        return isRecoverEnabled;
    }
    
    Class<? extends Activity> getMainPageClass()
    {
        return mMainPageClass;
    }
    
    boolean isSilentEnabled()
    {
        return isSilentEnabled;
    }
    
    SilentMode getSilentMode()
    {
        return mSilentMode;
    }
    
    public List<Class<? extends Activity>> getSkipActivities()
    {
        return mSkipActivities;
    }
    
    public enum SilentMode
    {
        RESTART(1), RECOVER_ACTIVITY_STACK(2), RECOVER_TOP_ACTIVITY(
            3), RESTART_AND_CLEAR(4);
        
        int value;
        
        SilentMode(int value)
        {
            this.value = value;
        }
        
        public static SilentMode getMode(int value)
        {
            switch (value)
            {
                case 1:
                    return RESTART;
                case 2:
                    return RECOVER_ACTIVITY_STACK;
                case 3:
                    return RECOVER_TOP_ACTIVITY;
                case 4:
                    return RESTART_AND_CLEAR;
                default:
                    return RECOVER_ACTIVITY_STACK;
            }
        }
        
        public int getValue()
        {
            return value;
        }
    }
}
