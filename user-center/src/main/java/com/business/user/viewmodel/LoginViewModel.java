package com.business.user.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.user.Urls;
import com.business.user.bean.UserBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.RequestMethod;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class LoginViewModel extends BaseViewModel {
    private MutableLiveData<UserBean> mUserData;

    public LoginViewModel() {
        mUserData = new MutableLiveData<>();
    }

    public void login(String username, String password) {
        startLoading("登录中,请稍候..");
        EntityRequest<UserBean> request = new EntityRequest<>(Urls.LOGIN, RequestMethod.POST, UserBean.class);
        request.add("username", username);
        request.add("password", password);
        CallServer.getInstance().request(0, request, new HttpListener<UserBean>() {
            @Override
            public void onSuccess(int what, UserBean userBean) {
                dismissLoading();
                showSuccessToast("登录成功！");
                mUserData.setValue(userBean);
            }

            @Override
            public void onFaill(int what, String error) {
                dismissLoading();
                showFaillToast(error);
            }
        });
    }

    public LiveData<UserBean> getLoginResult() {
        return mUserData;
    }
}
