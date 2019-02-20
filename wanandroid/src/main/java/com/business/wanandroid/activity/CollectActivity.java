package com.business.wanandroid.activity;

import android.support.v4.app.Fragment;

import com.business.wanandroid.fragment.CollectFragment;
import com.sgitg.common.base.AbstractToolbarActivity;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/20/020 19:31
 */

public class CollectActivity extends AbstractToolbarActivity {
    @Override
    protected String getToolbarTitle() {
        return "收藏列表";
    }

    @Override
    protected Fragment getFragment() {
        return new CollectFragment();
    }
}
