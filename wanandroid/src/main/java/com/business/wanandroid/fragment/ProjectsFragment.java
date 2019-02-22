package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.business.wanandroid.adapter.ProjectAdapter;
import com.business.wanandroid.bean.ProjectBean;
import com.business.wanandroid.viewmodel.ProjectViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.event.EventCenter;
import com.sgitg.common.event.EventCode;
import com.sgitg.common.base.AbstractLazyLoadListFragment;
import com.sgitg.common.common.WebViewActivity;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.route.UserProvider;
import com.sgitg.common.utils.ToastUtils;
import com.sgitg.common.viewmodel.LViewModelProviders;
import com.yanzhenjie.nohttp.Logger;

import java.util.List;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/17/017 11:17
 */

public class ProjectsFragment extends AbstractLazyLoadListFragment<ProjectBean.DatasBean> {
    private ProjectViewModel mViewModel;
    private String mCid;
    private int mCollectPos;

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

        mViewModel.getCollectResult().observe(this, new Observer<RestResult<String>>() {
            @Override
            public void onChanged(@Nullable RestResult<String> stringRestResult) {
                if (checkHttpResult(stringRestResult)) {
                    getmAdapter().getData().get(mCollectPos).setCollect(true);
                    addCollectIdToSp(getmAdapter().getData().get(mCollectPos).getId());
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
                    removeCollectIdFromSp(getmAdapter().getData().get(mCollectPos).getId());
                    getmAdapter().notifyDataSetChanged();
                } else {
                    ToastUtils.getInstance().showErrorInfoToast(stringRestResult.getErrorMsg());
                }
            }
        });
        return mViewModel;
    }

    private void removeCollectIdFromSp(int id) {
        UserProvider userProvider = ((UserProvider) ARouter.getInstance().build("/User/Service").navigation());
        userProvider.removeCollectId(id);
        Logger.i("removeCollectIdFromSp:" + userProvider.getCollectIdList());
    }

    private void addCollectIdToSp(int id) {
        UserProvider userProvider = ((UserProvider) ARouter.getInstance().build("/User/Service").navigation());
        userProvider.addCollectId(id);
        Logger.i(userProvider.getCollectIdList());
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

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mCollectPos = position;
                ProjectBean.DatasBean bean = (ProjectBean.DatasBean) adapter.getData().get(position);
                if (bean.isCollect()) {
                    mViewModel.unCollect(String.valueOf(bean.getId()));
                } else {
                    mViewModel.collect(String.valueOf(bean.getId()));
                }
            }
        });
        return adapter;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == EventCode.EVENT_LOGIN_SUCCESS || eventCenter.getEventCode() == EventCode.EVENT_REFRESH_COLLECT) {
            UserProvider userProvider = ((UserProvider) ARouter.getInstance().build("/User/Service").navigation());
            List<Integer> collectList = userProvider.getCollectIdList();
            if (collectList == null) {
                setAllCollectFalse();
            } else {
                setAllCollectFalse();
                for (Integer collect : collectList) {
                    for (ProjectBean.DatasBean datasBean : getmAdapter().getData()) {
                        if (collect == datasBean.getId()) {
                            datasBean.setCollect(true);
                        }
                    }
                }
            }
        } else if (eventCenter.getEventCode() == EventCode.EVENT_LOGOUT_SUCCESS) {
            setAllCollectFalse();
        }
        getmAdapter().notifyDataSetChanged();
    }

    private void setAllCollectFalse() {
        for (ProjectBean.DatasBean datasBean : getmAdapter().getData()) {
            datasBean.setCollect(false);
        }
    }
}
