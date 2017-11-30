package com.fyp.layim.im.server.handler;

import java.nio.ByteBuffer;

import com.fyp.layim.im.common.Protocol;
import com.fyp.layim.im.common.intf.LayimAbsMsgProcessor;
import com.fyp.layim.im.common.processor.LayimMsgProcessorManager;
import com.fyp.layim.im.common.util.ByteUtil;
import com.fyp.layim.im.packet.LayimMsgProperty;
import com.fyp.layim.im.packet.LayimResponseBody;
import com.fyp.layim.im.server.config.LayimServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpRequestDecoder;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseEncoder;
import org.tio.server.intf.ServerAioHandler;
import org.tio.utils.json.Json;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsServerDecoder;
import org.tio.websocket.common.WsServerEncoder;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;
/**
 * 源代码地址：https://gitee.com/tywo45/t-io/blob/master/src/zoo/websocket/server/src/main/java/org/tio/websocket/server/WsServerAioHandler.java
 * @author fyp
 * @crate 2017/11/19 18:33
 * @project SpringBootLayIM
 * 为了将（LayIM）消息的发送与回复封装，所以需要改造此类
 */
public class LayimServerAioHandler implements ServerAioHandler{
    private static Logger log = LoggerFactory.getLogger(LayimServerAioHandler.class);

    private LayimServerConfig layimServerConfig;

    private IWsMsgHandler wsMsgHandler;

    public LayimServerAioHandler(LayimServerConfig layimServerConfig, IWsMsgHandler wsMsgHandler) {
        this.layimServerConfig = layimServerConfig;
        this.wsMsgHandler = wsMsgHandler;
    }

    /**
     * 消息解码
     * 这里将消息的返回值进行封装
    */
    @Override
    public WsRequest decode(ByteBuffer buffer, ChannelContext channelContext) throws AioDecodeException {
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
        log.info("LayimServerAioHandler.decode");
        if (!wsSessionContext.isHandshaked()) {
            HttpRequest request = HttpRequestDecoder.decode(buffer, channelContext);
            if (request == null) {
                return null;
            }

            HttpResponse httpResponse = Protocol.updateToWebSocket(request, channelContext);
            if (httpResponse == null) {
                throw new AioDecodeException("http协议升级到websocket协议失败");
            }

            wsSessionContext.setHandshakeRequestPacket(request);
            wsSessionContext.setHandshakeResponsePacket(httpResponse);

            WsRequest wsRequestPacket = new WsRequest();
            wsRequestPacket.setHandShake(true);
            log.info("握手成功");
            return wsRequestPacket;
        }
        return WsServerDecoder.decode(buffer, channelContext);
    }

    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        WsResponse wsResponse = (WsResponse) packet;
        log.info("LayimServerAioHandler.encode");
        if (wsResponse.isHandShake()) {
            WsSessionContext imSessionContext = (WsSessionContext) channelContext.getAttribute();
            HttpResponse handshakeResponsePacket = imSessionContext.getHandshakeResponsePacket();
            return HttpResponseEncoder.encode(handshakeResponsePacket, groupContext, channelContext, false);
        }

        ByteBuffer byteBuffer = WsServerEncoder.encode(wsResponse, groupContext, channelContext);
        return byteBuffer;
    }

    public LayimServerConfig getHttpConfig() {
        return layimServerConfig;
    }


    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        log.info("LayimServerAioHandler.handler");
        WsRequest wsRequestPacket = (WsRequest) packet;

        if (wsRequestPacket.isHandShake()) {
            WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
            HttpRequest request = wsSessionContext.getHandshakeRequestPacket();
            HttpResponse httpResponse = wsSessionContext.getHandshakeResponsePacket();
            HttpResponse r = wsMsgHandler.handshake(request, httpResponse, channelContext);
            if (r == null) {
                Aio.remove(channelContext, "业务层不同意握手");
                return;
            }
            wsSessionContext.setHandshakeResponsePacket(r);

            WsResponse wsResponse = new WsResponse();
            wsResponse.setHandShake(true);
            Aio.send(channelContext, wsResponse);
            wsSessionContext.setHandshaked(true);
            return;
        }
        log.info("处理消息");
        WsResponse wsResponse = handleDetail(wsRequestPacket, wsRequestPacket.getBody(), wsRequestPacket.getWsOpcode(), channelContext);

        if (wsResponse != null) {
            Aio.send(channelContext, wsResponse);
        }
    }

    private WsResponse handleDetail(WsRequest websocketPacket, byte[] bytes, Opcode opcode, ChannelContext channelContext) throws Exception {
        log.info("LayimServerAioHandler.handleDetail");
        WsResponse wsResponse;
        if (opcode == Opcode.TEXT) {
            if (bytes == null || bytes.length == 0) {
                Aio.remove(channelContext, "错误的websocket包，body为空");
                return null;
            }
            //接收过来的json数据
            String text = ByteUtil.toText(bytes);
            //解析数据消息
            log.info("LayimServerAioHandler:解析消息类型");
            LayimMsgProperty property = Json.toBean(text,LayimMsgProperty.class);
            //获取到消息类型
            byte type = property.getMtype();
            log.info("LayimServerAioHandler:或得到的消息类型为：{}",type);
            //获取消息处理器
            LayimAbsMsgProcessor processor = LayimMsgProcessorManager.getProcessor(type);
            log.info("LayimServerAioHandler:或得到的消息处理器为：{}",processor.getClass().getName());
            boolean unknown = processor == null;
            if(!unknown) {
                processor.process(websocketPacket, channelContext);
            }
            //这里应该增加未知消息处理
            wsMsgHandler.onText(websocketPacket, text, channelContext);
            Object retObj ;
            String methodName = "onText";
            /**
             * 此处不采用 onText返回的消息
             * */
            if(unknown){
                retObj = new LayimResponseBody("unknown");
            }else{
                retObj = new LayimResponseBody("ok");
            }
            wsResponse = processRetObj(Json.toJson(retObj), methodName, channelContext);
            return wsResponse;
        } else if (opcode == Opcode.BINARY) {
            if (bytes == null || bytes.length == 0) {
                Aio.remove(channelContext, "错误的websocket包，body为空");
                return null;
            }
            Object retObj = wsMsgHandler.onBytes(websocketPacket, bytes, channelContext);
            String methodName = "onBytes";
            wsResponse = processRetObj(retObj, methodName, channelContext);
            return wsResponse;
        } else if (opcode == Opcode.PING || opcode == Opcode.PONG) {
            return null;
        } else if (opcode == Opcode.CLOSE) {
            Object retObj = wsMsgHandler.onClose(websocketPacket, bytes, channelContext);
            String methodName = "onClose";
            wsResponse = processRetObj(retObj, methodName, channelContext);
            return wsResponse;
        } else {
            Aio.remove(channelContext, "错误的websocket包，错误的Opcode");
            return null;
        }
    }

    private WsResponse processRetObj(Object obj, String methodName, ChannelContext channelContext) throws Exception {
        WsResponse wsResponse = null;
        if (obj == null) {
            return null;
        } else {
            if (obj instanceof String) {
                String str = (String) obj;
                wsResponse = WsResponse.fromText(str, layimServerConfig.getCharset());
                return wsResponse;
            } else if (obj instanceof byte[]) {
                wsResponse = WsResponse.fromBytes((byte[])obj);
                return wsResponse;
            } else if (obj instanceof WsResponse) {
                return (WsResponse) obj;
            } else if (obj instanceof ByteBuffer) {
                byte[] bs = ((ByteBuffer) obj).array();
                wsResponse = WsResponse.fromBytes(bs);
                return wsResponse;
            } else {
                log.error("{} {}.{}()方法，只允许返回byte[]、ByteBuffer、WsResponse或null，但是程序返回了{}", channelContext, this.getClass().getName(), methodName, obj.getClass().getName());
                return null;
            }
        }
    }

    /**

     * @param httpConfig the httpConfig to set

     */
    public void setHttpConfig(LayimServerConfig httpConfig) {
        this.layimServerConfig = httpConfig;
    }

}