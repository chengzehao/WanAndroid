package com.sgitg.common.http;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/5/23 12:11
 */

public class RestResult<T> {
    private int errorCode;
    private T data;
    private String errorMsg;

    public RestResult(int errorCode, T data, String errorMsg) {
        this.errorCode = errorCode;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
