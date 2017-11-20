package com.fyp.layim.im.common.intf;

import com.fyp.layim.im.common.util.ByteUtil;
import com.fyp.layim.im.packet.LayimBaseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

/**
 * @author fyp
 * @crate 2017/11/19 23:39
 * @project SpringBootLayIM
 */
public abstract class LayimAbsMsgProcessor<T extends LayimBaseBody> implements LayimMsgProcessor{
    private static Logger logger = LoggerFactory.getLogger(LayimAbsMsgProcessor.class);

    public abstract Class<T> getBodyClass();

    /**
     * 这里采用showcase中的设计思路（反序列化消息之后，由具体的消息处理器处理）
     * */
    @Override
    public WsResponse process(WsRequest layimPacket, ChannelContext channelContext) throws Exception {
        logger.info("LayimAbsMsgProcessor.process");
        Class<T> clazz = getBodyClass();
        T body = null;
        if (layimPacket.getBody() != null) {
            String json = ByteUtil.toText(layimPacket.getBody());
            body = Json.toBean(json, clazz);
        }
        return process(layimPacket, body, channelContext);
    }

    public  abstract WsResponse process(WsRequest layimPacket,T body,ChannelContext channelContext) throws  Exception;
}
