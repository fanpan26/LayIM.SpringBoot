package com.fyp.layim.common.event.bus.EventHandler;

public abstract class AbsEventHandler<T> {
    public abstract void handle(T body);
}
