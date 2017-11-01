package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author fyp
 * @crate 2017/11/1 19:55
 * @project SpringBootLayIM
 * 基本用户信息
 */
@Entity
public class User extends DomainBase{
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String avatar;
    @Column(name = "user_name")
    private String userName;
    private String sign;

}


