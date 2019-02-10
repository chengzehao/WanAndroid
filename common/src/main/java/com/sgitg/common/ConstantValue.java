package com.sgitg.common;

/**
 * ConstantValue 常量
 *
 * @author 周麟
 * @created 2018/1/4 10:09
 */
public class ConstantValue {
    /**
     * 一页默认显示条数
     */
    public final static int PAGENUM = 20;

    /**
     * 网络请求成功
     */
    public final static int ST_SUCCESS = 0;
    /**
     * 网络请求失败
     */
    public final static int ST_ERROR = 1;

    public final static String NET_STATE = "errorCode";
    public final static String NET_MSG = "errorMsg";
    public final static String NET_RESULTS = "data";
    public final static int HTTP_SUCCESS_CODE = 200;
}
