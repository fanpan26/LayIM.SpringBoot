package com.fyp.service.mapper;

import com.fyp.entity.FriendGroup;

import java.util.List;

public interface FriendGroupMapper {
    List<FriendGroup> getGroupsByUserId(Long userId);
}
