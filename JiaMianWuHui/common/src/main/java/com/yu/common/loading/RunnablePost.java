package com.yu.common.loading;

import android.os.Handler;
import android.os.Looper;

public class RunnablePost {

    public static void post(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        RunnablePost.postOn(runnable, Looper.getMainLooper());
    }


    public static void postOn(Runnable runnable, Looper looper) {
        if (runnable == null) {
            return;
        }
        if (looper == null || Looper.myLooper() == looper) {
            runnable.run();
        } else {
            new Handler(looper).post(runnable);
        }
    }
}

