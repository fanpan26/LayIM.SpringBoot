package com.fyp.service.mapper;

import com.fyp.entity.User;

import java.util.List;

public interface UserMapper {
    User getUser(Long id);
    List<User> getUsersByIds(List<Long> userIds);
}
