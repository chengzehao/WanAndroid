package com.business.wanandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.business.wanandroid.fragment.SearchResultFragment;
import com.sgitg.common.base.AbstractToolbarActivity;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/24/024 20:23
 */
public class SearchResultActivity extends AbstractToolbarActivity {
    private String mKey;

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        mKey = extras.getString("DATA");
    }

    @Override
    protected String getToolbarTitle() {
        return mKey;
    }

    @Override
    protected Fragment getFragment() {
        return SearchResultFragment.newInstance(mKey);
    }
}
