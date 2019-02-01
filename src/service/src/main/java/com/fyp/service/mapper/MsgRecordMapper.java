package com.fyp.service.mapper;

import com.fyp.entity.MsgRecord;

import java.util.List;

public interface MsgRecordMapper {
    void addRecord(MsgRecord record);

    List<MsgRecord> getList(Long roomId,long lastId);
}
