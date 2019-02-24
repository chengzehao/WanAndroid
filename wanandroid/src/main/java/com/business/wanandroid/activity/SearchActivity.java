package com.business.wanandroid.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.business.wanandroid.R;
import com.business.wanandroid.bean.HotWordBean;
import com.business.wanandroid.bean.SearchHistoryBean;
import com.business.wanandroid.viewmodel.SearchViewModel;
import com.sgitg.common.base.BaseActivity;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.thread.MainThreadExcute;
import com.sgitg.common.utils.StringUtils;
import com.sgitg.common.utils.ToastUtils;
import com.sgitg.common.viewmodel.LViewModelProviders;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/24/024 11:55
 */
public class SearchActivity extends BaseActivity {
    private EditText mKeywordText;
    private TagContainerLayout mHotTagContainerLayout;
    private TagContainerLayout mHistoryTagContainerLayout;
    private SearchViewModel mViewModel;
    private List<SearchHistoryBean> mLocalHistory;

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, SearchViewModel.class);
        mViewModel.getHot().observe(this, new Observer<RestResult<ArrayList<HotWordBean>>>() {
            @Override
            public void onChanged(@Nullable RestResult<ArrayList<HotWordBean>> arrayListRestResult) {
                if (checkHttpResult(arrayListRestResult)) {
                    handleHotWords(arrayListRestResult.getData());
                } else {
                    ToastUtils.getInstance().showErrorInfoToast(arrayListRestResult.getErrorMsg());
                }
            }
        });
        return mViewModel;
    }

    private void handleHotWords(ArrayList<HotWordBean> data) {
        mHotTagContainerLayout.removeAllTags();
        for (HotWordBean datum : data) {
            mHotTagContainerLayout.addTag(datum.getName());
        }

        mHotTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                toSearch(text);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    protected void setUpView() {
        mKeywordText = findViewById(R.id.et_keyword);
        mHotTagContainerLayout = findViewById(R.id.tcl_hot);
        mHistoryTagContainerLayout = findViewById(R.id.tcl_history);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() == null) {
            return;
        }
        //决定左上角的图标是否可以点击
        getSupportActionBar().setHomeButtonEnabled(true);
        //决定左上角图标的右侧是否有向左的小箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mKeywordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    toSearch(mKeywordText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void toSearch(String key) {
        if (StringUtils.isNullOrEmpty(key)) {
            ToastUtils.getInstance().showErrorInfoToast("关键字不能空着！");
            return;
        }
        localPersistenceHistory(key);
        loadHistory();
        Bundle b = new Bundle();
        b.putString("DATA", key);
        readyGo(SearchResultActivity.class, b);
    }

    private void localPersistenceHistory(String key) {
        if (mLocalHistory != null && mLocalHistory.size() > 0) {
            for (SearchHistoryBean historyBean : mLocalHistory) {
                if (key.equals(historyBean.getWord())) {
                    historyBean.delete();
                }
            }
            if (mLocalHistory.size() > 30) {
                LitePal.findFirst(SearchHistoryBean.class).delete();
            }
        }
        SearchHistoryBean historyBean = new SearchHistoryBean();
        historyBean.setWord(key);
        historyBean.save();
    }

    @Override
    protected void setUpData() {
        mViewModel.loadHot();
        loadHistory();
    }

    private void loadHistory() {
        mLocalHistory = LitePal.findAll(SearchHistoryBean.class);
        Collections.reverse(mLocalHistory);
        MainThreadExcute.post(new Runnable() {
            @Override
            public void run() {
                mHistoryTagContainerLayout.removeAllTags();
                if (mLocalHistory == null || mLocalHistory.size() == 0) {
                    return;
                }
                for (SearchHistoryBean historyBean : mLocalHistory) {
                    mHistoryTagContainerLayout.addTag(historyBean.getWord());
                }
                mHistoryTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int position, String text) {
                        toSearch(text);
                    }

                    @Override
                    public void onTagLongClick(int position, String text) {

                    }

                    @Override
                    public void onTagCrossClick(int position) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            toSearch(mKeywordText.getText().toString());
        }
        return super.onOptionsItemSelected(item);
    }
}
