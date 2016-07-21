package com.push.vain.pushframework.core;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.huawei.android.pushagent.PushManager;
import com.push.vain.pushframework.platform.PushPlatform;
import com.xiaomi.mipush.sdk.MiPushClient;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by vain on 16/7/6.
 */
public class PushService {

    private static final String TAG = "PushService";


    private OnPushListener mPushListener;

    /**
     * 饿汗单例模式。static保证类加载的时候初始化一次，线程安全。
     * 缺点：如果构造完不用的话，导致的资源浪费。
     * 这里的话，PushService轻量级，且项目初始化的时候就会用到。缺点的影响可以接受。
     */
    private static PushService INSTANCE = new PushService();

    private PushService() {
    }

    public static PushService getInstance() {
        return INSTANCE;
    }


    public void setOnPushListener(OnPushListener pushListener) {
        this.mPushListener = pushListener;
    }

    public OnPushListener getOnPushListener() {
        return mPushListener;
    }

    public void registerJpush(Context context) {
        try {
            JPushInterface.init(context);
            JPushInterface.setLatestNotificationNumber(context, 5);
            JPushInterface.resumePush(context);
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (Exception e) {
            Log.e(TAG, "Jpush crash due to some unexpected error.", e);
        }

    }

    public void registerMiPush(Context context, String appId, String appKey, String userCount) {
        MiPushClient.registerPush(context, appId, appKey);
        MiPushClient.setUserAccount(context, userCount, null);
    }

    public void registerHwPush(Context context) {
        PushManager.enableReceiveNotifyMsg(context, false);
        PushManager.requestToken(context);
    }


    public void unRegisterPushService(Context context, PushPlatform platform) {
        switch (platform) {
            case MI_PUSH:
                MiPushClient.unregisterPush(context);
                break;
            case HW_PUSH:
                //ignore
                break;
            case JPUSH:
                JPushInterface.stopPush(context);
                break;
        }
    }

    public void stopPush(Context context, PushPlatform pushPlatform) {
        switch (pushPlatform) {
            case JPUSH:
                JPushInterface.stopPush(context);
                break;
            case HW_PUSH:
                PushManager.enableReceiveNotifyMsg(context, false);
                break;
        }
    }

    public void resumePush(Context context, PushPlatform pushPlatform) {
        switch (pushPlatform) {
            case JPUSH:
                JPushInterface.resumePush(context);
                break;
            case HW_PUSH:
                PushManager.enableReceiveNotifyMsg(context, true);
                break;
        }
    }


    public void clearNotification(Context context, PushPlatform pushPlatform) {
        switch (pushPlatform) {
            case MI_PUSH:
                MiPushClient.clearNotification(context);
                break;
            case JPUSH:
                JPushInterface.clearAllNotifications(context);
                break;
            case HW_PUSH:
                NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nm.cancelAll();
                break;
        }
    }

}
