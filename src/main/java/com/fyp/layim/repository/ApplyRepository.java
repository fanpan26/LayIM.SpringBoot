package com.fyp.layim.repository;

import com.fyp.layim.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fyp
 * @crate 2017/12/6 19:41
 * @project SpringBootLayIM
 */
public interface ApplyRepository extends JpaRepository<Apply,Long> {
    int countByToidAndTypeAndResult(long toId,int type,int result);
}
