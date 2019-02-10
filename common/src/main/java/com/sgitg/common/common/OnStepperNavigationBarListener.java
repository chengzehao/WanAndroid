package com.sgitg.common.common;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/3/12 14:09
 */
public interface OnStepperNavigationBarListener {
    /**
     * STEP组件下一步按钮状态回调
     *
     * @param enabled 是否可用
     */
    void onChangeEndButtonEnabled(boolean enabled);
}
