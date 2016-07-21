package com.push.vain.pushframework.core;

import com.push.vain.pushframework.platform.PushPlatform;

/**
 * Created by vain on 16/7/7.
 */
public interface OnPushListener {
    void onNotificationArrived(PushPlatform platform, String jsonContent);

    void onNotificationClicked(PushPlatform platform, String jsonContent);

    /**
     * 华为推送的Token回调
     */
    void onToken();
}
