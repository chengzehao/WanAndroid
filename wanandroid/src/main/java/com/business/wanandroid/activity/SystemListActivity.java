package com.business.wanandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.business.wanandroid.bean.SystemCategoryBean;
import com.business.wanandroid.fragment.SystemListFragment;
import com.sgitg.common.base.AbstractToolbarActivity;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/17/017 17:03
 */

public class SystemListActivity extends AbstractToolbarActivity {
    private SystemCategoryBean.ChildrenBean mBean;

    @Override
    protected void getBundleExtras(Bundle extras) {
        mBean = (SystemCategoryBean.ChildrenBean) extras.getSerializable("DATA");
    }

    @Override
    protected String getToolbarTitle() {
        return mBean.getName();
    }

    @Override
    protected Fragment getFragment() {
        return SystemListFragment.newInstance(mBean);
    }
}
