package com.fyp.layim.repository;

import com.fyp.layim.domain.FriendGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.transaction.Transactional;

/**
 * @author fyp
 * @crate 2017/11/1 23:09
 * @project SpringBootLayIM
 */
public interface FriendGroupRepository extends JpaRepository<FriendGroup,Long> {
    Integer countByName(String groupName);

    /**
     * 我了个擦，这里不是写sql语句，否则会报告：QuerySyntaxException is not mapped
     * 不是表名，是映射的实体名
     * 必须添加  @Transactional  否则：nested exception is javax.persistence.TransactionRequiredException: Executing an update/delete query] with root cause
     * 删除一个好友分组
     * */
    @Modifying
    @Transactional
    @Query(value = "delete from FriendGroup where id=?1 and uid =?2")
    void deleteByIdAndUserId(Long id,Long uid);
}
