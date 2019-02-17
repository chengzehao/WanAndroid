package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.business.wanandroid.adapter.ProjectAdapter;
import com.business.wanandroid.bean.ProjectBean;
import com.business.wanandroid.viewmodel.ProjectViewModel;
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
 * @date 2019/2/17/017 11:17
 */

public class ProjectsFragment extends AbstractLazyLoadListFragment<ProjectBean.DatasBean> {
    private ProjectViewModel mViewModel;
    private String mCid;

    public static ProjectsFragment newInstance(String cid) {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle args = new Bundle();
        args.putString("DATA", cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, ProjectViewModel.class);
        mViewModel.getProject().observe(this, new Observer<RestResult<ProjectBean>>() {
            @Override
            public void onChanged(@Nullable RestResult<ProjectBean> projectBeanRestResult) {
                if (checkHttpResult(projectBeanRestResult)) {
                    onLoadDataSuccess(getCurrentPageIndex() == getInitPageIndex(), projectBeanRestResult.getData().getDatas());
                } else {
                    onLoadDataError(getCurrentPageIndex() == getInitPageIndex(), projectBeanRestResult.getErrorMsg());
                }
            }
        });
        return mViewModel;
    }

    @Override
    protected void init() {
        super.init();
        if (getArguments() != null) {
            mCid = getArguments().getString("DATA");
        }
    }

    @Override
    protected boolean isRefreshAndLoadMoreEnabled() {
        return true;
    }

    @Override
    public void loadData(int pageIndex) {
        mViewModel.loadProject(pageIndex, mCid);
    }

    @Override
    protected BaseQuickAdapter<ProjectBean.DatasBean, BaseViewHolder> createAdapter() {
        ProjectAdapter adapter = new ProjectAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProjectBean.DatasBean bean = (ProjectBean.DatasBean) adapter.getData().get(position);
                Bundle b = new Bundle();
                b.putString(WebViewActivity.WEB_URL, bean.getLink());
                b.putString(WebViewActivity.TITLE, bean.getTitle());
                readyGo(WebViewActivity.class, b);
            }
        });
        return adapter;
    }
}
