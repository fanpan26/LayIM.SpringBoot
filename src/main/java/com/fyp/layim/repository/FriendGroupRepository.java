package com.fyp.layim.repository;

import com.fyp.layim.domain.FriendGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fyp
 * @crate 2017/11/1 23:09
 * @project SpringBootLayIM
 */
public interface FriendGroupRepository extends JpaRepository<FriendGroup,Long> {
}
