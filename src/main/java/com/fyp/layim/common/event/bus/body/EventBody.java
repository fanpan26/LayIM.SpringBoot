package com.fyp.layim.common.event.bus.body;

/**
 * 事件消息体
 * */
public final class EventBody<T> {
    private int eventType;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public T getEventData() {
        return eventData;
    }

    public void setEventData(T eventData) {
        this.eventData = eventData;
    }

    private T eventData;
}
