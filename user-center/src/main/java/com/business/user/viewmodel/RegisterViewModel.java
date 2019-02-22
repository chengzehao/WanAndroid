package com.business.user.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.user.Urls;
import com.business.user.bean.UserBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.http.StringRequest;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.RequestMethod;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class RegisterViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<UserBean>> mUserData;

    public RegisterViewModel() {
        mUserData = new MutableLiveData<>();
    }


    public void register(String username, String password) {
        startLoading("注册中,请稍候..");
        EntityRequest<UserBean> request = new EntityRequest<>(Urls.REGISTER, RequestMethod.POST, UserBean.class);
        request.add("username", username);
        request.add("password", password);
        request.add("repassword", password);
        CallServer.getInstance().request(0, request, new HttpListener<UserBean>() {
            @Override
            public void onResponse(int what, RestResult<UserBean> t) {
                dismissLoading();
                mUserData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<UserBean>> getResult() {
        return mUserData;
    }
}
