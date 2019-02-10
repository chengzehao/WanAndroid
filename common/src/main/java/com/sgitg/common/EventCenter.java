package com.sgitg.common;

/**
 * EventCenter 事件模型
 *
 * @author 周麟
 * @created 2018/1/4 11:15
 */
public class EventCenter<T> {
    private T data;
    private int eventCode = -1;

    public EventCenter(int eventCode) {
        this(eventCode, null);
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public T getData() {
        return this.data;
    }
}
