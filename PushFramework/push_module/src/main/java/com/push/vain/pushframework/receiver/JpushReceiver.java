package com.push.vain.pushframework.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.push.vain.pushframework.core.PushService;
import com.push.vain.pushframework.platform.PushPlatform;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PushReceiver;

/**
 * Created by vain on 16/7/6.
 */
public class JpushReceiver extends PushReceiver {

    private static final String TAG = "JpushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        if (null == bundle) return;
        String dataJson = bundle.getString(JPushInterface.EXTRA_EXTRA);


        // 通知消息到达 push framework
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equalsIgnoreCase(action)) {
            if (PushService.getInstance().getOnPushListener() != null) {
                PushService.getInstance().getOnPushListener().onNotificationArrived(PushPlatform.JPUSH, dataJson);
            }
        }

        // 通知消息点击  push framework
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equalsIgnoreCase(action)) {
            if (PushService.getInstance().getOnPushListener() != null) {
                PushService.getInstance().getOnPushListener().onNotificationClicked(PushPlatform.JPUSH, dataJson);
            }
        }

        Log.d(TAG, "jPush action : " + action);
    }
}
