package com.business.user.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.business.user.R;
import com.business.user.bean.UserBean;
import com.business.user.viewmodel.RegisterViewModel;
import com.gs.keyboard.KeyboardType;
import com.gs.keyboard.SecurityConfigure;
import com.gs.keyboard.SecurityEditText;
import com.gs.keyboard.SecurityKeyboard;
import com.sgitg.common.base.BaseFragment;
import com.sgitg.common.event.EventCenter;
import com.sgitg.common.event.EventCode;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.thread.MainThreadExcute;
import com.sgitg.common.thread.ThreadManager;
import com.sgitg.common.utils.KeyboardUtils;
import com.sgitg.common.utils.StringUtils;
import com.sgitg.common.utils.ToastUtils;
import com.sgitg.common.viewmodel.LViewModelProviders;

import org.greenrobot.eventbus.EventBus;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/8/008 15:35
 */

public class RegisterFragment extends BaseFragment {
    private EditText mAccount;
    private SecurityEditText mM;
    private SecurityEditText mMAgain;

    private RegisterViewModel mViewModel;

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, RegisterViewModel.class);
        mViewModel.getResult().observe(this, new Observer<RestResult<UserBean>>() {
            @Override
            public void onChanged(@Nullable RestResult<UserBean> stringRestResult) {
                if (checkHttpResult(stringRestResult)) {
                    ToastUtils.getInstance().showSuccessInfoToast("注册成功！");
                    EventBus.getDefault().post(new EventCenter<>(EventCode.EVENT_REGISTER_SUCCESS));
                    successRegister(stringRestResult.getData());
                } else {
                    ToastUtils.getInstance().showErrorInfoToast(stringRestResult.getErrorMsg());
                }
            }
        });

        return mViewModel;
    }

    private void successRegister(final UserBean user) {
        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                user.save();
                MainThreadExcute.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventCenter<>(EventCode.EVENT_LOGIN_SUCCESS, user.getUsername()));
                        getMActivity().finish();
                    }
                });
            }
        });
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_register;
    }

    @Override
    protected void setUpView() {
        mAccount = getViewById(R.id.account);
        mM = getViewById(R.id.mm);
        mMAgain = getViewById(R.id.re_mm);
        Button mBtRegister = getViewById(R.id.bt_register);
        LinearLayout mRootView = getViewById(R.id.root);
        SecurityConfigure configure = new SecurityConfigure().setDefaultKeyboardType(KeyboardType.NUMBER);
        new SecurityKeyboard(mRootView, configure);
        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(getMActivity());
                if (StringUtils.isNullOrEmpty(mAccount.getText().toString())) {
                    ToastUtils.getInstance().showErrorInfoToast("账号不能为空！");
                    return;
                }
                if (StringUtils.isNullOrEmpty(mM.getText().toString())) {
                    ToastUtils.getInstance().showErrorInfoToast("密码不能为空！");
                    return;
                }

                if (StringUtils.isNullOrEmpty(mMAgain.getText().toString())) {
                    ToastUtils.getInstance().showErrorInfoToast("请您确认密码！");
                    return;
                }
                if (mM.getText().toString().contains(" ") || mAccount.getText().toString().contains(" ") || mMAgain.getText().toString().contains(" ")) {
                    ToastUtils.getInstance().showErrorInfoToast("格式非法！");
                    return;
                }
                if (!mM.getText().toString().equals(mMAgain.getText().toString())) {
                    ToastUtils.getInstance().showErrorInfoToast("两次密码输入不一致！");
                    return;
                }
                mViewModel.register(mAccount.getText().toString(), mM.getText().toString());
            }
        });
    }


}
