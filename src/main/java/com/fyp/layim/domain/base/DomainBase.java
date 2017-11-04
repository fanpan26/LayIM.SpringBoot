package com.fyp.layim.domain.base;

import com.fyp.layim.common.util.IdUtil;

import javax.persistence.*;

/**
 * @author fyp
 * @crate 2017/11/1 19:58
 * @project SpringBootLayIM
 */
@MappedSuperclass
public abstract class DomainBase {

    public DomainBase(){
        id = IdUtil.nextUserId();
        createAt =  System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    @Id
    @Column(nullable = false)
    private Long id;
    @Column(name = "create_at")

    private Long createAt;

}
