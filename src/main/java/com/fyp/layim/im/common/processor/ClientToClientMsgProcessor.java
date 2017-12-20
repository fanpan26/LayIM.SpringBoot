package com.fyp.layim.im.common.processor;

import com.fyp.layim.im.common.intf.LayimAbsMsgProcessor;
import com.fyp.layim.im.packet.ChatRequestBody;
import com.fyp.layim.im.packet.LayimToClientChatMsgBody;
import com.fyp.layim.im.packet.convert.BodyConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

/**
 * @author fyp
 * @crate 2017/11/19 23:38
 * @project SpringBootLayIM
 */
public class ClientToClientMsgProcessor extends LayimAbsMsgProcessor<ChatRequestBody> {

    private static final Logger logger = LoggerFactory.getLogger(ClientToClientMsgProcessor.class);

    @Override
    public WsResponse process(WsRequest layimPacket, ChatRequestBody body, ChannelContext channelContext) throws Exception {
        logger.info("ClientToClientMsgProcessor.process");

        LayimToClientChatMsgBody msgBody = BodyConvert.getInstance().convertToClientMsgBody(body,channelContext);
        WsResponse toClientBody = BodyConvert.getInstance().convertToTextResponse(msgBody);
        //发送消息
        logger.info("LayimAbsMsgProcessor:消息处理完毕，发送给对方");
        send(channelContext,toClientBody,body.getToId());
        return null;
    }

    /**
     * 这个方法提出来的目的，是让 ClientToGroupMsgProcessor 进行重写（当然这么设计只是符合Layim，讲究通用性的话应该是分开设计比较好）
     * */
    public void send(ChannelContext channelContext,WsResponse toClientBody,String toId){
        SetWithLock<ChannelContext> toChannelContext = Aio.getChannelContextsByUserid(channelContext.getGroupContext(),toId);
        if(toChannelContext == null) {
            return;
        }
        Aio.sendToSet(channelContext.getGroupContext(),toChannelContext,toClientBody,null);
    }

    @Override
    public Class<ChatRequestBody> getBodyClass() {
        return ChatRequestBody.class;
    }
}
