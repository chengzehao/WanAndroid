package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.business.wanandroid.adapter.SystemAdapter;
import com.business.wanandroid.bean.SystemBean;
import com.business.wanandroid.bean.SystemCategoryBean;
import com.business.wanandroid.viewmodel.SystemViewModel;
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
 * @date 2019/2/17/017 17:07
 */

public class SystemListFragment extends AbstractLazyLoadListFragment<SystemBean.DatasBean> {
    private SystemCategoryBean.ChildrenBean mBean;
    private SystemViewModel mViewModel;

    public static SystemListFragment newInstance(SystemCategoryBean.ChildrenBean bean) {
        SystemListFragment fragment = new SystemListFragment();
        Bundle args = new Bundle();
        args.putSerializable("DATA", bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, SystemViewModel.class);
        mViewModel.getSystem().observe(this, new Observer<RestResult<SystemBean>>() {
            @Override
            public void onChanged(@Nullable RestResult<SystemBean> systemBeanRestResult) {
                if (checkHttpResult(systemBeanRestResult)) {
                    onLoadDataSuccess(getCurrentPageIndex() == getInitPageIndex(), systemBeanRestResult.getData().getDatas());
                } else {
                    onLoadDataError(getCurrentPageIndex() == getInitPageIndex(), systemBeanRestResult.getErrorMsg());
                }
            }
        });
        return mViewModel;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            mBean = (SystemCategoryBean.ChildrenBean) getArguments().getSerializable("DATA");
        }
    }

    @Override
    protected boolean isRefreshAndLoadMoreEnabled() {
        return true;
    }

    @Override
    public void loadData(int pageIndex) {
        mViewModel.loadSystem(pageIndex, String.valueOf(mBean.getId()));
    }

    @Override
    protected BaseQuickAdapter<SystemBean.DatasBean, BaseViewHolder> createAdapter() {
        SystemAdapter adapter = new SystemAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SystemBean.DatasBean bean = (SystemBean.DatasBean) adapter.getData().get(position);
                Bundle b = new Bundle();
                b.putString(WebViewActivity.WEB_URL, bean.getLink());
                b.putString(WebViewActivity.TITLE, bean.getTitle());
                readyGo(WebViewActivity.class, b);
            }
        });
        return adapter;
    }
}
