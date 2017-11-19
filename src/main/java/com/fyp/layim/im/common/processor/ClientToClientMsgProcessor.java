package com.fyp.layim.im.common.processor;

import com.fyp.layim.im.common.LayimConst;
import com.fyp.layim.im.common.intf.LayimAbsMsgProcessor;
import com.fyp.layim.im.packet.ChatReponseBody;
import com.fyp.layim.im.packet.ChatRequestBody;
import com.fyp.layim.im.packet.ClientToClientMsgBody;
import com.fyp.layim.im.packet.LayimPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;

/**
 * @author fyp
 * @crate 2017/11/19 23:38
 * @project SpringBootLayIM
 */
public class ClientToClientMsgProcessor extends LayimAbsMsgProcessor<ChatRequestBody> {

    private static final Logger logger = LoggerFactory.getLogger(ClientToClientMsgProcessor.class);

    @Override
    public WsResponse process(LayimPacket layimPacket, ChatRequestBody body, ChannelContext channelContext) throws Exception {
        WsSessionContext sessionContext = (WsSessionContext) channelContext.getAttribute();

        ClientToClientMsgBody msgBody = new ClientToClientMsgBody();
        String currentUserId = channelContext.getUserid();
        msgBody.setUsername("zhangsan");
        //发送人
        msgBody.setId(currentUserId);
        //消息内容
        msgBody.setContent("收到消息了吗？");
        //发送时间
        msgBody.setTimestamp(body.getTimestamp());

        logger.info(msgBody.getContent());
        logger.info(body.getToId());
        logger.info(Json.toJson(msgBody));
        WsResponse request = new WsResponse();
        request.setBody(Json.toJson(msgBody).getBytes(LayimConst.CHAR_SET));
        request.setWsBodyText(Json.toJson(msgBody));
        request.setWsBodyLength(request.getWsBodyText().length());
        request.setWsOpcode(Opcode.TEXT);
        ChannelContext toChannelContext = Aio.getChannelContextByUserid(channelContext.getGroupContext(),body.getToId());
        //要接收消息的对象
        Aio.send(toChannelContext,request);
        return null;
    }

    @Override
    public Class<ChatRequestBody> getBodyClass() {
        return ChatRequestBody.class;
    }
}
