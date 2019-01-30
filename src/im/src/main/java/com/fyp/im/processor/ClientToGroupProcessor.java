package com.fyp.im.processor;

import com.fyp.im.utils.IMUtil;
import org.tio.core.ChannelContext;

public class ClientToGroupProcessor extends MsgStoredProcessor {
    @Override
    protected void processMessage(ChannelContext channelContext){
        IMUtil.sendToGroup(channelContext,getTargetId(),buildSendMessage(channelContext));
    }

    @Override
    protected long getId(ChannelContext channelContext) {
        return getTargetId();
    }

    @Override
    protected String getType(ChannelContext channelContext) {
        return "group";
    }
}
