package com.fyp.service.impl;

import com.fyp.entity.FriendGroup;
import com.fyp.entity.User;
import com.fyp.entity.UserFriendGroup;
import com.fyp.entity.result.InitResult;
import com.fyp.entity.result.JsonResult;
import com.fyp.service.intf.LayIMService;
import com.fyp.service.mapper.BigGroupMapper;
import com.fyp.service.mapper.FriendGroupMapper;
import com.fyp.service.mapper.UserFriendGroupMapper;
import com.fyp.service.mapper.UserMapper;
import com.fyp.service.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultLayIMService implements LayIMService {

    public JsonResult GetInitResult(Long userId) {

        SqlSession session = MyBatisUtil.getSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            FriendGroupMapper friendGroupMapper = session.getMapper(FriendGroupMapper.class);
            UserFriendGroupMapper userFriendGroupMapper = session.getMapper(UserFriendGroupMapper.class);
            BigGroupMapper bigGroupMapper = session.getMapper(BigGroupMapper.class);

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
            initResult.setGroup(bigGroupMapper.getGroupsByUserId(userId));
            return JsonResult.success(initResult);
        } finally {
            session.close();
        }
    }
}
