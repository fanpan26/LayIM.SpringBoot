package com.fyp.layim.common.event.bus;

import com.fyp.layim.common.event.bus.handler.AbsEventHandler;
import com.fyp.layim.common.event.bus.handler.AddFriendEventHandler;
import com.fyp.layim.common.event.bus.handler.ApplyEventHandler;
import com.fyp.layim.common.event.bus.body.EventBody;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 基于guava的EventBus
 * */
public class EventUtil {

    private static final AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));

    private static Logger logger = LoggerFactory.getLogger(EventUtil.class);

    private static Map<Integer,AbsEventHandler> handlerMap = new HashMap<>(2);

    private static AbsEventHandler getHandler(int type){
        return handlerMap.get(type);
    }

    static {
        handlerMap.put(LayimEventType.applyNotice,new ApplyEventHandler());
        handlerMap.put(LayimEventType.addFriendNotice,new AddFriendEventHandler());
        //注册事件
        logger.info("注册事件");
        eventBus.register(new Object(){
            @Subscribe
            public void listener(EventBody eventBody){
                AbsEventHandler handler = getHandler(eventBody.getEventType());
                handler.handle(eventBody.getEventData());
            }
        });
    }

    /**
     * 发布一条消息
     * */
    public static void publish(EventBody eventBody ){
        eventBus.post(eventBody);
    }
}
