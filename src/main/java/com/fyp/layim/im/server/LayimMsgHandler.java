package com.fyp.layim.im.server;

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
        //增加token验证方法
       String path = httpRequest.getRequestLine().getPath();
       Integer tokenLength = 5;
       if(path.length()< tokenLength){
           httpResponse.setStatus(HttpResponseStatus.C403);
       }else{
           String token = path.substring(1);
           Aio.bindUser(channelContext,token);
       }
        logger.info("我手之后");
        return httpResponse;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }
}
