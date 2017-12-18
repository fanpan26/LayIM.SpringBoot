package com.fyp.layim.common.event.application;

import org.springframework.context.ApplicationEvent;

/**
 * @author fyp
 * @crate 2017/12/6 21:59
 * @project SpringBootLayIM
 * 已经用 guava 的 EventBus 代替
 */
public class ApplyEvent extends ApplicationEvent {

    public ApplyEvent(Object source) {
        super(source);
    }

    private long toid;

    public long getToId(){
        return toid;
    }

    public ApplyEvent(Object source, long toId) {
        super(source);
        this.toid = toId;
    }

    public ApplyEvent(Object source,String toId){
        this(source,Long.parseLong(toId));
    }
}
