package com.business.wanandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.business.wanandroid.bean.ProjectCategoryBean;
import com.business.wanandroid.fragment.ProjectsFragment;

import java.util.List;

/**
 * TaskTabAdapter
 *
 * @author 周麟
 * @date 2018/1/4 11:06
 */
public class ProjectTabAdapter extends FragmentStatePagerAdapter {
    private List<ProjectCategoryBean> list;

    public ProjectTabAdapter(FragmentManager fm, List<ProjectCategoryBean> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ProjectsFragment.newInstance(String.valueOf(list.get(position).getId()));
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
