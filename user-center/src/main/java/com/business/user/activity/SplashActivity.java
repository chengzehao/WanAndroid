package com.business.user.activity;

import android.os.Looper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.business.user.Authority;
import com.business.user.R;
import com.business.user.utils.AntiHijackingUtils;
import com.sgitg.common.base.BaseActivity;
import com.sgitg.common.thread.MainThreadExcute;
import com.sgitg.common.thread.ThreadManager;
import com.sgitg.common.utils.StringUtils;
import com.sgitg.common.utils.ToastUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

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
                                        .colorOrImageRes(R.color.colorPrimaryDark)
                                        .go(new CircularAnim.OnAnimationEndListener() {
                                            @Override
                                            public void onAnimationEnd() {
                                                String username = Authority.getUserName();
                                                if (StringUtils.isNullOrEmpty(username)) {
                                                    readyGoThenKill(AccountLoginActivity.class);
                                                } else {
                                                    ARouter.getInstance().build("/Supervise/MainActivity").navigation();
                                                    finish();
                                                }
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

    @Override
    protected void onStop() {
        super.onStop();
        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                // 白名单
                boolean safe = AntiHijackingUtils.checkActivity(getApplicationContext());
                // 系统桌面
                boolean isHome = AntiHijackingUtils.isHome(getApplicationContext());
                // 锁屏操作
                boolean isReflectScreen = AntiHijackingUtils.isReflectScreen(getApplicationContext());
                // 判断程序是否当前显示
                if (!safe && !isHome && !isReflectScreen) {
                    Looper.prepare();
                    MainThreadExcute.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.getInstance().showErrorInfoToast("仓储移动引用被切换至后台，请您确认使用环境是否安全！");
                        }
                    });
                    Looper.loop();
                }
            }
        });
    }
}
