package com.sgitg.common.http;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sgitg.common.route.UserProvider;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * 描述：抽象网络请求基类封装
 *
 * @author 周麟
 * @created 2018/3/11 22:33
 */

public abstract class AbstractRequest<T> extends Request<RestResult<T>> {

    AbstractRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public void onPreExecute() {
    }

    @Override
    public RestResult<T> parseResponse(Headers headers, byte[] body) throws Exception {
        int responseCode = headers.getResponseCode();
        // 响应码等于200，Http层成功。
        if (responseCode == HttpConfig.HTTP_SUCCESS_CODE) {
            if (body == null || body.length == 0) {
                return new RestResult<>(HttpConfig.ST_ERROR, null, "服务异常！请稍后重试.");
            } else {
                String bodyString = StringRequest.parseResponseString(headers, body);
                Logger.i(bodyString);
                try {
                    JSONObject bodyObject = JSON.parseObject(bodyString);
                    if (bodyObject.containsKey(HttpConfig.NET_STATE)) {
                        if (bodyObject.getIntValue(HttpConfig.NET_STATE) == HttpConfig.ST_SUCCESS) {
                            String data = bodyObject.getString(HttpConfig.NET_RESULTS);
                            T result = getResult(data);
                            return new RestResult<>(HttpConfig.ST_SUCCESS, result, "");
                        }else if(bodyObject.getIntValue(HttpConfig.NET_STATE) == HttpConfig.ST_NEED_LOGIN){
                            String error = bodyObject.getString(HttpConfig.NET_MSG);
                            ((UserProvider) ARouter.getInstance().build("/User/Service").navigation()).toLogin();
                            return new RestResult<>(HttpConfig.ST_ERROR, null, error);
                        }else {
                            String error = bodyObject.getString(HttpConfig.NET_MSG);
                            return new RestResult<>(HttpConfig.ST_ERROR, null, error);
                        }
                    } else {
                        return new RestResult<>(HttpConfig.ST_ERROR, null, "服务异常！请稍后重试.");
                    }
                } catch (Exception e) {
                    return new RestResult<>(HttpConfig.ST_ERROR, null, "服务异常！请稍后重试.");
                }
            }
        } else {
            // 其它响应码，如果和服务器没有约定，那就是服务器发生错误了。
            return new RestResult<>(HttpConfig.ST_ERROR, null, "服务异常！请稍后重试.");
        }
    }

    /**
     * 子类需具体实现解析方式
     *
     * @param responseBody 返回体
     * @return 具体对象
     * @throws Exception 异常
     */
    abstract T getResult(String responseBody) throws Exception;
}
