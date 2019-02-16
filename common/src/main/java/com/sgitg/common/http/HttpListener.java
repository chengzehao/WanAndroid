package com.sgitg.common.http;

/**
 * 描述：接受回调结果
 *
 * @author 周麟
 * @created 2018/1/14/014 1:15
 */
public interface HttpListener<T> {

    /**
     * 服务器返回
     *
     * @param what 请求标识
     * @param t    返回数据
     */
    void onResponse(int what, RestResult<T> t);

}
