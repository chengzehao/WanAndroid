package com.sgitg.common.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy {
    private ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;
    private int mMaximumPoolSize;
    private long mKeepAliveTime;

    ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.mCorePoolSize = corePoolSize;
        this.mMaximumPoolSize = maximumPoolSize;
        this.mKeepAliveTime = keepAliveTime;
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    private synchronized void initThreadPoolExecutor() {
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            TimeUnit unit = TimeUnit.MILLISECONDS;
            BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();// 阻塞队列
            ThreadFactory threadFactory = Executors.defaultThreadFactory();
            // 如果出现错误,移除第一个任务,执行加入的任务
            RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();// 如果出现错误，不做处理
            mExecutor = new ThreadPoolExecutor(mCorePoolSize,// 核心线程数 : 2
                    mMaximumPoolSize,// 最大线程数 : 4
                    mKeepAliveTime,// 保持的时间长度
                    unit,// keepAliveTime单位
                    workQueue,// 任务队列
                    threadFactory,// 线程工厂
                    handler);// 错误捕获器
        }
    }
}
