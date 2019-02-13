package com.sgitg.common.viewmodel;

import android.arch.lifecycle.MutableLiveData;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/13/013 21:47
 */
public interface IViewModelAction {

    void startLoading(String message);

    void dismissLoading();

    void showSuccessToast(String message);

    void showFaillToast(String message);

    void finish();

    void finishWithResultOk();

    MutableLiveData<BaseActionEvent> getActionLiveData();

}