package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.CollectBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.http.StringRequest;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.RequestMethod;

import java.io.File;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class CollectViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<CollectBean>> mCollectData;
    private MutableLiveData<RestResult<String>> mUnCollectResult;

    public CollectViewModel() {
        mCollectData = new MutableLiveData<>();
        mUnCollectResult = new MutableLiveData<>();
    }

    public void loadCollectData(int pageIndex) {
        String url = Urls.COLLECT_LIST + File.separator + pageIndex + File.separator + "json";
        EntityRequest<CollectBean> request = new EntityRequest<>(url, RequestMethod.GET, CollectBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<CollectBean>() {
            @Override
            public void onResponse(int what, RestResult<CollectBean> t) {
                mCollectData.setValue(t);
            }
        });
    }

    public MutableLiveData<RestResult<CollectBean>> getCollectData() {
        return mCollectData;
    }

    public void unCollect(int id,int originId) {
        String url = Urls.UNCOLLECT2 + File.separator + id + File.separator + "json";
        StringRequest request = new StringRequest(url, RequestMethod.POST);
        request.add("originId",originId);
        CallServer.getInstance().request(0, request, new HttpListener<String>() {
            @Override
            public void onResponse(int what, RestResult<String> t) {
                mUnCollectResult.setValue(t);
            }
        });
    }

    public MutableLiveData<RestResult<String>> getUnCollectResult() {
        return mUnCollectResult;
    }

}
