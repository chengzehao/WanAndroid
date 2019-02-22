package com.sgitg.common.event;

/**
 * HttpConfig 常量
 *
 * @author 周麟
 * @created 2018/1/4 10:09
 */
public class EventCode {
    private static final int EVENT_BEGIN = 0X100;
    public static final int EVENT_LOGIN_SUCCESS = EVENT_BEGIN + 10;
    public static final int EVENT_LOGOUT_SUCCESS = EVENT_BEGIN + 20;
    public static final int EVENT_REFRESH_COLLECT = EVENT_BEGIN + 30;
    public static final int EVENT_REGISTER_SUCCESS = EVENT_BEGIN + 40;
}
