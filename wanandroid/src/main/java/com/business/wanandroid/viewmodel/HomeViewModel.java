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
    private MutableLiveData<HomeArticleBean> mHomeArticleBean;
    private MutableLiveData<ArrayList<HomeBannerBean>> mHomeBannerBeans;

    public HomeViewModel() {
        mHomeArticleBean = new MutableLiveData<>();
        mHomeBannerBeans = new MutableLiveData<>();
    }

    public void loadHomeArticle(int pageIndex) {
        String url = Urls.HOME_ARTICLE + File.separator + pageIndex + File.separator + "json";
        EntityRequest<HomeArticleBean> request = new EntityRequest<>(url, RequestMethod.GET, HomeArticleBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<HomeArticleBean>() {
            @Override
            public void onSuccess(int what, HomeArticleBean homeArticleBean) {
                mHomeArticleBean.setValue(homeArticleBean);
            }

            @Override
            public void onFaill(int what, String error) {

            }
        });
    }

    public LiveData<HomeArticleBean> getHomeArticleBean() {
        return mHomeArticleBean;
    }

    public void loadHomeBannerBeans() {
        EntityListRequest<HomeBannerBean> request = new EntityListRequest<>(Urls.HOME_BANNER, RequestMethod.GET, HomeBannerBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<ArrayList<HomeBannerBean>>() {
            @Override
            public void onSuccess(int what, ArrayList<HomeBannerBean> homeBannerBeans) {
                mHomeBannerBeans.setValue(homeBannerBeans);
            }

            @Override
            public void onFaill(int what, String error) {

            }
        });
    }

    public LiveData<ArrayList<HomeBannerBean>> getHomeBannerBeans() {
        return mHomeBannerBeans;
    }
}
