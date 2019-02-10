package com.sgitg.common.base;


import com.sgitg.common.R;
import com.sgitg.common.utils.ToastUtils;


/**
 * AbstractMipDoubleClickOutActivity 具备MIP平台功能 双击退出 activity基类
 *
 * @author 周麟
 * @created 2018/1/4 10:30
 */
public abstract class AbstractDoubleClickOutActivity extends BaseActivity {
    private long lastBackKeyDownTick = 0;
    private static final long MAX_DOUBLE_BACK_DURATION = 1500;

    @Override
    public void onBackPressed() {
        if (beforeOnBackPressed()) {
            long currentTick = System.currentTimeMillis();
            if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
                ToastUtils.getInstance().showErrorInfoToast(getString(R.string.exit_notifiy));
                lastBackKeyDownTick = currentTick;
            } else {
                finish();
            }
        }
    }

    protected boolean beforeOnBackPressed() {
        return true;
    }

}
