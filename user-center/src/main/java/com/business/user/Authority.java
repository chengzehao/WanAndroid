package com.business.user;

import com.business.user.bean.UserBean;
import com.sgitg.common.LibApp;

/**
 * Authority 用户信息维护工具
 *
 * @author 周麟
 * @created 2018/1/4 9:57
 */
//@Route(path = "/author/util")
public class Authority {

    public static void login(UserBean userBean) {
        setUserId(userBean.getId());
        setUserName(userBean.getUsername());
        setToken(userBean.getToken());
    }

    public static void clearSp() {
        setUserId(0);
        setUserName("");
        setToken("");
    }

    private static void setUserId(int userId) {
        LibApp.getInstance().getSharedPreferences().edit().putInt("UserId", userId).apply();
    }

    public static int getUserId() {
        return LibApp.getInstance().getSharedPreferences().getInt("UserId", 0);
    }

    private static void setUserName(String name) {
        LibApp.getInstance().getSharedPreferences().edit().putString("UserName", name).apply();
    }

    public static String getUserName() {
        return LibApp.getInstance().getSharedPreferences().getString("UserName", "");
    }

    private static void setToken(String token) {
        LibApp.getInstance().getSharedPreferences().edit().putString("Token", token).apply();
    }

    public static String getToken() {
        return LibApp.getInstance().getSharedPreferences().getString("Token", "");
    }
}
