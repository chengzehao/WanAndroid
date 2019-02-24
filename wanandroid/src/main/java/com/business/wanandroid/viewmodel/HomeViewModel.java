package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.ArticleBean;
import com.business.wanandroid.bean.HomeBannerBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityListRequest;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.http.StringRequest;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.RequestMethod;

import java.io.File;
import java.util.ArrayList;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class HomeViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<ArticleBean>> mHomeArticleData;
    private MutableLiveData<RestResult<ArrayList<HomeBannerBean>>> mHomeBannerData;
    private MutableLiveData<RestResult<String>> mCollectResult;
    private MutableLiveData<RestResult<String>> mUnCollectResult;

    public HomeViewModel() {
        mHomeArticleData = new MutableLiveData<>();
        mHomeBannerData = new MutableLiveData<>();
        mCollectResult = new MutableLiveData<>();
        mUnCollectResult = new MutableLiveData<>();
    }

    public void loadHomeArticle(int pageIndex) {
        String url = Urls.HOME_ARTICLE + File.separator + pageIndex + File.separator + "json";
        EntityRequest<ArticleBean> request = new EntityRequest<>(url, RequestMethod.GET, ArticleBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<ArticleBean>() {
            @Override
            public void onResponse(int what, RestResult<ArticleBean> t) {
                mHomeArticleData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<ArticleBean>> getHomeArticle() {
        return mHomeArticleData;
    }

    public void loadHomeBannerData() {
        EntityListRequest<HomeBannerBean> request = new EntityListRequest<>(Urls.HOME_BANNER, RequestMethod.GET, HomeBannerBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<ArrayList<HomeBannerBean>>() {
            @Override
            public void onResponse(int what, RestResult<ArrayList<HomeBannerBean>> t) {
                mHomeBannerData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<ArrayList<HomeBannerBean>>> getHomeBannerData() {
        return mHomeBannerData;
    }

    public void collect(String id) {
        startLoading("提交中");
        String url = Urls.COLLECT + File.separator + id + File.separator + "json";
        StringRequest request = new StringRequest(url, RequestMethod.POST);
        CallServer.getInstance().request(0, request, new HttpListener<String>() {
            @Override
            public void onResponse(int what, RestResult<String> t) {
                dismissLoading();
                mCollectResult.setValue(t);
            }
        });
    }

    public MutableLiveData<RestResult<String>> getCollectResult() {
        return mCollectResult;
    }

    public void unCollect(String id) {
        startLoading("提交中");
        String url = Urls.UNCOLLECT + File.separator + id + File.separator + "json";
        StringRequest request = new StringRequest(url, RequestMethod.POST);
        CallServer.getInstance().request(0, request, new HttpListener<String>() {
            @Override
            public void onResponse(int what, RestResult<String> t) {
                dismissLoading();
                mUnCollectResult.setValue(t);
            }
        });
    }

    public MutableLiveData<RestResult<String>> getUnCollectResult() {
        return mUnCollectResult;
    }
}
