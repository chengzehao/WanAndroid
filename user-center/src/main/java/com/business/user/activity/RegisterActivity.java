package com.business.user.activity;

import android.support.v4.app.Fragment;

import com.business.user.fragment.RegisterFragment;
import com.sgitg.common.base.AbstractToolbarActivity;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/8/008 15:33
 */

public class RegisterActivity extends AbstractToolbarActivity {
    @Override
    protected String getToolbarTitle() {
        return "新用户注册";
    }

    @Override
    protected Fragment getFragment() {
        return new RegisterFragment();
    }
}
