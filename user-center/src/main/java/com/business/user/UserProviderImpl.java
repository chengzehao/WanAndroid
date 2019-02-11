package com.business.user;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sgitg.common.LibApp;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.StringRequest;
import com.sgitg.common.route.UserProvider;
import com.yanzhenjie.nohttp.RequestMethod;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/1/29/029 21:09
 */
@Route(path = "/User/Service")
public class UserProviderImpl implements UserProvider {

    @Override
    public void logoutServer(HttpListener<String> listener) {
        StringRequest request = new StringRequest(Urls.LOGOUT, RequestMethod.POST);
        CallServer.getInstance().request(0, request, listener);
    }

    @Override
    public void clearSp() {
        Authority.clearSp();
    }

    @Override
    public void toLogin() {
        ARouter.getInstance().build("/User/AccountLoginActivity").navigation();
    }

    @Override
    public void logoutApp() {
        LibApp.getInstance().exitApp();
    }


    @Override
    public String getLoginUrl() {
        return Urls.LOGIN;
    }

    @Override
    public String getUserName() {
        return Authority.getUserName();
    }

    @Override
    public int getUserId() {
        return Authority.getUserId();
    }

    @Override
    public String getUserToken() {
        return Authority.getToken();
    }

    @Override
    public void init(Context context) {

    }
}
