package com.sgitg.common.http;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/1/15 8:42
 */

public class CallServer {
    private static volatile CallServer sInstance;

    public static CallServer getInstance() {
        if (sInstance == null) {
            synchronized (CallServer.class) {
                if (sInstance == null) {
                    sInstance = new CallServer();
                }
            }
        }
        return sInstance;
    }

    private RequestQueue mRequestQueue;

    private CallServer() {
        mRequestQueue = NoHttp.newRequestQueue(5);
    }

    public void request(int what, Request<String> request, OnResponseListener<String> listener) {
        mRequestQueue.add(what, request, listener);
    }

    public <T> void request(int what, AbstractRequest<T> request, HttpListener<T> callback) {
        mRequestQueue.add(what, request, new HttpResponseListener<>(request, callback));
    }
}
