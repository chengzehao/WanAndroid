package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.NavBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityListRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.RequestMethod;

import java.util.ArrayList;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class NavViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<ArrayList<NavBean>>> mNavData;

    public NavViewModel() {
        mNavData = new MutableLiveData<>();
    }

    public void loadNav() {
        String url = Urls.NAV;
        EntityListRequest<NavBean> request = new EntityListRequest<>(url, RequestMethod.GET, NavBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<ArrayList<NavBean>>() {
            @Override
            public void onResponse(int what, RestResult<ArrayList<NavBean>> t) {
                mNavData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<ArrayList<NavBean>>> getNav() {
        return mNavData;
    }

}
