package com.fyp.layim.im.common.processor;

import com.fyp.layim.im.common.intf.LayimAbsMsgProcessor;
import com.fyp.layim.im.packet.CheckOnlineRequestBody;
import com.fyp.layim.im.packet.LayimToClientCheckOnlineMsgBody;
import com.fyp.layim.im.packet.convert.BodyConvert;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

/**
 * 检测某个用户是否在线
 * */
public class ClientCheckOnlineMsgProcessor extends LayimAbsMsgProcessor<CheckOnlineRequestBody>{
    @Override
    public Class<CheckOnlineRequestBody> getBodyClass() {
        return CheckOnlineRequestBody.class;
    }

    @Override
    public WsResponse process(WsRequest layimPacket, CheckOnlineRequestBody body, ChannelContext channelContext) throws Exception {
        ChannelContext checkChannelContext = Aio.getChannelContextByUserid(channelContext.getGroupContext(),body.getId());
        //检查是否在线
        boolean isOnline = checkChannelContext != null && !checkChannelContext.isClosed();
        //组织返回数据
        LayimToClientCheckOnlineMsgBody msgBody = new LayimToClientCheckOnlineMsgBody(isOnline);
        msgBody.setId(body.getId());
        //返回信息
        send(msgBody,channelContext);
        return null;
    }

    protected void send(Object body,ChannelContext channelContext) throws Exception{
        WsResponse toClientBody = BodyConvert.getInstance().convertToTextResponse(body);
        Aio.send(channelContext,toClientBody);
    }
}
