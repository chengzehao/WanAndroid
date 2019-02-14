package com.sgitg.common.http;

import com.sgitg.common.ConstantValue;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/8/8 11:49
 */

public class HttpResponseListener<T> implements OnResponseListener<RestResult<T>> {
    private AbstractRequest<T> mRequest;
    private HttpListener<T> callback;

    HttpResponseListener(AbstractRequest<T> request, HttpListener<T> httpCallback) {
        this.mRequest = request;
        this.callback = httpCallback;
    }

    @Override
    public void onStart(int what) {
    }

    @Override
    public void onFinish(int what) {
    }

    @Override
    public void onSucceed(int what, Response<RestResult<T>> response) {
        if (callback != null && !mRequest.isCanceled()) {
            if (response.get().getErrorCode() == ConstantValue.ST_SUCCESS) {
                callback.onSuccess(what, response.get().getData());
            } else if (response.get().getErrorCode() == ConstantValue.ST_ERROR) {
                callback.onFaill(what, response.get().getErrorMsg());
            }
        }
    }

    @Override
    public void onFailed(int what, Response<RestResult<T>> response) {
        Exception exception = response.getException();
        String errStr;
        if (exception instanceof NetworkError) {
            errStr = "请检查网络";
        } else if (exception instanceof TimeoutError) {
            errStr = "请求超时";
        } else if (exception instanceof UnKnownHostError) {
            errStr = "未发现指定服务器";
        } else if (exception instanceof URLError) {
            errStr = "URL错误";
        } else {
            errStr = "未知错误";
        }
        if (callback != null) {
            callback.onFaill(what, errStr);
        }
    }
}
