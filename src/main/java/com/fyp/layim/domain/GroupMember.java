package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.*;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/5 18:50
 * @project SpringBootLayIM
 */
@Entity
public class GroupMember extends DomainBase{
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigGroup getGroup() {
        return group;
    }

    public void setGroup(BigGroup group) {
        this.group = group;
    }

    @OneToOne
    @JoinColumn(name="group_id")
    private BigGroup group;
}
