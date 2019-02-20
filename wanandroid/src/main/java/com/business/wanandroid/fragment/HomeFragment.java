package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.business.wanandroid.R;
import com.business.wanandroid.adapter.ArticleAdapter;
import com.business.wanandroid.bean.HomeArticleBean;
import com.business.wanandroid.bean.HomeBannerBean;
import com.business.wanandroid.viewmodel.HomeViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.ConstantValue;
import com.sgitg.common.EventCenter;
import com.sgitg.common.base.AbstractLazyLoadListFragment;
import com.sgitg.common.common.WebViewActivity;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.imageloader.GlideImageLoader;
import com.sgitg.common.route.UserProvider;
import com.sgitg.common.utils.ToastUtils;
import com.sgitg.common.viewmodel.LViewModelProviders;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/11/011 13:42
 */

public class HomeFragment extends AbstractLazyLoadListFragment<HomeArticleBean.DatasBean> {
    private HomeViewModel mViewModel;
    private Banner mBanner;
    private int mCollectPos;

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, HomeViewModel.class);
        mViewModel.getHomeBannerData().observe(this, new Observer<RestResult<ArrayList<HomeBannerBean>>>() {
            @Override
            public void onChanged(@Nullable RestResult<ArrayList<HomeBannerBean>> restResult) {
                if (restResult != null) {
                    if (checkHttpResult(restResult)) {
                        fillBanner(restResult.getData());
                    } else {
                        onLoadDataError(true, "加载失败 " + restResult.getErrorMsg());
                    }
                }
            }
        });

        mViewModel.getHomeArticle().observe(this, new Observer<RestResult<HomeArticleBean>>() {
            @Override
            public void onChanged(@Nullable RestResult<HomeArticleBean> restResult) {
                if (restResult != null) {
                    if (checkHttpResult(restResult)) {
                        onLoadDataSuccess(getCurrentPageIndex() == getInitPageIndex(), restResult.getData().getDatas());
                    } else {
                        onLoadDataError(getCurrentPageIndex() == getInitPageIndex(), "加载失败 " + restResult.getErrorMsg());
                    }
                }
            }
        });
        mViewModel.getCollectResult().observe(this, new Observer<RestResult<String>>() {
            @Override
            public void onChanged(@Nullable RestResult<String> stringRestResult) {
                if (checkHttpResult(stringRestResult)) {
                    getmAdapter().getData().get(mCollectPos).setCollect(true);
                    getmAdapter().notifyDataSetChanged();
                } else {
                    ToastUtils.getInstance().showErrorInfoToast(stringRestResult.getErrorMsg());
                }
            }
        });
        mViewModel.getUnCollectResult().observe(this, new Observer<RestResult<String>>() {
            @Override
            public void onChanged(@Nullable RestResult<String> stringRestResult) {
                if (checkHttpResult(stringRestResult)) {
                    getmAdapter().getData().get(mCollectPos).setCollect(false);
                    getmAdapter().notifyDataSetChanged();
                } else {
                    ToastUtils.getInstance().showErrorInfoToast(stringRestResult.getErrorMsg());
                }
            }
        });
        return mViewModel;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        View mHeaderView = getHeaderView();
        mBanner = mHeaderView.findViewById(R.id.banner);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setDelayTime(5000);
        mBanner.isAutoPlay(false);
        getmAdapter().addHeaderView(mHeaderView);
    }

    @Override
    protected boolean isRefreshAndLoadMoreEnabled() {
        return true;
    }

    @Override
    public void loadData(final int pageIndex) {
        if (pageIndex == getInitPageIndex()) {
            mViewModel.loadHomeBannerData();
        } else {
            mViewModel.loadHomeArticle(pageIndex);
        }
    }

    private void fillBanner(final ArrayList<HomeBannerBean> data) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (HomeBannerBean datum : data) {
            images.add(datum.getImagePath());
            titles.add(datum.getTitle());
        }
        mBanner.setImages(images);
        mBanner.setBannerTitles(titles);
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle b = new Bundle();
                b.putString(WebViewActivity.WEB_URL, data.get(position).getUrl());
                b.putString(WebViewActivity.TITLE, data.get(position).getTitle());
                readyGo(WebViewActivity.class, b);
            }
        });
        mViewModel.loadHomeArticle(getInitPageIndex());
    }

    @Override
    protected BaseQuickAdapter<HomeArticleBean.DatasBean, BaseViewHolder> createAdapter() {
        ArticleAdapter adapter = new ArticleAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeArticleBean.DatasBean bean = (HomeArticleBean.DatasBean) adapter.getData().get(position);
                Bundle b = new Bundle();
                b.putString(WebViewActivity.WEB_URL, bean.getLink());
                b.putString(WebViewActivity.TITLE, bean.getTitle());
                readyGo(WebViewActivity.class, b);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mCollectPos = position;
                HomeArticleBean.DatasBean bean = (HomeArticleBean.DatasBean) adapter.getData().get(position);
                if (bean.isCollect()) {
                    mViewModel.unCollect(String.valueOf(bean.getId()));
                } else {
                    mViewModel.collect(String.valueOf(bean.getId()));
                }
            }
        });
        return adapter;
    }

    private View getHeaderView() {
        return getLayoutInflater().inflate(R.layout.item_home_banner, (ViewGroup) getmRecyclerView().getParent(), false);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == ConstantValue.EVENT_LOGIN_SUCCESS) {
            String collectIds = ((UserProvider) ARouter.getInstance().build("/User/Service").navigation()).getCollectIds();
            ArrayList<Integer> collects = JSON.parseObject(collectIds, new TypeReference<ArrayList<Integer>>() {
            });
            for (Integer collect : collects) {
                for (HomeArticleBean.DatasBean datasBean : getmAdapter().getData()) {
                    if (collect == datasBean.getId()) {
                        datasBean.setCollect(true);
                    }
                }
            }
        } else if (eventCenter.getEventCode() == ConstantValue.EVENT_LOGOUT_SUCCESS) {
            for (HomeArticleBean.DatasBean datasBean : getmAdapter().getData()) {
                datasBean.setCollect(false);
            }
        }
        getmAdapter().notifyDataSetChanged();
    }
}
