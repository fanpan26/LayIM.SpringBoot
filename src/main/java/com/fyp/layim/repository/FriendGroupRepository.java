package com.fyp.layim.repository;

import com.fyp.layim.domain.FriendGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.transaction.RollbackException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/1 23:09
 * @project SpringBootLayIM
 */
public interface FriendGroupRepository extends JpaRepository<FriendGroup,Long> {
    /**
     * 获取群组个数
     * */
    Integer countByName(String groupName);

    /**
     * 我了个擦，这里不是写sql语句，否则会报告：QuerySyntaxException is not mapped
     * 不是表名，是映射的实体名
     * 必须添加  @Transactional  否则：nested exception is javax.persistence.TransactionRequiredException: Executing an update/delete query] with root cause
     * 删除一个好友分组
     * */
    @Transactional
    @Modifying
    @Query(value = "delete from FriendGroup where id=?1 and uid =?2")
    void deleteByIdAndUserId(Long id,Long uid);

    /**
     * 查询某个用户是否互为好友
     * */
    @Query(value = "select count(1) from user_friend_group A where group_id in (select id from friend_group where uid=?1) and A.uid=?2",nativeQuery = true)
    Integer countByUidInGroup(long userId,long friendId);

    /**
     * 获取用户的第一个好友分组id
     * */
    @Query(value = "SELECT  * FROM friend_group where uid=?1 order by create_at asc limit 0,1",nativeQuery = true)
    FriendGroup getFirstByUserId(long userId);

    /**
     * 根据好友分组ID获取好友分组
     * */
    @Query(value = "SELECT  * FROM friend_group where id=?1",nativeQuery = true)
    FriendGroup getFirstById(long id);

    /**
     * 获取当前用户的好友分组
     * */
    @Query(value = "SELECT * FROM friend_group where uid=?1",nativeQuery = true)
    List<FriendGroup> getAllByUserId(long userId);
}
