package com.fyp.im.processor;

import com.fyp.im.MsgType;
import com.fyp.utils.convert.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import java.util.Arrays;

public abstract class AbstractMsgProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMsgProcessor.class);
    private byte[] body;
    private static final int PREFIX_LENGTH = 5;

    public AbstractMsgProcessor() {

    }

    private void setBody(byte[] body) {
        if (body == null || body.length < 5) {
            throw new IllegalArgumentException("illegal msg body");
        }
        this.body = body;
    }

    /**
     * 消息处理入口
     * */
    public void process(ChannelContext channelContext, byte[] body) {
        if(logger.isDebugEnabled()){
            logger.debug("handle msg:{}",new String(body));
        }
        setBody(body);
        processInternal(channelContext);
    }

    protected abstract void processInternal(ChannelContext channelContext);

    /**
     * 获取接收人的ID
     */
    protected int getTargetId() {
        byte[] targetIdBytes = Arrays.copyOf(body, 4);
        return ConvertUtil.byteArrayToInt(targetIdBytes);
    }

    /**
     * 获取消息类型
     */
    protected MsgType getMsgType() {
        byte value = body[4];
        return MsgType.valueOf(value);
    }

    protected byte[] getBody() {
        byte[] contents = Arrays.copyOfRange(body, PREFIX_LENGTH, body.length);
        return contents;
    }
}
