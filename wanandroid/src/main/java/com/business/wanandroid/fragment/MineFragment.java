package com.business.wanandroid.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.business.wanandroid.R;
import com.sgitg.common.ConstantValue;
import com.sgitg.common.EventCenter;
import com.sgitg.common.base.BaseFragment;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.route.UserProvider;
import com.sgitg.common.thread.MainThreadExcute;
import com.sgitg.common.thread.ThreadManager;
import com.sgitg.common.utils.StringUtils;
import com.sgitg.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/19/019 19:52
 */

public class MineFragment extends BaseFragment {
    private TextView login;
    private Button logout;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void setUpView() {
        login = getViewById(R.id.login);
        Button my_collect = getViewById(R.id.my_collect);
        Button todo = getViewById(R.id.todo);
        logout = getViewById(R.id.logout);
        my_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void setUpData() {
        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                final String username = ((UserProvider) ARouter.getInstance().build("/User/Service").navigation()).getUserName();
                MainThreadExcute.post(new Runnable() {
                    @Override
                    public void run() {
                        if (StringUtils.isNullOrEmpty(username)) {
                            notLogin();
                        } else {
                            logined();
                        }
                    }
                });
            }
        });
    }

    private void notLogin() {
        login.setText("点击登录");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((UserProvider) ARouter.getInstance().build("/User/Service").navigation()).toLogin();
            }
        });
        logout.setOnClickListener(null);
    }

    private void logined() {
        login.setText(((UserProvider) ARouter.getInstance().build("/User/Service").navigation()).getUserName());
        login.setOnClickListener(null);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog("正在注销");
                ((UserProvider) ARouter.getInstance().build("/User/Service").navigation()).logoutServer(new HttpListener<String>() {
                    @Override
                    public void onResponse(int what, RestResult<String> t) {
                        dismissLoadingDialog();
                        if (checkHttpResult(t)) {
                            EventBus.getDefault().post(new EventCenter<>(ConstantValue.EVENT_LOGOUT_SUCCESS));
                            ((UserProvider) ARouter.getInstance().build("/User/Service").navigation()).clearSp();
                            notLogin();
                        } else {
                            ToastUtils.getInstance().showErrorInfoToast(t.getErrorMsg());
                        }
                    }
                });
            }
        });
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        super.onEventComming(eventCenter);
        if (eventCenter.getEventCode() == ConstantValue.EVENT_LOGIN_SUCCESS) {
            logined();
        }
    }
}
