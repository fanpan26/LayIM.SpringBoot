package com.fyp.layim.im.common.processor;

import com.fyp.layim.im.common.LayimMsgType;
import com.fyp.layim.im.common.intf.LayimAbsMsgProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fyp
 * @crate 2017/11/20 7:17
 * @project SpringBootLayIM
 */
public class LayimMsgProcessorManager {
    private static boolean isInit = false;
    private static Map<String,LayimAbsMsgProcessor<?>> processorMap = new HashMap<String, LayimAbsMsgProcessor<?>>();

    public static void init(){
        if(isInit){ return; }

        processorMap.put("CLIENT_TO_CLIENT",new ClientToClientMsgProcessor());
        //processorMap.put("CLIENT_TO_GROUP",new )

        isInit = true;
    }

    public static LayimAbsMsgProcessor getProcessor(byte type){
        String key = getKey(type);
        if(key == null){
            return null;
        }
        return processorMap.get(getKey(type));
    }

    private static String getKey(byte type){
        String key = null;
        switch (type) {
            case LayimMsgType.CLIENT_TO_CLIENT:
                key = "CLIENT_TO_CLIENT";
                break;
            case LayimMsgType.CLIENT_TO_GROUP:
                key = "CLIENT_TO_GROUP";
                break;
            default:
                break;
        }
        return key;
    }
}
