package com.business.wanandroid.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.business.wanandroid.R;
import com.business.wanandroid.bean.NavBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/18 14:26
 */
public class NavAdapter extends BaseQuickAdapter<NavBean, BaseViewHolder> {

    public NavAdapter() {
        super(R.layout.item_nav);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavBean item) {
        helper.setText(R.id.title, item.getName());
        ((TagContainerLayout) helper.getView(R.id.tcl)).removeAllTags();
        for (NavBean.ArticlesBean articlesBean : item.getArticles()) {
            ((TagContainerLayout) helper.getView(R.id.tcl)).addTag(articlesBean.getTitle());
        }
    }
}
