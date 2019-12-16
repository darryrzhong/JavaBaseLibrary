package com.zthx.crashlibrary.callback;

import com.zthx.crashlibrary.core.Recovery;
import com.zthx.crashlibrary.core.RecoveryActivity;
import com.zthx.crashlibrary.core.RecoveryStore;
import com.zthx.crashlibrary.utils.Reflect;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * 应用模块 crash 捕获:
 * <p>
 * 类名称: CrashActivityLifecycleCallback.java
 * <p>
 * 类描述: 监听发生crash的Activity生命周期
 * <p>
 *
 * @author darryrzhong
 * @since 2019/12/16 18:14
 */
public class CrashActivityLifecycleCallback
    implements Application.ActivityLifecycleCallbacks
{
    @Override
    public void onActivityCreated(final Activity activity,
        Bundle savedInstanceState)
    {
    }
    
    @Override
    public void onActivityStarted(final Activity activity)
    {
        boolean isLegal = RecoveryStore.getInstance().verifyActivity(activity);
        if (!isLegal)
        {
            return;
        }
        
        if (activity.getIntent()
            .getBooleanExtra(RecoveryActivity.RECOVERY_MODE_ACTIVE, false))
        {
            Reflect.on(Recovery.class)
                .method("registerRecoveryProxy")
                .invoke(Recovery.getInstance());
        }
        
        if (RecoveryStore.getInstance().contains(activity))
        {
            return;
        }
        
        Window window = activity.getWindow();
        if (window != null)
        {
            View decorView = window.getDecorView();
            if (decorView == null)
            {
                return;
            }
            decorView.post(new Runnable()
            {
                @Override
                public void run()
                {
                    RecoveryStore.getInstance().putActivity(activity);
                    Object o = activity.getIntent().clone();
                    RecoveryStore.getInstance().setIntent((Intent)o);
                }
            });
        }
        
    }
    
    @Override
    public void onActivityResumed(Activity activity)
    {
    }
    
    @Override
    public void onActivityPaused(Activity activity)
    {
    }
    
    @Override
    public void onActivityStopped(Activity activity)
    {
    }
    
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {
        
    }
    
    @Override
    public void onActivityDestroyed(Activity activity)
    {
        RecoveryStore.getInstance().removeActivity(activity);
    }
}
