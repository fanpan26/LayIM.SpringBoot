package com.fyp.layim.im.common.processor;

import com.fyp.layim.im.common.intf.LayimAbsMsgProcessor;
import com.fyp.layim.im.packet.ChatRequestBody;
import com.fyp.layim.im.packet.ClientToClientMsgBody;
import com.fyp.layim.im.packet.convert.BodyConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
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

        ClientToClientMsgBody msgBody = BodyConvert.getInstance().convertToMsgBody(body,channelContext);
        WsResponse response = BodyConvert.getInstance().convertToTextResponse(msgBody);

        ChannelContext toChannelContext = Aio.getChannelContextByUserid(channelContext.getGroupContext(),body.getToId());
        //要接收消息的对象
        Aio.send(toChannelContext,response);
        return null;
    }

    @Override
    public Class<ChatRequestBody> getBodyClass() {
        return ChatRequestBody.class;
    }
}
