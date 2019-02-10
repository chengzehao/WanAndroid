package com.sgitg.common.thread;

public class ThreadManager {

    private static volatile ThreadPoolProxy mLongPool;

    private ThreadManager(){}

    /**
     * 获得耗时操作的线程池
     */
    public static ThreadPoolProxy getLongPool() {
        if (mLongPool == null) {
            synchronized (ThreadManager.class) {
                if (mLongPool == null) {
                    mLongPool = new ThreadPoolProxy(5, 5, 0);
                }
            }
        }
        return mLongPool;
    }
}
