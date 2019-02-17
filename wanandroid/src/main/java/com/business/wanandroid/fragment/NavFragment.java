package com.business.wanandroid.fragment;

import android.support.v7.widget.RecyclerView;

import com.business.wanandroid.R;
import com.sgitg.common.base.BaseFragment;

import me.solidev.statusviewlayout.StatusViewLayout;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/17/017 22:17
 */

public class NavFragment extends BaseFragment{
    private StatusViewLayout mStatusViewLayout;
    private RecyclerView recyclerTypes;
    private RecyclerView recyclerNavigation;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        mStatusViewLayout=getViewById(R.id.status_view);
        recyclerTypes=getViewById(R.id.recycler_types);
        recyclerNavigation=getViewById(R.id.recycler_navigation);

    }
}
