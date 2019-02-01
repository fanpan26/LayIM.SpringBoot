package com.fyp.im.processor;

import com.fyp.entity.MsgRecord;
import com.fyp.entity.User;
import com.fyp.entity.im.Message;
import com.fyp.entity.LayIMConstants;
import com.fyp.im.utils.BeanUtil;
import com.fyp.im.utils.SafeEncoder;
import com.fyp.service.intf.LayIMService;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import java.util.concurrent.Executors;

public abstract class MsgStoredProcessor extends AbstractMsgProcessor{

    private void recordMsgAsync(ChannelContext channelContext){
        Executors.newCachedThreadPool().execute(() -> recordMsg(channelContext));
    }

    protected final byte[] buildSendMessage(ChannelContext channelContext) {
        Message message = new Message();

        User user = (User) channelContext.getAttribute(LayIMConstants.CURRENT_USER_ATTRIBUTE);
        if(null == user){
            throw new NullPointerException("current user is null");
        }
        message.setUsername(user.getUsername());
        message.setAvatar(user.getAvatar());
        message.setContent(SafeEncoder.encode(getBody()));
        message.setType(getType(channelContext));
        message.setId(getId(channelContext));
        return SafeEncoder.encode(Json.toJson(message));
    }

    protected abstract long getId(ChannelContext channelContext);
    protected abstract String getType(ChannelContext channelContext);

    private void recordMsg(ChannelContext channelContext) {
        LayIMService service = ((LayIMService) BeanUtil.getBean("layIMService"));
        MsgRecord record = new MsgRecord();
        record.setTo(getTargetId());
        record.setFrom(Long.valueOf(channelContext.userid));
        record.setContents(SafeEncoder.encode(getBody()));
        record.setCreateAt(System.currentTimeMillis());
        record.setId(System.currentTimeMillis());
        record.setMsgType(getMsgType());
        service.addRecord(record);
    }

    protected abstract void processMessage(ChannelContext channelContext);

    protected void processInternal(ChannelContext channelContext) {
        recordMsgAsync(channelContext);
        processMessage(channelContext);
    }
}
