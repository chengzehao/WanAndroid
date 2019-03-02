package com.business.wanandroid.adapter;

import android.view.View;
import android.widget.ImageView;

import com.business.wanandroid.R;
import com.business.wanandroid.bean.ProjectBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.imageloader.ImageLoader;
import com.sgitg.common.utils.DateUtils;
import com.sgitg.common.utils.StringUtils;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/7/2 13:33
 */
public class ProjectAdapter extends BaseQuickAdapter<ProjectBean.DatasBean, BaseViewHolder> {
    public ProjectAdapter() {
        super(R.layout.item_article);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBean.DatasBean bean) {
        helper.setText(R.id.tv_people, bean.getAuthor());
        helper.setText(R.id.tv_time, DateUtils.friendlyTime(DateUtils.parseDateTime(bean.getPublishTime())));
        helper.setText(R.id.tv_title, StringUtils.deal(bean.getTitle()));
        helper.getView(R.id.chapterName).setVisibility(View.GONE);
        if (StringUtils.isNullOrEmpty(bean.getEnvelopePic())) {
            helper.getView(R.id.iv_img).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_img).setVisibility(View.VISIBLE);
            ImageLoader.displayImage((ImageView) helper.getView(R.id.iv_img), bean.getEnvelopePic());
        }
        if (bean.isCollect()) {
            helper.setImageResource(R.id.star, R.mipmap.ic_collection);
        } else {
            helper.setImageResource(R.id.star, R.mipmap.ic_collection_gray);
        }
        helper.addOnClickListener(R.id.star);
    }
}
