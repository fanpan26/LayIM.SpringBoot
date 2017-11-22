package com.fyp.layim.repository;

import com.fyp.layim.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository  extends JpaRepository<UserAccount,Long> {

    /**
     * 根据用户账号查询用户信息
     * */
    UserAccount findByUsername(String username);
}
