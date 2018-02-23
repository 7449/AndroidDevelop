package com.wifi.core;


import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class CustomHandlerThread extends Thread {

    /**
     * 处理线程消息的handler
     */
    private Handler mHandler;

    /**
     * 线程优先级别
     */
    private int mPriority;
    /**
     * 当前线程的Looper
     */
    private Looper mLooper;

    private volatile boolean mIsReady = false;
    /**
     * 处理消息的Handler
     */
    private Class<? extends Handler> mHandlerClass;

    public CustomHandlerThread(String threadName, Class<? extends Handler> handlerClass) {
        super(threadName);
        mPriority = Process.THREAD_PRIORITY_DEFAULT;
        mHandlerClass = handlerClass;
    }

    public Handler getLooperHandler() {
        return mHandler;
    }

    /**
     * 确保mHandler被创建
     */
    public void isReady() {
        synchronized (this) {
            while (!mIsReady) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }

        Process.setThreadPriority(mPriority);
        try {
            Constructor<? extends Handler> handler_creater = mHandlerClass.getConstructor(Looper.class);
            mHandler = handler_creater.newInstance(mLooper);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        onLooperPrepared();
        Looper.loop();
    }

    private void onLooperPrepared() {
        synchronized (this) {
            mIsReady = true;
            notifyAll();
        }
    }

    private Looper getLooper() {
        if (!isAlive())
            return null;
        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return mLooper;
    }

    public void quit() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
        }
    }

    @TargetApi(18)
    public boolean quitSafely() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quitSafely();
            return true;
        }
        return false;
    }
}
