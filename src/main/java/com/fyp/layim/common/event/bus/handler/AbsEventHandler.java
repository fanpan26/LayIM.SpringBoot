package com.fyp.layim.common.event.bus.handler;

public abstract class AbsEventHandler<T> {
    public abstract void handle(T body);
}
