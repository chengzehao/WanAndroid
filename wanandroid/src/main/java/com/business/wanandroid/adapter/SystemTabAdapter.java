package com.business.wanandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.business.wanandroid.bean.SystemCategoryBean;
import com.business.wanandroid.fragment.SystemListFragment;

import java.util.List;

/**
 * TaskTabAdapter
 *
 * @author 周麟
 * @date 2018/1/4 11:06
 */
public class SystemTabAdapter extends FragmentStatePagerAdapter {
    private List<SystemCategoryBean.ChildrenBean> list;

    public SystemTabAdapter(FragmentManager fm, List<SystemCategoryBean.ChildrenBean> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return SystemListFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
