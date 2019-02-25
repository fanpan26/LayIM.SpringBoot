package com.fyp.im.processor;

import com.fyp.entity.LayIMConstants;
import com.fyp.im.utils.IMUtil;
import org.tio.core.ChannelContext;

/**
 * 客户端对客户端发送消息
 * */
public class ClientToClientProcessor extends MsgStoredProcessor {

    @Override
    protected void processMessage(ChannelContext channelContext){
        IMUtil.send(channelContext,getTargetId(),buildSendMessage(channelContext));
    }

    @Override
    protected long getId(ChannelContext channelContext) {
        return Long.valueOf(channelContext.userid);
    }

    @Override
    protected String getType(ChannelContext channelContext) {
        return LayIMConstants.CHAT_TYPE_FRIEND;
    }
}
