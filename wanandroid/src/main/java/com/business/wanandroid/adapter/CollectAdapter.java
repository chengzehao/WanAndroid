package com.business.wanandroid.adapter;

import com.business.wanandroid.R;
import com.business.wanandroid.bean.CollectBean;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.utils.DateUtils;
import com.sgitg.common.utils.StringUtils;

import java.util.List;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/7/2 13:33
 */
public class CollectAdapter extends BaseItemDraggableAdapter<CollectBean.DatasBean, BaseViewHolder> {
    public CollectAdapter(List<CollectBean.DatasBean> data) {
        super(R.layout.item_collect, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectBean.DatasBean bean) {
        helper.setText(R.id.tv_people, bean.getAuthor());
        helper.setText(R.id.tv_time, DateUtils.friendlyTime(DateUtils.parseDateTime(bean.getPublishTime())));
        helper.setText(R.id.tv_title, StringUtils.deal(bean.getTitle()));
        helper.setText(R.id.chapterName, bean.getChapterName());
        helper.setText(R.id.nice_time, bean.getNiceDate());
    }
}
