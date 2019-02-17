package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.business.wanandroid.adapter.SystemCategoryAdapter;
import com.business.wanandroid.bean.SystemCategoryBean;
import com.business.wanandroid.viewmodel.SystemCategoryViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.base.AbstractLazyLoadListFragment;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.viewmodel.LViewModelProviders;

import java.util.ArrayList;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/17/017 16:42
 */

public class SystemCategoryFragment extends AbstractLazyLoadListFragment<SystemCategoryBean> {
    private SystemCategoryViewModel mViewModel;

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, SystemCategoryViewModel.class);
        mViewModel.getCategory().observe(this, new Observer<RestResult<ArrayList<SystemCategoryBean>>>() {
            @Override
            public void onChanged(@Nullable RestResult<ArrayList<SystemCategoryBean>> arrayListRestResult) {
                if (checkHttpResult(arrayListRestResult)) {
                    onLoadDataSuccess(true, arrayListRestResult.getData());
                } else {
                    onLoadDataError(true, arrayListRestResult.getErrorMsg());
                }
            }
        });
        return mViewModel;
    }

    @Override
    protected boolean isRefreshAndLoadMoreEnabled() {
        return false;
    }

    @Override
    public void loadData(int pageIndex) {
        mViewModel.loadCategory();
    }

    @Override
    protected BaseQuickAdapter<SystemCategoryBean, BaseViewHolder> createAdapter() {
        return new SystemCategoryAdapter();
    }
}
