package com.fyp.layim.repository;

import com.fyp.layim.domain.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
    int countByToidAndTypeAndResult(long toId,int type,int result);

    /**
     * 获取未读消息条数
     * */
    int countByToidAndIsread(long userId,boolean isRead);

    /**
     * 分页获取系统消息
     * */
    Page<Apply> findAppliesByToidOrderByCreateAtDesc(long toId, Pageable pageable);
}
