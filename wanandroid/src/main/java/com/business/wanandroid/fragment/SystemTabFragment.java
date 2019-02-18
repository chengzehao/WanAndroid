package com.business.wanandroid.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.business.wanandroid.R;
import com.business.wanandroid.adapter.SystemTabAdapter;
import com.business.wanandroid.bean.SystemCategoryBean;
import com.sgitg.common.base.BaseFragment;

import me.solidev.statusviewlayout.StatusViewLayout;

/**
 * TaskTabFragment
 *
 * @author 周麟
 * @created 2018/1/4 11:09
 */
public class SystemTabFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StatusViewLayout statusView;
    private SystemCategoryBean mBean;
    private int mPos;

    public static SystemTabFragment newInstance(SystemCategoryBean bean, int pos) {
        SystemTabFragment fragment = new SystemTabFragment();
        Bundle args = new Bundle();
        args.putSerializable("DATA", bean);
        args.putInt("POS", pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            mBean = (SystemCategoryBean) getArguments().getSerializable("DATA");
            mPos = getArguments().getInt("POS");
        }
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
        SystemTabAdapter adapter = new SystemTabAdapter(getChildFragmentManager(), mBean.getChildren());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mBean.getChildren().size() - 1);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(mPos);
        statusView.showContent();
    }
}
