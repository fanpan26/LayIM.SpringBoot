package com.fyp.layim.im.common.processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsResponse;

/**
 * 这里由于群聊消息用的接收消息体都是ChatRequestBody， 所以，直接继承 ClientToClientMsgProcessor，并且重写process方法即可
 * */
public class ClientToGroupMsgProcessor extends ClientToClientMsgProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ClientToGroupMsgProcessor.class);
    public void send(ChannelContext channelContext,WsResponse toClientBody,String toId){
        logger.info("execute ClientToGroupMsgProcessor.send");
        Aio.sendToGroup(channelContext.getGroupContext(),toId,toClientBody);
    }
}
