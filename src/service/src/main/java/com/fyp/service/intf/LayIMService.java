package com.fyp.service.intf;

import com.fyp.entity.MsgRecord;
import com.fyp.entity.result.JsonResult;

import java.util.List;

public interface LayIMService {
    JsonResult GetInitResult(Long userId);

    JsonResult GetMembersByGroupId(Long groupId);

    List<Long> getGroupIds(Long userId);

    JsonResult addRecord(MsgRecord record);
}
