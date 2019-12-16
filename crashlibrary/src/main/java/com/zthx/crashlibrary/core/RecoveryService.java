package com.zthx.crashlibrary.core;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.zthx.crashlibrary.core.RecoveryActivity.RECOVERY_MODE_ACTIVE;

import java.util.ArrayList;

import com.zthx.crashlibrary.utils.RecoverySilentSharedPrefsUtil;
import com.zthx.crashlibrary.utils.RecoveryUtil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

/**
 * Created by zhengxiaoyong on 16/8/29.
 */
public class RecoveryService extends Service
{
    
    static final String RECOVERY_SILENT_MODE_VALUE =
        "recovery_silent_mode_value";
    
    public RecoveryService()
    {
    }
    
    public static void start(Context context, Intent intent)
    {
        try
        {
            if (Build.VERSION.SDK_INT >= 26)
            {
                context.startForegroundService(intent);
            }
            else
            {
                context.startService(intent);
            }
        }
        catch (Throwable t)
        {
            // ignore.
        }
    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (Build.VERSION.SDK_INT >= 26)
        {
            try
            {
                NotificationManager notificationManager =
                    (NotificationManager)getApplication()
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel channel =
                    new NotificationChannel("channel_recovery_1", "Recovery",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
                Notification notification =
                    new Notification.Builder(getApplication(),
                        "channel_recovery_1").build();
                startForeground(1024, notification);
            }
            catch (Throwable t)
            {
                // ignore.
            }
        }

        if (RecoverySilentSharedPrefsUtil.shouldClearAppNotRestart())
        {
            // If restore failed twice within 30 seconds, will only delete data
            // and not restored.
            RecoverySilentSharedPrefsUtil.clear();
            stopSelf();
            killProcess();
        }

        Recovery.SilentMode mode = getRecoverySilentMode(intent);

        if (mode == Recovery.SilentMode.RESTART)
        {
            restart();
        }
        else if (mode == Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
        {
            recoverActivityStack(intent);
        }
        else if (mode == Recovery.SilentMode.RECOVER_TOP_ACTIVITY)
        {
            recoverTopActivity(intent);
        }
        else if (mode == Recovery.SilentMode.RESTART_AND_CLEAR)
        {
            restartAndClear();
        }
        else
        {
            stopSelf();
        }
        return Service.START_NOT_STICKY;
    }
    
    private void restartAndClear()
    {
        RecoveryUtil.clearApplicationData();
        restart();
    }
    
    private void recoverActivityStack(Intent o)
    {
        ArrayList<Intent> intents = getRecoveryIntents(o);
        if (intents != null && !intents.isEmpty())
        {
            ArrayList<Intent> availableIntents = new ArrayList<>();
            for (Intent tmp : intents)
            {
                if (tmp != null && RecoveryUtil.isIntentAvailable(this, tmp))
                {
                    tmp.setExtrasClassLoader(getClassLoader());
                    availableIntents.add(tmp);
                }
            }
            if (!availableIntents.isEmpty())
            {
                availableIntents.get(0)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                availableIntents.get(availableIntents.size() - 1)
                    .putExtra(RECOVERY_MODE_ACTIVE, true);
                startActivities(availableIntents
                    .toArray(new Intent[availableIntents.size()]));
                stopSelf();
                return;
            }
        }
        restart();
    }
    
    private void recoverTopActivity(Intent o)
    {
        Intent intent = getRecoveryIntent(o);
        if (intent != null && RecoveryUtil.isIntentAvailable(this, intent))
        {
            intent.setExtrasClassLoader(getClassLoader());
            intent.addFlags(
                FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(RECOVERY_MODE_ACTIVE, true);
            startActivity(intent);
            stopSelf();
            return;
        }
        restart();
    }
    
    private Intent getRecoveryIntent(Intent intent)
    {
        boolean hasRecoverIntent =
            intent.hasExtra(RecoveryStore.RECOVERY_INTENT);
        if (!hasRecoverIntent)
            return null;
        return intent.getParcelableExtra(RecoveryStore.RECOVERY_INTENT);
    }
    
    private ArrayList<Intent> getRecoveryIntents(Intent intent)
    {
        boolean hasRecoveryIntents =
            intent.hasExtra(RecoveryStore.RECOVERY_INTENTS);
        if (!hasRecoveryIntents)
            return null;
        return intent
            .getParcelableArrayListExtra(RecoveryStore.RECOVERY_INTENTS);
    }
    
    private Recovery.SilentMode getRecoverySilentMode(Intent intent)
    {
        int value = intent.getIntExtra(RECOVERY_SILENT_MODE_VALUE, -1);
        return Recovery.SilentMode.getMode(value);
    }
    
    private void restart()
    {
        Intent launchIntent = getApplication().getPackageManager()
            .getLaunchIntentForPackage(this.getPackageName());
        if (launchIntent != null)
        {
            launchIntent.addFlags(
                FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(launchIntent);
        }
        stopSelf();
    }
    
    private void killProcess()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        killProcess();
    }
}
