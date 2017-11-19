package com.fyp.layim.im.common;

import org.apache.commons.lang3.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpConst;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.websocket.common.util.BASE64Util;
import org.tio.websocket.common.util.SHA1Util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fyp
 * @crate 2017/11/19 21:31
 * @project SpringBootLayIM
 */
public final class Protocol {
    /**
    * 升级websocket
    * */
    public static HttpResponse updateToWebSocket(HttpRequest request, ChannelContext channelContext) {
        Map<String, String> headers = request.getHeaders();

        String Sec_WebSocket_Key = headers.get(HttpConst.RequestHeaderKey.Sec_WebSocket_Key);

        if (StringUtils.isNotBlank(Sec_WebSocket_Key)) {
            String Sec_WebSocket_Key_Magic = Sec_WebSocket_Key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
            byte[] key_array = SHA1Util.SHA1(Sec_WebSocket_Key_Magic);
            String acceptKey = BASE64Util.byteArrayToBase64(key_array);
            HttpResponse httpResponse = new HttpResponse(request, null);

            httpResponse.setStatus(HttpResponseStatus.C101);

            Map<String, String> respHeaders = new HashMap<String, String>(5);
            respHeaders.put(HttpConst.ResponseHeaderKey.Connection, HttpConst.ResponseHeaderValue.Connection.Upgrade);
            respHeaders.put(HttpConst.ResponseHeaderKey.Upgrade, "WebSocket");
            respHeaders.put(HttpConst.ResponseHeaderKey.Sec_WebSocket_Accept, acceptKey);
            httpResponse.setHeaders(respHeaders);
            return httpResponse;
        }
        return null;
    }
}
