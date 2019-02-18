package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.business.wanandroid.R;
import com.business.wanandroid.adapter.NavAdapter;
import com.business.wanandroid.adapter.NavTypeAdapter;
import com.business.wanandroid.bean.NavBean;
import com.business.wanandroid.viewmodel.NavViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sgitg.common.base.BaseFragment;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.viewmodel.LViewModelProviders;

import java.util.ArrayList;

import me.solidev.statusviewlayout.StatusViewLayout;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/17/017 22:17
 */

public class NavFragment extends BaseFragment {
    private StatusViewLayout mStatusViewLayout;
    private RecyclerView recyclerNavigation;
    private RecyclerView recyclerTypes;
    private NavAdapter navAdapter;
    private NavTypeAdapter typeAdapter;

    private NavViewModel mViewMoel;
    private ArrayList<NavBean> mNavBeans;
    private int currPos;

    @Override
    protected ViewModel initViewModel() {
        mViewMoel = LViewModelProviders.of(this, NavViewModel.class);
        mViewMoel.getNav().observe(this, new Observer<RestResult<ArrayList<NavBean>>>() {
            @Override
            public void onChanged(@Nullable RestResult<ArrayList<NavBean>> navBeanRestResult) {
                if (checkHttpResult(navBeanRestResult)) {
                    mStatusViewLayout.showContent();
                    mNavBeans = navBeanRestResult.getData();
                    currPos = 0;
                    mNavBeans.get(currPos).setSelected(true);
                    navAdapter.setNewData(mNavBeans);
                    typeAdapter.setNewData(mNavBeans);
                } else {
                    mStatusViewLayout.showError(navBeanRestResult.getErrorMsg());
                }
            }
        });
        return mViewMoel;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        mStatusViewLayout = getViewById(R.id.status_view);
        recyclerTypes = getViewById(R.id.recycler_types);
        recyclerNavigation = getViewById(R.id.recycler_navigation);

        recyclerNavigation.setLayoutManager(new LinearLayoutManager(getActivity()));
        navAdapter = new NavAdapter();
        recyclerNavigation.setAdapter(navAdapter);
        recyclerNavigation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mNavBeans != null && mNavBeans.size() > 0) {
                    if (mNavBeans.get(getFirstVisibleItemPosition()).getCid() != mNavBeans.get(currPos).getCid()) {
                        for (int i = 0; i < mNavBeans.size(); i++) {
                            if (mNavBeans.get(getFirstVisibleItemPosition()).getCid() == mNavBeans.get(i).getCid()) {
                                selectItem(i);
                                recyclerTypes.smoothScrollToPosition(i);
                                break;
                            }
                        }
                    }
                }
            }
        });

        recyclerTypes.setLayoutManager(new LinearLayoutManager(getActivity()));
        typeAdapter = new NavTypeAdapter();
        recyclerTypes.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectItem(position);
                int firstVisibleItemPosition = getFirstVisibleItemPosition();
                if (currPos != firstVisibleItemPosition) {
                    recyclerNavigation.smoothScrollToPosition(currPos);
                }
            }
        });
        mStatusViewLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpData();
            }
        });
    }

    @Override
    protected void setUpData() {
        mStatusViewLayout.showLoading();
        mViewMoel.loadNav();
    }

    private void selectItem(int position) {
        if (position < 0 || mNavBeans.size() < position) {
            return;
        }
        mNavBeans.get(currPos).setSelected(false);
        currPos = position;
        mNavBeans.get(currPos).setSelected(true);
        typeAdapter.notifyDataSetChanged();
    }

    private int getFirstVisibleItemPosition() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerNavigation.getLayoutManager();
        if (layoutManager == null) {
            return 0;
        }
        return layoutManager.findFirstVisibleItemPosition();
    }
}
