package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.SystemBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.RequestMethod;

import java.io.File;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class SystemViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<SystemBean>> mSystemData;

    public SystemViewModel() {
        mSystemData = new MutableLiveData<>();
    }

    public void loadSystem(int pageIndex, String cid) {
        String url = Urls.SYSTEM_LIST + File.separator + pageIndex + File.separator + "json";
        EntityRequest<SystemBean> request = new EntityRequest<>(url, RequestMethod.GET, SystemBean.class);
        request.add("cid", cid);
        CallServer.getInstance().request(0, request, new HttpListener<SystemBean>() {
            @Override
            public void onResponse(int what, RestResult<SystemBean> t) {
                mSystemData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<SystemBean>> getSystem() {
        return mSystemData;
    }

}
