package com.sgitg.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sgitg.common.ConstantValue;
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
        if (responseCode == ConstantValue.HTTP_SUCCESS_CODE) {
            if (body == null || body.length == 0) {
                return new RestResult<>(1, null, "服务异常！请稍后重试.");
            } else {
                String bodyString = StringRequest.parseResponseString(headers, body);
                Logger.i(bodyString);
                try {
                    JSONObject bodyObject = JSON.parseObject(bodyString);
                    if (bodyObject.containsKey(ConstantValue.NET_STATE)) {
                        if (bodyObject.getIntValue(ConstantValue.NET_STATE) == ConstantValue.ST_SUCCESS) {
                            String data = bodyObject.getString(ConstantValue.NET_RESULTS);
                            T result = getResult(data);
                            return new RestResult<>(ConstantValue.ST_SUCCESS, result, "");
                        } else {
                            String error = bodyObject.getString(ConstantValue.NET_MSG);
                            return new RestResult<>(ConstantValue.ST_ERROR, null, error);
                        }
                    } else {
                        return new RestResult<>(ConstantValue.ST_ERROR, null, "服务异常！请稍后重试.");
                    }
                } catch (Exception e) {
                    String error = "服务异常！请稍后重试.";
                    return new RestResult<>(ConstantValue.ST_ERROR, null, error);
                }
            }
        } else {
            // 其它响应码，如果和服务器没有约定，那就是服务器发生错误了。
            String error = "服务异常！请稍后重试.";
            return new RestResult<>(ConstantValue.ST_ERROR, null, error);
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
