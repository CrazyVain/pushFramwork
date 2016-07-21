package com.push.vain.pushframework.receiver;

import android.content.Context;
import android.util.Log;

import com.push.vain.pushframework.core.PushService;
import com.push.vain.pushframework.platform.PushPlatform;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * Created by vain on 16/7/6.
 */
public class MiPushReceiver extends PushMessageReceiver {
    private static final String TAG = "MiPushReceiver";

    /**
     * 客户端向服务器发送命令消息后的响应回调
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        Log.d(TAG,
                "onCommandResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);

        switch (command) {
            case MiPushClient.COMMAND_REGISTER: {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    Log.d(TAG, "register success!  " + cmdArg1);
                } else {
                    Log.d(TAG, "register fail ：" + message.getReason());
                }
                break;
            }
        }

    }


    /**
     * 注册后的响应回调
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        Log.d(TAG, "onReceiveRegisterResult : " + miPushCommandMessage.toString());
    }

    /**
     * 透传消息
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        Log.d(TAG, "onReceivePassThroughMessage");
    }

    /**
     * 接收通知栏消息
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        Log.d(TAG, "onNotificationMessageArrived");
        String jsonData = miPushMessage.getContent();
        if (PushService.getInstance().getOnPushListener() != null) {
            PushService.getInstance().getOnPushListener().onNotificationArrived(PushPlatform.MI_PUSH, jsonData);
        }
    }

    /**
     * 通知栏消息点击处理
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        Log.d(TAG, "onNotificationMessageClicked");
        String jsonData = miPushMessage.getContent();
        if (PushService.getInstance().getOnPushListener() != null) {
            PushService.getInstance().getOnPushListener().onNotificationClicked(PushPlatform.MI_PUSH, jsonData);
        }
    }

}
