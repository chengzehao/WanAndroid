package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.HotWordBean;
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

public class SearchViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<ArrayList<HotWordBean>>> mHotData;

    public SearchViewModel() {
        mHotData = new MutableLiveData<>();
    }

    public void loadHot() {
        EntityListRequest<HotWordBean> request = new EntityListRequest<>(Urls.HOT, RequestMethod.GET, HotWordBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<ArrayList<HotWordBean>>() {
            @Override
            public void onResponse(int what, RestResult<ArrayList<HotWordBean>> t) {
                mHotData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<ArrayList<HotWordBean>>> getHot() {
        return mHotData;
    }

}
