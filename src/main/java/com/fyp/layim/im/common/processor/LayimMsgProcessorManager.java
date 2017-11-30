package com.fyp.layim.im.common.processor;

import com.fyp.layim.im.common.intf.LayimMsgType;
import com.fyp.layim.im.common.intf.LayimAbsMsgProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger logger = LoggerFactory.getLogger(LayimMsgProcessorManager.class);

    public static void init(){
        if(isInit){ return; }

        processorMap.put("CLIENT_TO_CLIENT",new ClientToClientMsgProcessor());
        processorMap.put("CLIENT_TO_GROUP",new ClientToGroupMsgProcessor());
        processorMap.put("CLIENT_CHECK_ONLINE",new ClientCheckOnlineMsgProcessor());
        processorMap.put("CLIENT_CHECK_ONLINE_COUNT",new ClientCheckOnlineCountMsgProcessor());
        isInit = true;
    }

    public static LayimAbsMsgProcessor getProcessor(byte type){
        logger.info("LayimMsgProcessorManager.getProcessor");
        String key = getKey(type);
        if(key == null){
            return null;
        }
        return processorMap.get(getKey(type));
    }

    private static String getKey(byte type) {
        String key = null;
        switch (type) {
            case LayimMsgType.CLIENT_TO_CLIENT:
                key = "CLIENT_TO_CLIENT";
                break;
            case LayimMsgType.CLIENT_TO_GROUP:
                key = "CLIENT_TO_GROUP";
                break;
            case LayimMsgType.CLIENT_CHECK_ONLINE:
                key = "CLIENT_CHECK_ONLINE";
                break;
            case LayimMsgType.CLIENT_CHECK_ONLINE_COUNT:
                key = "CLIENT_CHECK_ONLINE_COUNT";
                break;
            default:
                break;
        }
        return key;
    }
}
