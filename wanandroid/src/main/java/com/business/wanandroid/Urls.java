package com.business.wanandroid;


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
    private static final String SERVER_URL = NdkMethods.getServerPath();


    /**
     * 首页文章
     */
    public static final String HOME_ARTICLE = SERVER_URL + File.separator + "article" + File.separator + "list";

    /**
     * 首页轮播
     */
    public static final String HOME_BANNER = SERVER_URL + File.separator + "banner" + File.separator + "json";


}
