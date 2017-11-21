package com.fyp.layim.common.util;

import com.fyp.layim.im.packet.ChatRequestBody;
import org.junit.Test;
import org.tio.utils.json.Json;

import static org.junit.Assert.*;

/**
 * @author fyp
 * @crate 2017/11/2 21:27
 * @project SpringBootLayIM
 */
public class IdUtilTest {

    @Test
    public void testNextUserId(){
        for (Integer i=0;i<10000;i++) {
            Long id = IdUtil.nextUserId();
            System.out.println(id);
        }
    }

    @Test
    public void testJson(){
        ChatRequestBody body = new ChatRequestBody();
        body = Json.toBean("{\"type\":1,\"content\":\"你好吗\"}",ChatRequestBody.class);

        System.out.println(body.getContent());
    }
}