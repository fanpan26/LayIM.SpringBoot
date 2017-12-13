package com.fyp.layim.repository;

import com.fyp.layim.domain.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/12/6 19:41
 * @project SpringBootLayIM
 */
public interface ApplyRepository extends JpaRepository<Apply,Long> {
    /**
     * 获取有没有申请过该类型
     * */
    int countByToidAndUidAndTypeAndResult(long toId,long uid,int type,int result);

    /**
     * 获取未读消息条数
     * */
    int countByToidAndIsread(long userId,boolean isRead);

    /**
     * 分页获取系统消息
     * */
    Page<Apply> findAppliesByToidOrderByCreateAtDesc(long toId, Pageable pageable);

    /**
     * 将某个用户的消息设置为已读
     * */
    @Transactional
    @Query(value = "update apply set isread=1 where toid=?1",nativeQuery = true)
    @Modifying
    void updateUnreadToRead(long uid);

    /**
     * 更新申请状态
     * */
    @Query(value = "update apply set result=?2 where id=?1",nativeQuery = true)
    @Modifying
    void updateResult(long id,int status);
}
