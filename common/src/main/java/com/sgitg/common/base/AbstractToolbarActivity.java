package com.sgitg.common.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.sgitg.common.R;


/**
 * AbstractMipToolbarActivity 具备MIP平台功能 通用标题栏activity基类
 *
 * @author 周麟
 * @created 2018/1/4 10:31
 */
public abstract class AbstractToolbarActivity extends BaseActivity {
    protected AppBarLayout mAppBarLayout;
    protected TextSwitcher mTextSwitcher;
    protected FragmentManager mFragmentManager;

    @Override
    protected void init(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_toolbar;
    }

    @Override
    protected void setUpView() {
        mAppBarLayout = findViewById(R.id.appbar_layout);
        setUpToolBar();
        setTitle(getToolbarTitle());
    }

    private void setUpToolBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTextSwitcher = findViewById(R.id.textSwitcher);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @SuppressWarnings("deprecation")
            @Override
            public View makeView() {
                Context context = AbstractToolbarActivity.this;
                TextView textView = new TextView(context);
                textView.setTextAppearance(context, R.style.WebTitle);
                textView.setTextColor(Color.BLACK);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setSelected(!v.isSelected());
                    }
                });
                return textView;
            }
        });
        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);
    }

    /**
     * 提供标题栏显示的文本
     *
     * @return 标题栏显示的文本
     */
    protected abstract String getToolbarTitle();

    @Override
    protected void setUpData() {
        Fragment fragment = getFragment();
        if (fragment != null&&mFragmentManager!=null) {
            mFragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTextSwitcher.setText(title);
        mTextSwitcher.setSelected(true);
    }

    /**
     * 提供片段
     *
     * @return activity显示内容的片段
     */
    protected abstract Fragment getFragment();
}
