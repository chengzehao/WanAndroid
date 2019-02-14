package com.sgitg.common.base;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.ConstantValue;
import com.sgitg.common.R;

import java.util.List;

import me.solidev.statusviewlayout.StatusViewLayout;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/7/2/002 21:46
 */

public abstract class AbstractLazyLoadListFragment<T> extends AbstractLazyLoadFragment {
    private StatusViewLayout mStatusViewLayout;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private int mPageSize;
    private int mCurrentPageIndex;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_base_recyclerview;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        mPageSize = getPageSize();
        mCurrentPageIndex = getInitPageIndex();
        mAdapter = createAdapter();
        if (mAdapter == null) {
            throw new IllegalAccessError("请设置适配器！");
        }
        mStatusViewLayout = getContentView().findViewById(R.id.status_view);
        mRecyclerView = getContentView().findViewById(R.id.rv_list);
        mSwipeRefreshLayout = getContentView().findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setDistanceToTriggerSync(200);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        boolean mIsRefreshAndLoadMoreEnabled = isRefreshAndLoadMoreEnabled();
        mSwipeRefreshLayout.setEnabled(mIsRefreshAndLoadMoreEnabled);
        mRecyclerView.setAdapter(mAdapter);

        mStatusViewLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusViewLayout.showLoading();
                loadData(getInitPageIndex());
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(mIsRefreshAndLoadMoreEnabled ? new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPageIndex = getInitPageIndex();
                //防止下拉刷新的时候还可以上拉加载
                mAdapter.setEnableLoadMore(false);
                loadData(mCurrentPageIndex);
            }
        } : null);
        mAdapter.setOnLoadMoreListener(mIsRefreshAndLoadMoreEnabled ? new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData(mCurrentPageIndex);
            }
        } : null, mRecyclerView);
    }

    protected abstract boolean isRefreshAndLoadMoreEnabled();

    @Override
    protected void lazyLoad() {
        mStatusViewLayout.showLoading();
        mCurrentPageIndex = getInitPageIndex();
        //防止下拉刷新的时候还可以上拉加载
        mAdapter.setEnableLoadMore(false);
        loadData(mCurrentPageIndex);
    }


    /**
     * 加载数据
     *
     * @param pageIndex 页码
     */
    public abstract void loadData(int pageIndex);


    protected int getInitPageIndex() {
        return 0;
    }

    protected int getPageSize() {
        return ConstantValue.PAGENUM;
    }

    /**
     * 提供适配器
     *
     * @return 多条目类型适配器
     */
    protected abstract BaseQuickAdapter<T, BaseViewHolder> createAdapter();

    protected void onLoadDataSuccess(boolean isRefresh, List<T> data) {
        mStatusViewLayout.showContent();
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size == 0) {
                mStatusViewLayout.showError(getEmptyMsg());
            } else {
                setData(true, data);
            }
            mAdapter.setEnableLoadMore(true);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            setData(false, data);
        }
    }

    protected void onLoadDataError(boolean isRefresh, String err) {
        if (isRefresh) {
            mStatusViewLayout.showError(err);
            mAdapter.setEnableLoadMore(true);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mAdapter.loadMoreFail();
        }
    }

    private void setData(boolean isRefresh, List<T> data) {
        mCurrentPageIndex++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < mPageSize) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public BaseQuickAdapter<T, BaseViewHolder> getmAdapter() {
        return mAdapter;
    }

    @NonNull
    protected String getEmptyMsg() {
        return "无数据";
    }

    public void showLoading() {
        mStatusViewLayout.showLoading();
    }

    public int getCurrentPageIndex() {
        return mCurrentPageIndex;
    }
}
