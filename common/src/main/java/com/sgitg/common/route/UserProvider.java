package com.sgitg.common.route;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.sgitg.common.http.HttpListener;

import java.util.List;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/1/29/029 21:07
 */

public interface UserProvider extends IProvider {
    void logoutServer(HttpListener<String> listener);

    void clearSp();

    void toLogin();

    void logoutApp();

    String getLoginUrl();

    String getUserName();

    List<Integer> getCollectIdList();

    void addCollectId(int id);

    void removeCollectId(int id);

}
