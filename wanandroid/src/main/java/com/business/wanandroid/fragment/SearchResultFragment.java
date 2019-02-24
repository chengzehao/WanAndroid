package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.business.wanandroid.adapter.ArticleAdapter;
import com.business.wanandroid.bean.ArticleBean;
import com.business.wanandroid.viewmodel.SearchResultViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.base.AbstractLazyLoadListFragment;
import com.sgitg.common.common.WebViewActivity;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.viewmodel.LViewModelProviders;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/24/024 20:25
 */
public class SearchResultFragment extends AbstractLazyLoadListFragment<ArticleBean.DatasBean> {
    private String mKey;
    private SearchResultViewModel mViewModel;

    public static SearchResultFragment newInstance(String key) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString("DATA", key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, SearchResultViewModel.class);
        mViewModel.getResult().observe(this, new Observer<RestResult<ArticleBean>>() {
            @Override
            public void onChanged(@Nullable RestResult<ArticleBean> restResult) {
                if (restResult != null) {
                    if (checkHttpResult(restResult)) {
                        onLoadDataSuccess(getCurrentPageIndex() == getInitPageIndex(), restResult.getData().getDatas());
                    } else {
                        onLoadDataError(getCurrentPageIndex() == getInitPageIndex(), "加载失败 " + restResult.getErrorMsg());
                    }
                }
            }
        });
        return mViewModel;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            mKey = getArguments().getString("DATA");
        }
    }

    @Override
    protected boolean isRefreshAndLoadMoreEnabled() {
        return true;
    }

    @Override
    public void loadData(int pageIndex) {
        mViewModel.loadResult(mKey, pageIndex);
    }

    @Override
    protected BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder> createAdapter() {
        ArticleAdapter adapter = new ArticleAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean.DatasBean bean = (ArticleBean.DatasBean) adapter.getData().get(position);
                Bundle b = new Bundle();
                b.putString(WebViewActivity.WEB_URL, bean.getLink());
                b.putString(WebViewActivity.TITLE, bean.getTitle());
                readyGo(WebViewActivity.class, b);
            }
        });
        return adapter;
    }
}
