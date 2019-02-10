package com.business.user.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.business.user.Urls;
import com.business.user.bean.UserBean;
import com.sgitg.common.ConstantValue;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.thread.MainThreadExcute;
import com.sgitg.common.thread.ThreadManager;
import com.yanzhenjie.nohttp.RequestMethod;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class LoginViewModel extends ViewModel {
    private LiveData<RestResult<UserBean>> mData;

    public void login(String username, String password) {
        final MutableLiveData<RestResult<UserBean>> data = new MutableLiveData<>();
        EntityRequest<UserBean> request = new EntityRequest<>(Urls.LOGIN, RequestMethod.POST, UserBean.class);
        request.add("username", username);
        request.add("password", password);
        CallServer.getInstance().request(0, request, new HttpListener<UserBean>() {
            @Override
            public void onResponse(int what, RestResult<UserBean> t) {
                data.setValue(t);
            }
        });
        mData = data;
    }

    public LiveData<RestResult<UserBean>> getLoginResult() {
        return mData;
    }
}
