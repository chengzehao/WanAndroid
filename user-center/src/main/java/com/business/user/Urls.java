package com.business.user;


import com.sgitg.common.NdkMethods;

import java.io.File;

/**
 * Urls 网络路径类
 *
 * @author 周麟
 * @date 2018/1/4 10:25
 */
public class Urls {

    /**
     * 主路径
     */
    //private static final String SERVER_URL = NdkMethods.getServerPath();
    private static final String SERVER_URL = "http://www.wanandroid.com";

    /**
     * 登录验证
     */
    public static final String LOGIN = SERVER_URL + File.separator + "user" + File.separator + "login";

    /**
     * 注销
     */
    public static final String LOGOUT = SERVER_URL + File.separator + "user" + File.separator + "logout" + File.separator + "json" ;

    /**
     * 注册
     */
    public static final String REGISTER = SERVER_URL + File.separator + "user" + File.separator + "register";

}
