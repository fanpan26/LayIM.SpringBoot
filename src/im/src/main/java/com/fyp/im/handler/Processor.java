package com.fyp.im.handler;

import com.fyp.im.MsgType;
import com.fyp.im.processor.AbstractMsgProcessor;
import com.fyp.im.processor.ClientToClientProcessor;
import com.fyp.im.processor.ClientToGroupProcessor;
import com.fyp.im.processor.PingProcessor;
import org.tio.core.ChannelContext;

public class Processor {
    /**
     * 消息处理入口
     */
    public static Object process(ChannelContext channelContext, byte[] body) {
        getProcessor(body[4]).process(channelContext, body);
        return null;
    }

    private static AbstractMsgProcessor getProcessor(byte msgType) {
        MsgType type = MsgType.valueOf(msgType);
        switch (type) {
            case clientToClient:
                return new ClientToClientProcessor();
            case clientToGroup:
                return new ClientToGroupProcessor();
            case ping:
                return new PingProcessor();
        }
        return null;
    }
}
