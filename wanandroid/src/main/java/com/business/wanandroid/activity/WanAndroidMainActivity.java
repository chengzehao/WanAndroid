package com.business.wanandroid.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.business.wanandroid.Constants;
import com.business.wanandroid.R;
import com.business.wanandroid.fragment.HomeFragment;
import com.business.wanandroid.fragment.MineFragment;
import com.business.wanandroid.fragment.ProjectTabFragment;
import com.business.wanandroid.fragment.SystemCategoryFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.sgitg.common.base.AbstractDoubleClickOutActivity;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/10/010 22:11
 */
@Route(path = "/WanAndroid/MainActivity")
public class WanAndroidMainActivity extends AbstractDoubleClickOutActivity {
    private HomeFragment mHomeFragment;
    private ProjectTabFragment mProjectFragment;
    private SystemCategoryFragment mSystemFragment;
    private MineFragment mMineFragment;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        SpaceNavigationView spaceNavigationView = findViewById(R.id.space);
        spaceNavigationView.addSpaceItem(new SpaceItem("首页", R.mipmap.bottom_menu_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("项目", R.mipmap.bottom_menu_project));
        spaceNavigationView.addSpaceItem(new SpaceItem("体系", R.mipmap.bottom_menu_system));
        spaceNavigationView.addSpaceItem(new SpaceItem("我的", R.mipmap.bottom_menu_mine));
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        spaceNavigationView.showTextOnly();

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                readyGo(SearchActivity.class);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switchFragment(itemIndex);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });

        switchFragment(0);
    }

    private void switchFragment(int tabId) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        if (tabId == 0) {
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.container, mHomeFragment, Constants.HOME_TAG);
            } else {
                fragmentTransaction.show(mHomeFragment);
            }

        } else if (tabId == 1) {
            if (mProjectFragment == null) {
                mProjectFragment = new ProjectTabFragment();
                fragmentTransaction.add(R.id.container, mProjectFragment, Constants.PROJECT_TAG);
            } else {
                fragmentTransaction.show(mProjectFragment);
            }

        } else if (tabId == 2) {
            if (mSystemFragment == null) {
                mSystemFragment = new SystemCategoryFragment();
                fragmentTransaction.add(R.id.container, mSystemFragment, Constants.SYSTEM_TAG);
            } else {
                fragmentTransaction.show(mSystemFragment);
            }

        } else if (tabId == 3) {
            if (mMineFragment == null) {
                mMineFragment = new MineFragment();
                fragmentTransaction.add(R.id.container, mMineFragment, Constants.MINE_TAG);
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
