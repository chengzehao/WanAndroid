package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.business.wanandroid.R;
import com.business.wanandroid.adapter.ProjectTabAdapter;
import com.business.wanandroid.bean.ProjectCategoryBean;
import com.business.wanandroid.viewmodel.ProjectCategoryViewModel;
import com.sgitg.common.base.BaseFragment;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.viewmodel.LViewModelProviders;

import java.util.ArrayList;

import me.solidev.statusviewlayout.StatusViewLayout;

/**
 * TaskTabFragment
 *
 * @author 周麟
 * @created 2018/1/4 11:09
 */
public class ProjectTabFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StatusViewLayout statusView;
    private ProjectCategoryViewModel mViewModel;

    @Override
    protected ViewModel initViewModel() {
        mViewModel= LViewModelProviders.of(this,ProjectCategoryViewModel.class);
        mViewModel.getProjectCategory().observe(this, new Observer<RestResult<ArrayList<ProjectCategoryBean>>>() {
            @Override
            public void onChanged(@Nullable RestResult<ArrayList<ProjectCategoryBean>> arrayListRestResult) {
                if(checkHttpResult(arrayListRestResult)){
                    ProjectTabAdapter adapter = new ProjectTabAdapter(getChildFragmentManager(), arrayListRestResult.getData());
                    viewPager.setAdapter(adapter);
                    viewPager.setOffscreenPageLimit(arrayListRestResult.getData().size() - 1);
                    tabLayout.setupWithViewPager(viewPager);
                    statusView.showContent();
                }else{
                    statusView.showError(arrayListRestResult.getErrorMsg());
                }
            }
        });
        return mViewModel;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void setUpView() {
        statusView = getContentView().findViewById(R.id.status_view_layout);
        tabLayout = getContentView().findViewById(R.id.tab_layout);
        viewPager = getContentView().findViewById(R.id.view_pager);
        statusView.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpData();
            }
        });
        statusView.showLoading();
    }

    @Override
    protected void setUpData() {
        mViewModel.loadProjectCategory();
    }
}
