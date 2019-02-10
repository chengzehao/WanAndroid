package com.sgitg.common.thread;

import android.os.Handler;
import android.os.Looper;

public class MainThreadExcute {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 让task在主线程中执行
     */
    public static void post(Runnable task) {
        mHandler.post(task);
    }

    /**
     * 执行延时任务
     */
    public static void postDelayed(Runnable task, int delayed) {
        mHandler.postDelayed(task, delayed);
    }

    /**
     * 移除任务
     */
    public static void removeCallbacks(Runnable task) {
        mHandler.removeCallbacks(task);
    }

}
