package com.fyp.layim.im.server.handler;

import com.fyp.layim.domain.User;
import com.fyp.layim.im.packet.ContextUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * @author fyp
 * @crate 2017/11/19 18:35
 * @project SpringBootLayIM
 */
public class LayimMsgHandler implements IWsMsgHandler {

    private static final Logger logger = LoggerFactory.getLogger(LayimMsgHandler.class);

    @Override
    public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) throws Exception {
        return "消息发送成功";
    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return "暂不支持字节消息解析";
    }

    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //这里需要重构
        //增加token验证方法
        String path = httpRequest.getRequestLine().getPath();

        String token = path.substring(1);
        User user = new User();
        user.setUserName("小盘子");
        user.setId(Long.parseLong(token));
        user.setAvatar("/photo.jpg");
        if (user != null) {
            logger.info("LayimMsgHandler.handshake:绑定用户信息");
            ContextUser contextUser = new ContextUser();
            contextUser.setUserid(token);
            contextUser.setUsername(user.getUserName());
            contextUser.setAvatar(user.getAvatar());

            channelContext.setAttribute(contextUser.getUserid(), contextUser);
            Aio.bindUser(channelContext, token);
            //测试
            Aio.bindGroup(channelContext,"1");
        } else {
            httpResponse.setStatus(HttpResponseStatus.C404);
        }
        return httpResponse;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }
}
