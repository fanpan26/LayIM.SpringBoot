package com.fyp.layim.repository;

import com.fyp.layim.domain.User;
import com.fyp.layim.domain.result.JsonResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fyp
 * @crate 2017/11/1 23:06
 * @project SpringBootLayIM
 */
public interface UserRepository extends JpaRepository<User,Long> {

}
