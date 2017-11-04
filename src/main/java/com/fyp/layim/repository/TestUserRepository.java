package com.fyp.layim.repository;

import com.fyp.layim.domain.test.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fyp
 * @crate 2017/11/4 10:42
 * @project SpringBootLayIM
 */
public interface TestUserRepository extends JpaRepository<TestUser,Long> {
}
