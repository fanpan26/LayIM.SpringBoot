package com.fyp.service.mapper;

import com.fyp.entity.UserFriendGroup;

import java.util.List;

public interface UserFriendGroupMapper {
    List<UserFriendGroup> getUserIdsByGroupIds(List<Long> groupIds);
}
