package com.business.wanandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.business.wanandroid.bean.SystemCategoryBean;
import com.business.wanandroid.fragment.SystemTabFragment;
import com.sgitg.common.base.AbstractToolbarActivity;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/17/017 17:03
 */

public class SystemTabActivity extends AbstractToolbarActivity {
    private SystemCategoryBean mBean;
    private int mPos;

    @Override
    protected void getBundleExtras(Bundle extras) {
        mBean = (SystemCategoryBean) extras.getSerializable("DATA");
        mPos = extras.getInt("POS");
    }

    @Override
    protected String getToolbarTitle() {
        return mBean.getName();
    }

    @Override
    protected Fragment getFragment() {
        return SystemTabFragment.newInstance(mBean, mPos);
    }
}
