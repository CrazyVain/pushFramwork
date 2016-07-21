package com.push.vain.pushframework.platform;

/**
 * Created by vain on 16/7/6.
 */
public enum PushPlatform {
    JPUSH(1),
    MI_PUSH(2),
    HW_PUSH(3);


    int provider;

    PushPlatform(int provider) {
        this.provider = provider;
    }
}
