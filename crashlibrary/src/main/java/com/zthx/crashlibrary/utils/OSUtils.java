package com.zthx.crashlibrary.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 应用模块: <p>
 * 类名称:<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/16  22:06
 */
public class OSUtils {

    static final String sepHead = "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***";
    static final String timeFormatterStr = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /**
     * 获取进程名
     * */
   public static String getProcessName(Context ctx, int pid) {

        //get from ActivityManager
        try {
            ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
            if (manager != null) {
                List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
                if (processInfoList != null) {
                    for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
                        if (processInfo.pid == pid && !TextUtils.isEmpty(processInfo.processName)) {
                            return processInfo.processName; //OK
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }

        //get from /proc/PID/cmdline
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = br.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
                if (!TextUtils.isEmpty(processName)) {
                    return processName; //OK
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ignored) {
            }
        }

        //failed
        return null;
    }

    private static final String[] suPathname = {
            "/data/local/su",
            "/data/local/bin/su",
            "/data/local/xbin/su",
            "/system/xbin/su",
            "/system/bin/su",
            "/system/bin/.ext/su",
            "/system/bin/failsafe/su",
            "/system/sd/xbin/su",
            "/system/usr/we-need-root/su",
            "/sbin/su",
            "/su/bin/su"};

    static boolean isRoot() {
        try {
            for (String path : suPathname) {
                File file = new File(path);
                if (file.exists()) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    static String getAbiList() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            return android.text.TextUtils.join(",", Build.SUPPORTED_ABIS);
        } else {
            String abi = Build.CPU_ABI;
            String abi2 = Build.CPU_ABI2;
            if (TextUtils.isEmpty(abi2)) {
                return abi;
            } else {
                return abi + "," + abi2;
            }
        }
    }

   public static String getAppVersion(Context ctx) {
        String appVersion = null;

        try {
            appVersion = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (Exception ignored) {
        }

        if (TextUtils.isEmpty(appVersion)) {
            appVersion = "unknown";
        }

        return appVersion;
    }




  public   static String getDeviceInfo(Date startTime, Date crashTime, String crashType, String appId, String appVersion) {
        DateFormat timeFormatter = new SimpleDateFormat(OSUtils.timeFormatterStr, Locale.US);

        return OSUtils.sepHead + "\n"
                + "Crash type: '" + crashType + "'\n"
                + "Start time: '" + timeFormatter.format(startTime) + "'\n"
                + "Crash time: '" + timeFormatter.format(crashTime) + "'\n"
                + "App ID: '" + appId + "'\n"
                + "App version: '" + appVersion + "'\n"
                + "Rooted: '" + (OSUtils.isRoot() ? "Yes" : "No") + "'\n"
                + "API level: '" + Build.VERSION.SDK_INT + "'\n"
                + "OS version: '" + Build.VERSION.RELEASE + "'\n"
                + "ABI list: '" + OSUtils.getAbiList() + "'\n"
                + "Manufacturer: '" + Build.MANUFACTURER + "'\n"
                + "Brand: '" + Build.BRAND + "'\n"
                + "Model: '" + Build.MODEL + "'\n"
                + "Build fingerprint: '" + Build.FINGERPRINT + "'\n";
    }


}
