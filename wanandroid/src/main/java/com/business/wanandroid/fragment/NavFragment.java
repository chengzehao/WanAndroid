package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
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

public class NavFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {
    private StatusViewLayout mStatusViewLayout;
    private RecyclerView recyclerTypes;
    private RecyclerView recyclerNavigation;
    private NavAdapter navAdapter;
    private NavTypeAdapter typeAdapter;

    private NavViewModel mViewMoel;
    private ArrayList<NavBean> mNavBeans;
    private boolean shouldScroll;
    private int currPos;

    @Override
    protected ViewModel initViewModel() {
        mViewMoel = LViewModelProviders.of(this, NavViewModel.class);
        mViewMoel.getNav().observe(this, new Observer<RestResult<ArrayList<NavBean>>>() {
            @Override
            public void onChanged(@Nullable RestResult<ArrayList<NavBean>> navBeanRestResult) {
                if (checkHttpResult(navBeanRestResult)) {
                    mNavBeans = navBeanRestResult.getData();
                    onLoadNavSuccess(navBeanRestResult.getData());
                } else {
                    mStatusViewLayout.showError(navBeanRestResult.getErrorMsg());
                }
            }
        });
        return mViewMoel;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (shouldScroll) {
                return;
            }
            int firstVisibleItemPosition = getFirstVisibleItemPosition();
            if (currPos != firstVisibleItemPosition) {
                selectItem(firstVisibleItemPosition);
                scrollToPosition(recyclerTypes, currPos, true, false);
            }
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (shouldScroll && newState == RecyclerView.SCROLL_STATE_IDLE) {
                shouldScroll = false;
                scrollToPosition(recyclerNavigation, currPos, false, true);
            }
        }
    };

    private RecyclerView.OnScrollListener onTypeScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (shouldScroll && newState == RecyclerView.SCROLL_STATE_IDLE) {
                shouldScroll = false;
                scrollToPosition(recyclerTypes, currPos, false, true);
            }
        }
    };

    private void onLoadNavSuccess(ArrayList<NavBean> data) {
        mStatusViewLayout.showContent();
        navAdapter.setNewData(data);
        typeAdapter.setNewData(data);
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
        recyclerNavigation.addOnScrollListener(onScrollListener);

        recyclerTypes.setLayoutManager(new LinearLayoutManager(getActivity()));
        typeAdapter = new NavTypeAdapter();
        typeAdapter.setOnItemChildClickListener(this);
        recyclerTypes.setAdapter(typeAdapter);
        recyclerTypes.addOnScrollListener(onTypeScrollListener);

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

    private int getFirstVisibleItemPosition() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerNavigation.getLayoutManager();
        if (layoutManager == null) {
            return 0;
        }
        return layoutManager.findFirstVisibleItemPosition();
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

    private void scrollToPosition(RecyclerView recyclerView, int position, boolean needSmooth, boolean itemInScreenNeedScroll) {
        int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
        int lastItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
        if (position < firstItem) {
            //在屏幕上方，直接滚上去就是顶部
            if (needSmooth) {
                recyclerView.smoothScrollToPosition(position);
            } else {
                recyclerView.scrollToPosition(position);
            }
        } else if (position <= lastItem) {
            if (itemInScreenNeedScroll) {
                //在屏幕中，直接滚动到相应位置的顶部
                int movePosition = position - firstItem;
                if (movePosition >= 0 && movePosition < recyclerView.getChildCount()) {
                    //粘性头部，会占据一定的top空间，所以真是的top位置应该是减去粘性header的高度
                    int top = recyclerView.getChildAt(movePosition).getTop();
                    if (needSmooth) {
                        recyclerView.smoothScrollBy(0, top);
                    } else {
                        recyclerView.scrollBy(0, top);
                    }
                }
            }
        } else {
            //在屏幕下方，需要西安滚动到屏幕内，在校验
            shouldScroll = true;
            if (needSmooth) {
                recyclerView.smoothScrollToPosition(position);
            } else {
                recyclerView.scrollToPosition(position);
            }
            currPos = position;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        selectItem(position);
        int firstVisibleItemPosition = getFirstVisibleItemPosition();
        if (currPos != firstVisibleItemPosition) {
            scrollToPosition(recyclerNavigation, currPos, true, true);
        }
    }
}
