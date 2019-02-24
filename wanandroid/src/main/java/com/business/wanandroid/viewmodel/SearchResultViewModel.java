package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.ArticleBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.http.StringRequest;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;

import java.io.File;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class SearchResultViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<ArticleBean>> mResultData;

    public SearchResultViewModel() {
        mResultData = new MutableLiveData<>();
    }

    public void loadResult(String key, int pageIndex) {
        String url = Urls.SEARCH + File.separator + pageIndex + File.separator + "json";

        EntityRequest<ArticleBean> request = new EntityRequest<>(url, RequestMethod.POST, ArticleBean.class);
        request.add("k", key);
        CallServer.getInstance().request(0, request, new HttpListener<ArticleBean>() {
            @Override
            public void onResponse(int what, RestResult<ArticleBean> t) {
                mResultData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<ArticleBean>> getResult() {
        return mResultData;
    }
}
