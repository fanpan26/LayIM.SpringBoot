package com.fyp.im.processor;

import com.fyp.entity.MsgRecord;
import com.fyp.im.utils.BeanUtil;
import com.fyp.service.intf.LayIMService;
import org.tio.core.ChannelContext;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;

public abstract class MsgStoredProcessor extends AbstractMsgProcessor{

    private void recordMsgAsync(ChannelContext channelContext){
        Executors.newCachedThreadPool().execute(() -> recordMsg(channelContext));
    }
    private void recordMsg(ChannelContext channelContext) {
        byte[] body = getBody();
        String msg;
        try {
            msg = new String(body, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return;
        }
        LayIMService service = ((LayIMService) BeanUtil.getBean("layIMService"));
        MsgRecord record = new MsgRecord();
        record.setTo(getTargetId());
        record.setFrom(Long.valueOf(channelContext.userid));
        record.setContents(msg);
        record.setCreateAt(System.currentTimeMillis());
        record.setId(System.currentTimeMillis());
        service.addRecord(record);
    }


    protected abstract void processMessage(ChannelContext channelContext);

    protected void processInternal(ChannelContext channelContext) {
        recordMsgAsync(channelContext);
        processMessage(channelContext);
    }
}
