package com.sgitg.common.http;

/**
 * 描述：接受回调结果
 *
 * @author 周麟
 * @created 2018/1/14/014 1:15
 */
public interface HttpListener<T> {

    /**
     * 业务成功回调
     *
     * @param what 请求标识
     * @param t    返回数据
     */
    void onSuccess(int what, T t);

    /**
     * 业务失败回调
     *
     * @param what 请求标识
     * @param error 失败消息
     */
    void onFaill(int what, String error);

}
