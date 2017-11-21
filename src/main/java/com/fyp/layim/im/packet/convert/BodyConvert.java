package com.fyp.layim.im.packet.convert;

import com.fyp.layim.im.common.LayimMsgType;
import com.fyp.layim.im.common.util.ByteUtil;
import com.fyp.layim.im.packet.ChatRequestBody;
import com.fyp.layim.im.packet.ClientToClientMsgBody;
import com.fyp.layim.im.packet.ContextUser;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsResponse;

import java.io.IOException;

/**
 * @author fyp
 * @crate 2017/11/20 7:53
 * @project SpringBootLayIM
 */
public class BodyConvert {

    private static BodyConvert instance;
    private static byte obj[] = new byte[0];

    public static BodyConvert getInstance(){
        if (instance == null){
            synchronized (obj){
                if(instance == null){
                    instance = new BodyConvert();
                }
            }
        }
        return instance;
    }

    public WsResponse convertToTextResponse(Object body) throws IOException{
        WsResponse response = new WsResponse();
        if(body != null) {
            String json = Json.toJson(body);
            response.setBody(ByteUtil.toBytes(json));
            response.setWsBodyText(json);
            response.setWsBodyLength(response.getWsBodyText().length());
            response.setWsOpcode(Opcode.TEXT);
        }
        return response;
    }
    public ClientToClientMsgBody convertToMsgBody(ChatRequestBody requestBody, ChannelContext channelContext){
        ClientToClientMsgBody msgBody = new ClientToClientMsgBody();

        ContextUser contextUser =(ContextUser)channelContext.getAttribute(channelContext.getUserid());

        //设置当前用户名
        msgBody.setUsername(contextUser.getUsername());
        //用户头像
        msgBody.setAvatar(contextUser.getAvatar());
        //发送人
        msgBody.setId(contextUser.getUserid());
        //msgBody.setId("1");
        //消息内容
        msgBody.setContent(requestBody.getContent());
        //发送时间
        msgBody.setTimestamp(requestBody.getTimestamp());
        msgBody.setMsgType(LayimMsgType.CLIENT_TO_CLIENT);

        return msgBody;
    }
}
