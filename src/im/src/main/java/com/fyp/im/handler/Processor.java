package com.fyp.im.handler;

import com.fyp.im.MsgType;
import com.fyp.im.processor.AbstractMsgProcessor;
import com.fyp.im.processor.ClientToClientProcessor;
import com.fyp.im.processor.ClientToGroupProcessor;
import org.tio.core.ChannelContext;

import java.util.HashMap;
import java.util.Map;

public class Processor {
    /**
     * 消息处理入口
     * */
    public static Object process(ChannelContext channelContext,byte[] body) {
        getProcessor(body).process(channelContext, body);
        return null;
    }

    private static AbstractMsgProcessor getProcessor(byte[] body) {
        MsgType type = MsgType.valueOf(body[4]);
        AbstractMsgProcessor processor = processors.get(type);
        if (processor == null) {
            throw new IllegalArgumentException("no msgProcessor for msgType :" + type.getValue());
        }
        return processor;
    }
    private static final Map<MsgType,AbstractMsgProcessor> processors = new HashMap<>();

    static {
        processors.put(MsgType.clientToClient,new ClientToClientProcessor());
        processors.put(MsgType.clientToGroup,new ClientToGroupProcessor());
    }
}
