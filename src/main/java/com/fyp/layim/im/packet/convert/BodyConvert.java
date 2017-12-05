package com.fyp.layim.im.packet.convert;

import com.fyp.layim.im.common.LayimConst;
import com.fyp.layim.im.common.intf.LayimMsgType;
import com.fyp.layim.im.common.util.ByteUtil;
import com.fyp.layim.im.packet.ChatRequestBody;
import com.fyp.layim.im.packet.LayimToClientChatMsgBody;
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
            //返回text类型消息（如果这里设置成 BINARY,那么客户端就需要进行解析了）
            response.setWsOpcode(Opcode.TEXT);
        }
        return response;
    }
    public LayimToClientChatMsgBody convertToClientMsgBody(ChatRequestBody requestBody, ChannelContext channelContext){
        LayimToClientChatMsgBody msgBody = new LayimToClientChatMsgBody();
        //先获取用户信息
        ContextUser contextUser =(ContextUser)channelContext.getAttribute(channelContext.getUserid());
        //设置当前用户名
        msgBody.setUsername(contextUser.getUsername());
        //用户头像
        msgBody.setAvatar(contextUser.getAvatar());
        /**
         * 这里要注意，如果是单聊，那么id为发送人id，否则为群组id
         * 根据requestBody的msgType判断
         * 当msgType==1 的时候，toId为接收人的ID
         * 当msgType==2 的时候，toId为接收群的ID
         * 这里有了if else 判断，当时也考虑用抽象类实现。
         * */
        if(requestBody.getMtype()==LayimMsgType.CLIENT_TO_CLIENT){
            msgBody.setId(contextUser.getUserid());
            //消息类型：好友
            msgBody.setType(LayimConst.CHAT_TYPE_FRIEND);
        }else if(requestBody.getMtype() == LayimMsgType.CLIENT_TO_GROUP){
            msgBody.setId(requestBody.getToId());
            //消息类型：群组
            msgBody.setType(LayimConst.CHAT_TYPE_GROUP);
            //群聊消息会让用户都收到信息，所以，自己的就不给自己显示了，需要客户端根据from字段进行处理，另外，单聊消息就不给赋值了，没必要。
            msgBody.setFrom(channelContext.getUserid());
        }
        msgBody.setMtype(requestBody.getMtype());
        //消息内容
        msgBody.setContent(requestBody.getContent());
        //发送时间
        msgBody.setTimestamp(requestBody.getTimestamp());
        return msgBody;
    }
}
