package com.fyp.layim.repository;

import com.fyp.layim.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fyp
 * @crate 2017/12/6 19:41
 * @project SpringBootLayIM
 */
public interface ApplyRepository extends JpaRepository<Apply,Long> {
    /**
     * 获取有没有申请过该类型
     * */
    int countByToidAndTypeAndResult(long toId,int type,int result);

    /**
     * 获取未读消息条数
     * */
    int countByToidAndIsread(long userId,boolean isRead);
}
