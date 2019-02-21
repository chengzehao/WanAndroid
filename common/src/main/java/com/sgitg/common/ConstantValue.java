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
     * 网络请求业务成功
     */
    public final static int ST_SUCCESS = 0;
    /**
     * 网络请求业务失败
     */
    public final static int ST_ERROR = 1;
    /**
     * 网络请求业务需要登录
     */
    public final static int ST_NEED_LOGIN = -1001;

    public final static String NET_STATE = "errorCode";
    public final static String NET_MSG = "errorMsg";
    public final static String NET_RESULTS = "data";
    public final static int HTTP_SUCCESS_CODE = 200;

    private static final int EVENT_BEGIN = 0X100;
    public static final int EVENT_LOGIN_SUCCESS = EVENT_BEGIN + 10;
    public static final int EVENT_LOGOUT_SUCCESS = EVENT_BEGIN + 20;
    public static final int EVENT_REFRESH_COLLECT = EVENT_BEGIN + 30;
}
