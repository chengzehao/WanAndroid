package com.symbol.wanandroid;

import com.sgitg.common.LibApp;
import com.sgitg.common.NdkMethods;
import com.sgitg.common.utils.CommonUtils;

/**
 * App 程序入口
 *
 * @author 周麟
 * @created 2018/1/4 10:09
 */
public class App extends LibApp {
    @Override
    public void onCreate() {
        super.onCreate();
        NdkMethods.signatureVerify(this);
        /*if (CommonUtils.isRoot()) {
            throw new RuntimeException(getString(R.string.illegal_root));
        }
        if (CommonUtils.isEmulator(this)) {
            throw new RuntimeException(getString(R.string.illegal_virtual_machine));
        }*/
    }
}
