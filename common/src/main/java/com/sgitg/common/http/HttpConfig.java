package com.sgitg.common.http;

/**
 * HttpConfig 常量
 *
 * @author 周麟
 * @created 2018/1/4 10:09
 */
public class HttpConfig {
    /**
     * 一页默认显示条数
     */
    public final static int PAGENUM = 20;

    /**
     * 网络请求业务成功
     */
    public final static int ST_SUCCESS = 0;
    /**
     * 网络请求业务失败
     */
    final static int ST_ERROR = 1;
    /**
     * 网络请求业务需要登录
     */
    final static int ST_NEED_LOGIN = -1001;

    final static String NET_STATE = "errorCode";
    final static String NET_MSG = "errorMsg";
    final static String NET_RESULTS = "data";
    final static int HTTP_SUCCESS_CODE = 200;

}
