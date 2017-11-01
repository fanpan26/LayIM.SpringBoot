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
    private Long uid;

    @Column(length = 20)
    private String name;
}
