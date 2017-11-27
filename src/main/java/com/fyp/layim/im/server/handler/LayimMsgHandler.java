package com.fyp.layim.im.server.handler;

import com.fyp.layim.im.common.util.SpringUtil;
import com.fyp.layim.im.packet.LayimContextUserInfo;
import com.fyp.layim.service.UserService;
import com.fyp.layim.service.auth.TokenVerify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.net.URLDecoder;
import java.util.List;

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
        return handleHandshakeUserInfo(httpRequest,httpResponse,channelContext);
    }

    /**
     * 用 @Autowired 注解UserService 不起作用（原因我不是很清楚）
     * 解析客户端的token，获取用户信息转化为ContextUser对象
     * 将ContextUser对象，以UserId为Key，ContextUser为value存入ChannelContext的Attribute中，方便后续使用当前用户信息
     * 获取用户群组，遍历调用 Aio.bindGroup方法加入群组
     * */
    private HttpResponse handleHandshakeUserInfo(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws  Exception {
        UserService userService = (UserService) SpringUtil.getBean("userService");
        //增加token验证方法

        String path = httpRequest.getRequestLine().getPath();
        String token = URLDecoder.decode(path.substring(1),"utf-8");

        String userId = TokenVerify.IsValid(token);

        if (userId == null) {
            //没有token 未授权
            httpResponse.setStatus(HttpResponseStatus.C401);
        } else {
            //解析token
            LayimContextUserInfo userInfo = userService.getContextUserInfo(Long.parseLong(userId));
            if (userInfo == null) {
                //没有找到用户
                httpResponse.setStatus(HttpResponseStatus.C404);
            } else {
                channelContext.setAttribute(userId, userInfo.getContextUser());
                //绑定用户ID
                Aio.bindUser(channelContext, userId);
                //绑定用户群组
                List<String> groupIds = userInfo.getGroupIds();
                //绑定用户群信息
                if (groupIds != null) {
                    groupIds.forEach((String groupId) -> Aio.bindGroup(channelContext, groupId));
                }
            }
        }
        return httpResponse;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }
}
