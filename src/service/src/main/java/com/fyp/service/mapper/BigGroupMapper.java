package com.fyp.service.mapper;

import com.fyp.entity.BigGroup;

import java.util.List;

public interface BigGroupMapper {
    List<BigGroup> getGroupsByUserId(Long userId);
}
