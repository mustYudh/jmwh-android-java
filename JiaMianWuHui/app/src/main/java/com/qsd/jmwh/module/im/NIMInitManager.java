package com.qsd.jmwh.module.im;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.BroadcastMessage;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.IMMessageFilter;

/**
 * Created by hzchenkang on 2017/9/26.
 * 用于初始化时，注册全局的广播、云信观察者等等云信相关业务
 */

public class NIMInitManager {

    private static final String TAG = "NIMInitManager";

    private NIMInitManager() {
    }

    private static class InstanceHolder {
        static NIMInitManager receivers = new NIMInitManager();
    }

    public static NIMInitManager getInstance() {
        return InstanceHolder.receivers;
    }

    public void init(boolean register) {
        // 注册通知消息过滤器
        registerIMMessageFilter();

        // 注册语言变化监听广播
        registerLocaleReceiver(register);

        // 注册全局云信sdk 观察者
        registerGlobalObservers(register);

    }

    private void registerGlobalObservers(boolean register) {
        // 注册云信全员广播
        registerBroadcastMessages(register);
    }

    private void registerLocaleReceiver(boolean register) {
        if (register) {
            IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        } else {
        }
    }

    private BroadcastReceiver localeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
            }
        }
    };


    /**
     * 通知消息过滤器（如果过滤则该消息不存储不上报）
     */
    private void registerIMMessageFilter() {
        NIMClient.getService(MsgService.class).registerIMMessageFilter(new IMMessageFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {

                return false;
            }
        });
    }

    /**
     * 注册云信全服广播接收器
     *
     * @param register
     */
    private void registerBroadcastMessages(boolean register) {
        NIMClient.getService(
            MsgServiceObserve.class).observeBroadcastMessage(new Observer<BroadcastMessage>() {
            @Override
            public void onEvent(BroadcastMessage broadcastMessage) {
            }
        }, register);
    }

}
