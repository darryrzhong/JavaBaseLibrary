package com.example.corelibrary.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

/**
 * 类名称: NotificationUtils.java
 * <p>
 * 类描述: 创建前台服务通知工具类
 * <p>
 *
 * @author darryrzhong
 * @since 2019/10/18 10:48
 */
public class NotificationUtils
{
    private static final String NOTIFICATION_CHANNEL_NAME =
        "BackgroundLocation";
    
    /**
     * 创建一个通知
     * 
     * @param context 上下文
     * 
     */
    public static Notification buildNotification(Context context, int logoID,
        String name)
    {
        Notification.Builder builder = null;
        Notification notification = null;
        NotificationManager notificationManager = null;
        // Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            
            notificationManager = (NotificationManager)context
                .getSystemService(Context.NOTIFICATION_SERVICE);
            String channelId = context.getPackageName();
            NotificationChannel notificationChannel =
                new NotificationChannel(channelId, NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            // 是否在桌面icon右上角展示小圆点
            notificationChannel.enableLights(true);
            // 小圆点颜色
            notificationChannel.setLightColor(Color.BLUE);
            // 是否在久按桌面图标时显示此渠道的通知
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new Notification.Builder(context.getApplicationContext(),
                channelId);
        }
        else
        {
            builder = new Notification.Builder(context.getApplicationContext());
        }
        builder.setSmallIcon(logoID)
            .setContentTitle(name)
            .setContentText("正在后台运行")
            .setWhen(System.currentTimeMillis());
        notification = builder.build();
        return notification;
    }
}
