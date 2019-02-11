package com.sgitg.common.common;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.just.agentweb.AgentWeb;
import com.sgitg.common.R;
import com.sgitg.common.base.BaseActivity;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/7/3/003 22:23
 */

public class WebViewActivity extends BaseActivity {
    public static final String TITLE = "webViewTitle";
    public static final String WEB_URL = "webViewUrl";
    private String mUrl;
    private String mTitle;

    private FrameLayout mContent;
    private AgentWeb mAgentWeb;

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        mUrl = extras.getString(WEB_URL);
        mTitle = extras.getString(TITLE);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_webview;
    }

    @Override
    protected void setUpView() {
        mContent = findViewById(R.id.container);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextSwitcher mTextSwitcher = findViewById(R.id.textSwitcher);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @SuppressWarnings("deprecation")
            @Override
            public View makeView() {
                Context context = WebViewActivity.this;
                TextView textView = new TextView(context);
                textView.setTextAppearance(context, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setTextColor(Color.BLACK);
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
        mTextSwitcher.setText(mTitle);
        mTextSwitcher.setSelected(true);
    }

    @Override
    protected void setUpData() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContent, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(Color.parseColor("#33A1C9"))
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
