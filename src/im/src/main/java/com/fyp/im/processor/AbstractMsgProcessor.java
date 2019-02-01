package com.fyp.im.processor;

import com.fyp.utils.convert.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import java.util.Arrays;

public abstract class AbstractMsgProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMsgProcessor.class);
    private byte[] body;
    private static final int PREFIX_LENGTH = 5;
    private static final int BODY_LENGTH_LENGTH = 4;
    private static final int PLACEHOLDER_LENGTH = 9;
    private byte[] realBody;
    private int targetId;

    public AbstractMsgProcessor() {

    }

    private void setBody(byte[] body) {
        if (body == null || body.length < 9) {
            throw new IllegalArgumentException("illegal msg body");
        }
        this.body = body;
    }

    /**
     * 消息处理入口
     * */
    public final void process(ChannelContext channelContext, byte[] body) {
        setBody(body);
        processInternal(channelContext);
    }

    protected abstract void processInternal(ChannelContext channelContext);

    /**
     * 获取接收人的ID
     */
    protected final int getTargetId() {
        if (targetId == 0) {
            byte[] targetIdBytes = Arrays.copyOf(body, BODY_LENGTH_LENGTH);
            targetId = ConvertUtil.byteArrayToInt(targetIdBytes);
        }
        return targetId;
    }

    protected final int getBodyLength() {
        byte[] bodyLengthBytes =  Arrays.copyOfRange(body, PREFIX_LENGTH, PLACEHOLDER_LENGTH);
        return ConvertUtil.byteArrayToInt(bodyLengthBytes);
    }

    protected final byte getMsgType(){
        return body[4];
    }

    protected final byte[] getBody() {
        if (realBody == null) {
            int length = getBodyLength();
            int totalLength = PLACEHOLDER_LENGTH + length;
            if (body.length != totalLength) {
                throw new IllegalArgumentException("illegal body length");
            }
            realBody = Arrays.copyOfRange(body, PLACEHOLDER_LENGTH, totalLength);
        }
        return realBody;
    }
}
