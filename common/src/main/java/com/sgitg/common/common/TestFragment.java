package com.sgitg.common.common;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sgitg.common.R;
import com.sgitg.common.base.BaseFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * TestFragment 测试占位
 *
 * @author 周麟
 * @created 2018/1/4 11:09
 */
public class TestFragment extends BaseFragment implements Step {

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragemnt_test;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
