package com.fyp.service.impl;

import com.fyp.entity.BigGroup;
import com.fyp.entity.FriendGroup;
import com.fyp.entity.User;
import com.fyp.entity.UserFriendGroup;
import com.fyp.entity.result.GroupMemberResult;
import com.fyp.entity.result.InitResult;
import com.fyp.entity.result.JsonResult;
import com.fyp.service.intf.LayIMService;
import com.fyp.service.mapper.*;
import com.fyp.service.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultLayIMService implements LayIMService {

    public JsonResult GetInitResult(Long userId) {

        SqlSession session = MyBatisUtil.getSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            FriendGroupMapper friendGroupMapper = session.getMapper(FriendGroupMapper.class);
            UserFriendGroupMapper userFriendGroupMapper = session.getMapper(UserFriendGroupMapper.class);
            BigGroupMapper bigGroupMapper = session.getMapper(BigGroupMapper.class);
            UserBigGroupMapper userBigGroupMapper = session.getMapper(UserBigGroupMapper.class);

            InitResult initResult = new InitResult();
            initResult.setMine(userMapper.getUser(userId));
            List<FriendGroup> friendGroups = friendGroupMapper.getGroupsByUserId(userId);
            if (!friendGroups.isEmpty()) {
                List<Long> friendGroupIds = friendGroups
                        .stream()
                        .map(x -> x.getId())
                        .collect(Collectors.toList());

                List<UserFriendGroup> userFriendGroups = userFriendGroupMapper.getUserIdsByGroupIds(friendGroupIds);
                //根据所有的用户ID查询出用户信息
                if (!userFriendGroups.isEmpty()) {
                    List<Long> friendIds = userFriendGroups
                            .stream()
                            .map(x -> x.getUserId())
                            .collect(Collectors.toList());
                    List<User> friends = userMapper.getUsersByIds(friendIds);

                    friendGroups.stream().forEach(x -> {
                        List<Long> userIdInGroup = userFriendGroups.stream()
                                .filter(u -> u.getGroupId() == x.getId())
                                .map(m -> m.getUserId())
                                .collect(Collectors.toList());

                        x.setList(friends.stream()
                                .filter(f -> userIdInGroup.contains(f.getId()))
                                .collect(Collectors.toList()));
                    });
                }

                initResult.setFriend(friendGroups);
            }
            List<Long> myGroupIds = userBigGroupMapper.getGroupsByUserId(userId);
            List<BigGroup> myGroups = bigGroupMapper.getGroupsByGroupIds(myGroupIds);
            initResult.setGroup(myGroups);
            return JsonResult.success(initResult);
        } finally {
            session.close();
        }
    }

    @Override
    public JsonResult GetMembersByGroupId(Long groupId) {
        GroupMemberResult result = new GroupMemberResult();
        if (groupId <= 0) {
            return JsonResult.success(result);
        }
        SqlSession session = MyBatisUtil.getSession();
        try {
            BigGroupMapper bigGroupMapper = session.getMapper(BigGroupMapper.class);
            UserBigGroupMapper userBigGroupMapper = session.getMapper(UserBigGroupMapper.class);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            Long userId = bigGroupMapper.getGroupOwnerId(groupId);
            result.setOwner(userMapper.getUser(userId));

            List<Long> memberIds = userBigGroupMapper.getGroupMembers(groupId);
            List<User> users = userMapper.getUsersByIds(memberIds);
            result.setList(users);
            return JsonResult.success(result);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Long> getGroupIds(Long userId) {
        SqlSession session = MyBatisUtil.getSession();
        try {
            UserBigGroupMapper userBigGroupMapper = session.getMapper(UserBigGroupMapper.class);
            return userBigGroupMapper.getGroupsByUserId(userId);
        } finally {
            session.close();
        }
    }
}
