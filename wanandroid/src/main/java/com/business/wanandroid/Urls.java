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

    /**
     * 项目分类
     */
    public static final String PROJECT_CATEGORY = SERVER_URL + File.separator + "project" + File.separator + "tree"+ File.separator + "json";

    /**
     * 项目列表
     */
    public static final String PROJECT_LIST = SERVER_URL + File.separator + "project" + File.separator + "list";

    /**
     * 体系分类
     */
    public static final String SYSTEM_CATEGORY = SERVER_URL + File.separator + "tree" + File.separator + "json";

    /**
     * 体系列表
     */
    public static final String SYSTEM_LIST = SERVER_URL + File.separator + "article" + File.separator + "list";

    /**
     * 导航数据
     */
    public static final String NAV = SERVER_URL + File.separator + "navi" + File.separator + "json";

}
