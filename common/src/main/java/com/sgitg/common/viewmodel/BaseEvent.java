package com.sgitg.common.viewmodel;

/**
 * 描述：
 * 
 * @author 周麟
 * @date 2019/2/13/013 21:43
 */
public class BaseEvent {

    private int action;

    public BaseEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

}