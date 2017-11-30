package com.fyp.layim.im.common.processor;

import com.fyp.layim.im.packet.CheckOnlineRequestBody;
import com.fyp.layim.im.packet.LayimToClientCheckOnlineCountMsgBody;
import com.fyp.layim.im.packet.convert.BodyConvert;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

/**
 * 统计群内人数
 * */
public class ClientCheckOnlineCountMsgProcessor extends ClientCheckOnlineMsgProcessor {
    @Override
    public WsResponse process(WsRequest layimPacket, CheckOnlineRequestBody body, ChannelContext channelContext) throws Exception {

        SetWithLock<ChannelContext> channelLocks =
                Aio.getChannelContextsByGroup(channelContext.getGroupContext(),body.getId());
        int onlineCount = channelLocks.getObj().size();

        LayimToClientCheckOnlineCountMsgBody msgBody = new LayimToClientCheckOnlineCountMsgBody(onlineCount);
        msgBody.setId(body.getId());

        send(msgBody,channelContext);
        return null;
    }
}
