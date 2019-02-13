package com.sgitg.common.base;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.sgitg.common.EventCenter;
import com.sgitg.common.utils.ToastUtils;
import com.sgitg.common.viewmodel.BaseActionEvent;
import com.sgitg.common.viewmodel.IViewModelAction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * BaseFragment
 *
 * @author 周麟
 * @created 2018/1/4 11:19
 */
public abstract class BaseFragment extends Fragment {
    protected FragmentActivity mActivity;
    private View mContentView;
    private Context mContext;
    private AlertDialog mLoadingDialog;
    private MaterialStyledDialog mAlertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModelEvent();
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(), container, false);
        mContext = getContext();
        init();
        setUpView();
        setUpData();
        return mContentView;
    }

    private void initViewModelEvent() {
        List<ViewModel> viewModelList = initViewModelList();
        if (viewModelList != null && viewModelList.size() > 0) {
            observeEvent(viewModelList);
        } else {
            ViewModel viewModel = initViewModel();
            if (viewModel != null) {
                List<ViewModel> modelList = new ArrayList<>();
                modelList.add(viewModel);
                observeEvent(modelList);
            }
        }
    }

    protected ViewModel initViewModel() {
        return null;
    }

    ;

    protected List<ViewModel> initViewModelList() {
        return null;
    }

    private void observeEvent(List<ViewModel> viewModelList) {
        for (ViewModel viewModel : viewModelList) {
            if (viewModel instanceof IViewModelAction) {
                IViewModelAction viewModelAction = (IViewModelAction) viewModel;
                viewModelAction.getActionLiveData().observe(this, new Observer<BaseActionEvent>() {
                    @Override
                    public void onChanged(@Nullable BaseActionEvent baseActionEvent) {
                        if (baseActionEvent != null) {
                            switch (baseActionEvent.getAction()) {
                                case BaseActionEvent.SHOW_LOADING: {
                                    showLoadingDialog(baseActionEvent.getMessage());
                                    break;
                                }
                                case BaseActionEvent.DISMISS_LOADING: {
                                    dismissLoadingDialog();
                                    break;
                                }
                                case BaseActionEvent.SHOW_SUCCESS_TOAST: {
                                    ToastUtils.getInstance().showSuccessInfoToast(baseActionEvent.getMessage());
                                    break;
                                }
                                case BaseActionEvent.SHOW_FAILL_TOAST: {
                                    ToastUtils.getInstance().showErrorInfoToast(baseActionEvent.getMessage());
                                    break;
                                }
                                case BaseActionEvent.FINISH: {
                                    getMActivity().finish();
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 设置视图view id
     *
     * @return id
     */
    protected abstract int setLayoutResourceID();

    protected void init() {
    }

    protected void setUpView() {
    }

    protected void setUpData() {
    }

    protected View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }

    public FragmentActivity getMActivity() {
        return mActivity;
    }

    protected boolean isBindEventBusHere() {
        return false;
    }

    @Subscribe
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComming(eventCenter);
        }
    }

    protected void onEventComming(EventCenter eventCenter) {

    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void showLoadingDialog(String text) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            return;
        }
        mLoadingDialog = new SpotsDialog.Builder().setContext(mContext).build();
        mLoadingDialog.setMessage(text);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }

    public void showLoadingDialog(String text, DialogInterface.OnCancelListener onCancelListener) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            return;
        }
        mLoadingDialog = new SpotsDialog.Builder().setContext(mContext).build();
        mLoadingDialog.setMessage(text);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setOnCancelListener(onCancelListener);
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 创建并显示一个Dialog，两个按钮。
     *
     * @param dialogId  Dialog的标识
     * @param title     标题
     * @param message   内容
     * @param positive  确认文本
     * @param negative  取消文本
     * @param colorHead 头部颜色资源
     */
    public void showAlertDialog(final int dialogId, String title, String message, String positive, String negative, int colorHead) {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            return;
        }
        mAlertDialog = new MaterialStyledDialog.Builder(mContext).setTitle(title).setDescription(message)
                .setHeaderColor(colorHead)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setPositiveText(positive)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onPositive(dialogId);
                    }
                })
                .setNegativeText(negative)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onNegative(dialogId);
                    }
                })
                .show();
    }

    /**
     * 创建并显示一个Dialog，一个按钮。
     *
     * @param dialogId  Dialog的标识
     * @param title     标题
     * @param message   内容
     * @param positive  确认文本
     * @param colorHead 头部颜色资源
     */
    public void showAlertDialog(final int dialogId, String title, String message, String positive, int colorHead) {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            return;
        }
        mAlertDialog = new MaterialStyledDialog.Builder(mContext).setTitle(title).setDescription(message)
                .setHeaderColor(colorHead)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setPositiveText(positive)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onPositive(dialogId);
                    }
                })
                .show();
    }

    /**
     * 关闭当前显示的对话框
     */
    public void dismissAlertDialog() {
        try {
            if (mAlertDialog != null) {
                mAlertDialog.dismiss();
                mAlertDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击确认按钮的回调方法(子类可实现该方法)
     *
     * @param dialogId 一个Activity可能有多个AlertDialog，这个id是来区别我们点击的是哪个AlertDialog
     */
    public void onPositive(int dialogId) {
    }

    /**
     * 点击取消按钮的回调方法，我们一般点取消都是关闭AlertDialog,这里我预留一个回调方法在这里(子类可实现该方法)
     */
    public void onNegative(int dialogId) {

    }

    @Override
    public void onDestroy() {
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.mActivity = null;
        this.mLoadingDialog = null;
        this.mAlertDialog = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) mContentView.findViewById(id);
    }

}
