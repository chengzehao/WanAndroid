package com.business.wanandroid.adapter;

import android.os.Bundle;

import com.business.wanandroid.R;
import com.business.wanandroid.activity.SystemTabActivity;
import com.business.wanandroid.bean.SystemCategoryBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.base.BaseActivity;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/7/2 13:33
 */
public class SystemCategoryAdapter extends BaseQuickAdapter<SystemCategoryBean, BaseViewHolder> {
    public SystemCategoryAdapter() {
        super(R.layout.item_system_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SystemCategoryBean bean) {
        helper.setText(R.id.title, bean.getName());
        ((TagContainerLayout) helper.getView(R.id.tcl)).removeAllTags();
        for (SystemCategoryBean.ChildrenBean childrenBean : bean.getChildren()) {
            ((TagContainerLayout) helper.getView(R.id.tcl)).addTag(childrenBean.getName());
        }
        ((TagContainerLayout) helper.getView(R.id.tcl)).setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Bundle b = new Bundle();
                b.putSerializable("DATA", bean);
                b.putInt("POS",position);
                ((BaseActivity) mContext).readyGo(SystemTabActivity.class, b);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }
}
