package com.fyp.layim.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author fyp
 * @crate 2017/12/6 21:59
 * @project SpringBootLayIM
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
}
