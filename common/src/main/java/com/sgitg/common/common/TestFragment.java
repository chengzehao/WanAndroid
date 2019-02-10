package com.sgitg.common.common;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

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
    private String mContent = "Material";

    public static TestFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("DATA", content);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        super.init();
        if (getArguments() != null) {
            mContent = getArguments().getString("DATA");
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragemnt_test;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        ((TextView) getViewById(R.id.content)).setText(mContent);
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
