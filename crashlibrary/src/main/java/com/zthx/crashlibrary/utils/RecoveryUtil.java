package com.zthx.crashlibrary.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.zthx.crashlibrary.core.Recovery;
import com.zthx.crashlibrary.exception.RecoveryException;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;

/**
 * Created by zhengxiaoyong on 16/8/28.
 */
public class RecoveryUtil {

    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private RecoveryUtil() {
        throw new RecoveryException("Stub!");
    }

    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new RecoveryException(String.valueOf(message));
        }
        return t;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        if (context == null || intent == null) {
            return false;
        }
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static boolean isAppInBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return true;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

    public static String getAppName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        CharSequence charSequence = packageManager.getApplicationLabel(applicationInfo);
        return charSequence == null ? "" : (String) charSequence;
    }

    public static boolean isMainProcess(Context context) {
        try {
            ActivityManager am = ((ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE));
            List<ActivityManager.RunningAppProcessInfo> processInfo = am.getRunningAppProcesses();
            String mainProcessName = context.getPackageName();
            int myPid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo info : processInfo) {
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static File getDataDir() {
        return new File(File.separator + "data" + File.separator + "data" + File.separator + Recovery.getInstance().getContext().getPackageName());
    }

    private static File getExternalDataDir() {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = Recovery.getInstance().getContext().getExternalCacheDir();
        }
        return file == null ? null : file.getParentFile();
    }

    private static boolean clearAppData(File dir) {
        if (dir == null || !dir.isDirectory() || !dir.exists()) {
            return false;
        }
        File[] files = dir.listFiles();
        int length = files.length;
        for (int i = 0; i < length; i++) {
            File file = files[i];
            if (file == null) {
                continue;
            }
            if (file.isFile() && file.exists()) {
                boolean result = file.delete();
                RecoveryLog.e(file.getName() + (result ? " delete success!" : " delete failed!"));
                continue;
            }
            if (file.isDirectory() && file.exists()) {
                clearAppData(file);
            }
        }
        return true;
    }

    public static void clearApplicationData() {
        clearAppData(getDataDir());
        clearAppData(getExternalDataDir());
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static DateFormat getDateFormat() {
        return DATE_FORMAT_THREAD_LOCAL.get();
    }
}
