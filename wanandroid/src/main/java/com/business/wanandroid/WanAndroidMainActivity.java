package com.business.wanandroid;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.sgitg.common.base.AbstractDoubleClickOutActivity;
import com.sgitg.common.common.TestFragment;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/10/010 22:11
 */
@Route(path = "/WanAndroid/MainActivity")
public class WanAndroidMainActivity extends AbstractDoubleClickOutActivity {
    private TestFragment mHomeFragment;
    private TestFragment mProjectFragment;
    private TestFragment mSystemFragment;
    private TestFragment mMineFragment;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        BottomBar mBottomBar = findViewById(R.id.bottomBar);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switchFragment(tabId);
            }
        });
    }

    private void switchFragment(int tabId) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        if (tabId == R.id.tab_home) {
            if (mHomeFragment == null) {
                mHomeFragment = TestFragment.newInstance("Home");
                fragmentTransaction.add(R.id.content, mHomeFragment, Constants.HOME_TAG);
            } else {
                fragmentTransaction.show(mHomeFragment);
            }

        } else if (tabId == R.id.tab_project) {
            if (mProjectFragment == null) {
                mProjectFragment = TestFragment.newInstance("Project");
                fragmentTransaction.add(R.id.content, mProjectFragment, Constants.PROJECT_TAG);
            } else {
                fragmentTransaction.show(mProjectFragment);
            }

        } else if (tabId == R.id.tab_system) {
            if (mSystemFragment == null) {
                mSystemFragment = TestFragment.newInstance("System");
                fragmentTransaction.add(R.id.content, mSystemFragment, Constants.SYSTEM_TAG);
            } else {
                fragmentTransaction.show(mSystemFragment);
            }

        } else if (tabId == R.id.tab_mine) {
            if (mMineFragment == null) {
                mMineFragment = TestFragment.newInstance("Mine");
                fragmentTransaction.add(R.id.content, mMineFragment, Constants.MINE_TAG);
            } else {
                fragmentTransaction.show(mMineFragment);
            }

        }
        fragmentTransaction.commit();
    }


    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }

        if (mProjectFragment != null) {
            fragmentTransaction.hide(mProjectFragment);
        }
        if (mSystemFragment != null) {
            fragmentTransaction.hide(mSystemFragment);
        }

        if (mMineFragment != null) {
            fragmentTransaction.hide(mMineFragment);
        }
    }
}
