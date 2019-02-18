package com.business.wanandroid.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.business.wanandroid.R;
import com.business.wanandroid.bean.NavBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/18 14:07
 */
public class NavTypeAdapter extends BaseQuickAdapter<NavBean, BaseViewHolder> {

    public NavTypeAdapter() {
        super(R.layout.item_nav_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavBean item) {
        TextView tvItem = helper.getView(R.id.title);
        tvItem.setText(item.getName());
        if (item.isSelected()) {
            tvItem.setTextColor(ContextCompat.getColor(mContext, R.color.md_white_1000));
            tvItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            tvItem.setTextColor(ContextCompat.getColor(mContext, R.color.md_black_1000));
            tvItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.md_white_1000));
        }
    }
}
