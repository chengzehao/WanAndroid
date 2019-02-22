package com.business.wanandroid.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.business.wanandroid.adapter.CollectAdapter;
import com.business.wanandroid.bean.CollectBean;
import com.business.wanandroid.viewmodel.CollectViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.sgitg.common.event.EventCenter;
import com.sgitg.common.event.EventCode;
import com.sgitg.common.base.AbstractLazyLoadListFragment;
import com.sgitg.common.common.WebViewActivity;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.route.UserProvider;
import com.sgitg.common.utils.ToastUtils;
import com.sgitg.common.viewmodel.LViewModelProviders;
import com.yanzhenjie.nohttp.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/20/020 19:32
 */

public class CollectFragment extends AbstractLazyLoadListFragment<CollectBean.DatasBean> {
    private CollectViewModel viewModel;
    private int cancelCollectId;

    @Override
    protected ViewModel initViewModel() {
        viewModel = LViewModelProviders.of(this, CollectViewModel.class);
        viewModel.getCollectData().observe(this, new Observer<RestResult<CollectBean>>() {
            @Override
            public void onChanged(@Nullable RestResult<CollectBean> stringRestResult) {
                if (checkHttpResult(stringRestResult)) {
                    onLoadDataSuccess(getCurrentPageIndex() == getInitPageIndex(), stringRestResult.getData().getDatas());
                } else {
                    onLoadDataError(getCurrentPageIndex() == getInitPageIndex(), stringRestResult.getErrorMsg());
                }
            }
        });
        viewModel.getUnCollectResult().observe(this, new Observer<RestResult<String>>() {
            @Override
            public void onChanged(@Nullable RestResult<String> stringRestResult) {
                if (checkHttpResult(stringRestResult)) {
                    ToastUtils.getInstance().showSuccessInfoToast("已取消收藏");
                    removeCollectIdFromSp(cancelCollectId);
                    EventBus.getDefault().post(new EventCenter<>(EventCode.EVENT_REFRESH_COLLECT));
                } else {
                    ToastUtils.getInstance().showErrorInfoToast(stringRestResult.getErrorMsg());
                }
            }
        });
        return viewModel;
    }

    private void removeCollectIdFromSp(int id) {
        UserProvider userProvider = ((UserProvider) ARouter.getInstance().build("/User/Service").navigation());
        userProvider.removeCollectId(id);
        Logger.i(userProvider.getCollectIdList());
    }

    @Override
    protected boolean isRefreshAndLoadMoreEnabled() {
        return true;
    }

    @Override
    public void loadData(int pageIndex) {
        viewModel.loadCollectData(pageIndex);
    }

    @Override
    protected BaseQuickAdapter<CollectBean.DatasBean, BaseViewHolder> createAdapter() {
        CollectAdapter adapter = new CollectAdapter(null);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(getmRecyclerView());

        itemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        // 开启滑动删除
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(onItemSwipeListener);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CollectBean.DatasBean bean = (CollectBean.DatasBean) adapter.getData().get(position);
                Bundle b = new Bundle();
                b.putString(WebViewActivity.WEB_URL, bean.getLink());
                b.putString(WebViewActivity.TITLE, bean.getTitle());
                readyGo(WebViewActivity.class, b);
            }
        });
        return adapter;
    }

    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Logger.i("onItemSwipeStart:" + pos);
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            Logger.i("clearView:" + pos);
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            Logger.i("onItemSwiped:" + pos);
            CollectBean.DatasBean bean = getmAdapter().getData().get(pos);
            cancelCollectId = bean.getOriginId();
            viewModel.unCollect(bean.getId(), bean.getOriginId());
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };

}
