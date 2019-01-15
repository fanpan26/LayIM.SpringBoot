package com.fyp.im.processor;

import com.fyp.im.utils.IMUtil;
import org.tio.core.ChannelContext;

/**
 * 客户端对客户端发送消息
 * */
public class ClientToClientProcessor extends MsgStoredProcessor {

    @Override
    protected void processMessage(ChannelContext channelContext){
        IMUtil.send(channelContext,getTargetId(),getBody());
    }
}
