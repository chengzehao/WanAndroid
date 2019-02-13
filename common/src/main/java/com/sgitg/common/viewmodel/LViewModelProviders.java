package com.sgitg.common.viewmodel;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/13/013 22:12
 */
public class LViewModelProviders {

    public static <T extends BaseViewModel> T of(@NonNull FragmentActivity activity, Class<T> modelClass) {
        T t = ViewModelProviders.of(activity).get(modelClass);
        t.setLifecycleOwner(activity);
        return t;
    }

    public static <T extends BaseViewModel> T of(@NonNull Fragment fragment, Class<T> modelClass) {
        T t = ViewModelProviders.of(fragment).get(modelClass);
        t.setLifecycleOwner(fragment);
        return t;
    }

}