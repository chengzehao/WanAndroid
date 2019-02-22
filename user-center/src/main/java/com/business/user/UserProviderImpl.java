package com.business.user;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.business.user.bean.UserBean;
import com.sgitg.common.LibApp;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.StringRequest;
import com.sgitg.common.route.UserProvider;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

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
        StringRequest request = new StringRequest(Urls.LOGOUT, RequestMethod.GET);
        CallServer.getInstance().request(0, request, listener);
    }

    @Override
    public void clearSp() {
        LitePal.deleteAll(UserBean.class);
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

    private UserBean getUser() {
        List<UserBean> userBeans = LitePal.findAll(UserBean.class);
        if (userBeans != null && userBeans.size() > 0) {
            return userBeans.get(0);
        }
        return null;
    }

    @Override
    public String getUserName() {
        if (getUser() != null) {
            return getUser().getUsername();
        }
        return "";
    }

    @Override
    public List<Integer> getCollectIdList() {
        if (getUser() != null) {
            return getUser().getCollectIds();
        }
        return null;
    }

    @Override
    public void addCollectId(int id) {
        List<Integer> cs = getCollectIdList();
        if (cs == null) {
            cs = new ArrayList<>();
        }
        cs.add(id);
        UserBean u = getUser();
        if (u != null) {
            u.setCollectIds(cs);
            u.update(u.getId());
        }
    }

    @Override
    public void removeCollectId(int id) {
        List<Integer> cs = getCollectIdList();
        UserBean u = getUser();
        if (cs == null) {
            return;
        }
        List<Integer> temp = new ArrayList<>();
        for (Integer c : cs) {
            if (c != id) {
                temp.add(c);
            }
        }
        if (u != null) {
            if (temp.size() == 0) {
                u.setToDefault("collectIds");
                u.update(u.getId());
            } else {
                u.setCollectIds(temp);
                u.update(u.getId());
            }
        }
    }


    @Override
    public void init(Context context) {

    }
}
