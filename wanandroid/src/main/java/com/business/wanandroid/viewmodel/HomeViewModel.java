package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.HomeArticleBean;
import com.business.wanandroid.bean.HomeBannerBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityListRequest;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
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
    private MutableLiveData<HomeArticleBean> mHomeArticleData;
    private MutableLiveData<String> mLoadHomeArticleError;
    private MutableLiveData<ArrayList<HomeBannerBean>> mHomeBannerData;
    private MutableLiveData<String> mLoadHomeBannerError;

    public HomeViewModel() {
        mHomeArticleData = new MutableLiveData<>();
        mLoadHomeArticleError = new MutableLiveData<>();
        mHomeBannerData = new MutableLiveData<>();
        mLoadHomeBannerError = new MutableLiveData<>();
    }

    public void loadHomeArticle(int pageIndex) {
        String url = Urls.HOME_ARTICLE + File.separator + pageIndex + File.separator + "json";
        EntityRequest<HomeArticleBean> request = new EntityRequest<>(url, RequestMethod.GET, HomeArticleBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<HomeArticleBean>() {
            @Override
            public void onSuccess(int what, HomeArticleBean homeArticleBean) {
                mHomeArticleData.setValue(homeArticleBean);
            }

            @Override
            public void onFaill(int what, String error) {
                mLoadHomeArticleError.setValue(error);
            }
        });
    }

    public LiveData<HomeArticleBean> getHomeArticle() {
        return mHomeArticleData;
    }

    public MutableLiveData<String> getLoadHomeArticleError() {
        return mLoadHomeArticleError;
    }

    public void loadHomeBannerData() {
        EntityListRequest<HomeBannerBean> request = new EntityListRequest<>(Urls.HOME_BANNER, RequestMethod.GET, HomeBannerBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<ArrayList<HomeBannerBean>>() {
            @Override
            public void onSuccess(int what, ArrayList<HomeBannerBean> homeBannerBeans) {
                mHomeBannerData.setValue(homeBannerBeans);
            }

            @Override
            public void onFaill(int what, String error) {
                mLoadHomeBannerError.setValue(error);
            }
        });
    }

    public LiveData<ArrayList<HomeBannerBean>> getHomeBannerData() {
        return mHomeBannerData;
    }

    public MutableLiveData<String> getLoadHomeBannerError() {
        return mLoadHomeBannerError;
    }
}
