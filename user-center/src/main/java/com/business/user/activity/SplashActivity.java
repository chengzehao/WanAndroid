package com.business.user.activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.business.user.R;
import com.sgitg.common.base.BaseActivity;
import com.sgitg.common.thread.MainThreadExcute;
import com.sgitg.common.utils.ToastUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.sofia.Sofia;

import java.util.List;

import top.wefor.circularanim.CircularAnim;

/**
 * SplashActivity 启动页
 *
 * @author 周麟
 * @created 2018/1/4 10:57
 */
public class SplashActivity extends BaseActivity {
    private static final int DELAY = 2000;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpView() {
        Sofia.with(this).statusBarDarkFont().statusBarBackgroundAlpha(0).invasionStatusBar();
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE, Permission.Group.CAMERA)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        MainThreadExcute.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CircularAnim.fullActivity(SplashActivity.this, findViewById(R.id.appName))
                                        .colorOrImageRes(R.color.light_color_blue)
                                        .go(new CircularAnim.OnAnimationEndListener() {
                                            @Override
                                            public void onAnimationEnd() {
                                                ARouter.getInstance().build("/WanAndroid/MainActivity").navigation();
                                                finish();
                                            }
                                        });
                            }
                        }, DELAY);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        ToastUtils.getInstance().showErrorInfoToast(getString(R.string.permission_reject));
                        finish();
                    }
                })
                .start();
    }

    @Override
    public void onBackPressed() {

    }
}
