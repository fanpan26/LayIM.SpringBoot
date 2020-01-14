package com.fyp.service.impl;

import com.fyp.entity.*;
import com.fyp.entity.result.ChatRecordResult;
import com.fyp.entity.result.GroupMemberResult;
import com.fyp.entity.result.InitResult;
import com.fyp.entity.result.JsonResult;
import com.fyp.service.intf.LayIMService;
import com.fyp.service.mapper.*;
import com.fyp.service.utils.MyBatisUtil;
import com.fyp.service.utils.Utils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.fyp.entity.LayIMConstants.*;

@Service
public class DefaultLayIMService implements LayIMService {

    public User getByUserId(Long userId) {
        SqlSession session = MyBatisUtil.getSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.getUser(userId);
        } finally {
            session.close();
        }
    }

    public JsonResult getInitResult(Long userId) {

        SqlSession session = MyBatisUtil.getSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            FriendGroupMapper friendGroupMapper = session.getMapper(FriendGroupMapper.class);

            BigGroupMapper bigGroupMapper = session.getMapper(BigGroupMapper.class);
            UserBigGroupMapper userBigGroupMapper = session.getMapper(UserBigGroupMapper.class);

            InitResult initResult = new InitResult();
            initResult.setMine(userMapper.getUser(userId));
            List<FriendGroup> friendGroups = friendGroupMapper.getGroupsByUserId(userId);
            if (!friendGroups.isEmpty()) {
                UserFriendGroupMapper userFriendGroupMapper = session.getMapper(UserFriendGroupMapper.class);
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
    public JsonResult getMembersByGroupId(Long groupId) {
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

    @Override
    public JsonResult addRecord(MsgRecord record) {
        if (record.getTo() <= 0) {
            return JsonResult.fail("无效的接收人");
        }
        if (record.getContents() == null || record.getContents().isEmpty()) {
            return JsonResult.fail("空的消息内容");
        }
        record.setCreateAt(System.currentTimeMillis());
        record.setRoom(Utils.generateRoomId(record.getFrom(), record.getTo(), record.getMsgType() == 2 ? CHAT_TYPE_GROUP : CHAT_TYPE_OTHER));
        SqlSession session = MyBatisUtil.getSession();
        try {
            MsgRecordMapper msgRecordMapper = session.getMapper(MsgRecordMapper.class);
            msgRecordMapper.addRecord(record);
            session.commit();
        } finally {
            session.close();
        }
        return JsonResult.success();
    }

    @Override
    public JsonResult getChatRecords(Long userId,Long id, String type, Long lastId) {
        if(!type.equals(CHAT_TYPE_FRIEND)&&!type.equals(CHAT_TYPE_GROUP)) {
            throw new IllegalArgumentException("type should be group or friend");
        }
        boolean isChatGroup = type.equals(CHAT_TYPE_GROUP);
        SqlSession session = MyBatisUtil.getSession();
        try {
            MsgRecordMapper msgRecordMapper = session.getMapper(MsgRecordMapper.class);
            List<ChatRecordResult> records = new ArrayList<>(20);
            List<MsgRecord> list = msgRecordMapper.getList(Utils.generateRoomId(userId,id,type),lastId);

            if(list.isEmpty()){
                return JsonResult.success(records);
            }
            List<Long> userIds;
            if(isChatGroup){
                userIds = list
                        .stream()
                        .map(x -> x.getFrom())
                        .distinct()
                        .collect(Collectors.toList());
            }else {
                userIds = new ArrayList<>(2);
                userIds.add(userId);
                userIds.add(id);
            }
            UserMapper userMapper = session.getMapper(UserMapper.class);
           List<User> users = userMapper.getUsersByIds(userIds);

            for (MsgRecord record : list) {
                ChatRecordResult res = new ChatRecordResult();
                res.setId(record.getId());

                Optional<User> user = users.stream().filter(x -> x.getId().equals(record.getFrom())).findFirst();
                res.setAvatar(user.orElse(User.Anonymous).getAvatar());
                res.setUsername(user.orElse(User.Anonymous).getUsername());

                res.setContent(record.getContents());
                res.setSelf(userId.equals(record.getFrom()));
                res.setTimestamp(record.getCreateAt());
                records.add(res);
            }
            Collections.sort(records);
            return JsonResult.success(records);
        } finally {
            session.close();
        }

    }
}
