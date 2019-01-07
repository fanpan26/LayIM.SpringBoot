package com.fyp.service.mapper;


import com.fyp.entity.BigGroup;

import java.util.List;

public interface UserBigGroupMapper {
    List<Long> getGroupsByUserId(Long userId);
    List<Long> getGroupMembers(Long groupId);
}
