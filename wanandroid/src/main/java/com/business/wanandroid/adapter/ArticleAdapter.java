package com.business.wanandroid.adapter;

import android.view.View;
import android.widget.ImageView;

import com.business.wanandroid.R;
import com.business.wanandroid.bean.HomeArticleBean;
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
public class ArticleAdapter extends BaseQuickAdapter<HomeArticleBean.DatasBean, BaseViewHolder> {
    public ArticleAdapter() {
        super(R.layout.item_article);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeArticleBean.DatasBean bean) {
        helper.setText(R.id.tv_people, bean.getAuthor());
        helper.setText(R.id.tv_time, DateUtils.friendlyTime(DateUtils.parseDateTime(bean.getPublishTime())));
        helper.setText(R.id.tv_title, bean.getTitle());
        helper.setText(R.id.chapterName, bean.getChapterName());
        if (StringUtils.isNullOrEmpty(bean.getEnvelopePic())) {
            helper.getView(R.id.iv_img).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_img).setVisibility(View.VISIBLE);
            ImageLoader.displayImage((ImageView) helper.getView(R.id.iv_img), bean.getEnvelopePic());
        }
    }
}
