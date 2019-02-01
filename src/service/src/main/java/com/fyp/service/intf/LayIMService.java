package com.fyp.service.intf;

import com.fyp.entity.MsgRecord;
import com.fyp.entity.User;
import com.fyp.entity.result.JsonResult;

import java.util.List;

public interface LayIMService {
    User getByUserId(Long userId);

    JsonResult getInitResult(Long userId);

    JsonResult getMembersByGroupId(Long groupId);

    List<Long> getGroupIds(Long userId);

    JsonResult addRecord(MsgRecord record);

    JsonResult getChatRecords(Long userId,Long id,String type,Long lastId);
}
