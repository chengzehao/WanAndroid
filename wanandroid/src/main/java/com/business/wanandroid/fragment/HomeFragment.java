package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.business.wanandroid.R;
import com.business.wanandroid.adapter.ArticleAdapter;
import com.business.wanandroid.bean.HomeArticleBean;
import com.business.wanandroid.bean.HomeBannerBean;
import com.business.wanandroid.viewmodel.HomeViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.ConstantValue;
import com.sgitg.common.base.AbstractLazyLoadListFragment;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.imageloader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

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
    private View mHeaderView;
    private Banner mBanner;

    @Override
    protected void setUpView() {
        super.setUpView();
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mHeaderView = getHeaderView();
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
            mViewModel.loadHomeBannerBeans();
            mViewModel.getHomeBannerBeans().observe(this, new Observer<RestResult<ArrayList<HomeBannerBean>>>() {
                @Override
                public void onChanged(@Nullable RestResult<ArrayList<HomeBannerBean>> arrayListRestResult) {
                    if (arrayListRestResult == null) {
                        return;
                    }
                    if (arrayListRestResult.getErrorCode() == ConstantValue.ST_SUCCESS) {
                        fillBanner(arrayListRestResult.getData());
                    } else {
                        onLoadDataError(true, "加载失败 " + arrayListRestResult.getErrorMsg());
                    }
                }
            });
        } else {
            loadArticle(pageIndex);
        }
    }

    private void fillBanner(ArrayList<HomeBannerBean> data) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (HomeBannerBean datum : data) {
            images.add(datum.getImagePath());
            titles.add(datum.getTitle());
        }
        mBanner.setImages(images);
        mBanner.setBannerTitles(titles);
        mBanner.start();
        loadArticle(getInitPageIndex());
    }

    private void loadArticle(final int pageIndex) {
        mViewModel.loadHomeArticle(pageIndex);
        mViewModel.getHomeArticleBean().observe(this, new Observer<RestResult<HomeArticleBean>>() {
            @Override
            public void onChanged(@Nullable RestResult<HomeArticleBean> homeArticleBeanRestResult) {
                if (homeArticleBeanRestResult == null) {
                    return;
                }

                if (homeArticleBeanRestResult.getErrorCode() == ConstantValue.ST_SUCCESS) {
                    onLoadDataSuccess(pageIndex == getInitPageIndex(), homeArticleBeanRestResult.getData().getDatas());
                } else {
                    onLoadDataError(pageIndex == getInitPageIndex(), "加载失败 " + homeArticleBeanRestResult.getErrorMsg());
                }
            }
        });
    }


    @Override
    protected BaseQuickAdapter<HomeArticleBean.DatasBean, BaseViewHolder> createAdapter() {
        ArticleAdapter adapter = new ArticleAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        return adapter;
    }

    private View getHeaderView() {
        return getLayoutInflater().inflate(R.layout.item_home_banner, (ViewGroup) getmRecyclerView().getParent(), false);
    }
}
