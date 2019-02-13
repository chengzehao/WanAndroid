package com.sgitg.common.viewmodel;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/13/013 21:44
 */
public class BaseActionEvent extends BaseEvent {

    public static final int SHOW_LOADING = 1;

    public static final int DISMISS_LOADING= 2;

    public static final int SHOW_SUCCESS_TOAST = 3;

    public static final int SHOW_FAILL_TOAST = 4;

    public static final int FINISH = 5;

    public static final int FINISH_WITH_RESULT_OK = 6;

    private String message;

    public BaseActionEvent(int action) {
        super(action);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}