package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author fyp
 * @crate 2017/11/1 22:55
 * @project SpringBootLayIM
 * 好友分组
 */
@Entity
public class FriendGroup extends DomainBase {
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Long uid;

    @Column(length = 20)
    private String name;
}
