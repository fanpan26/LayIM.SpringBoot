package com.fyp.layim.domain.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author fyp
 * @crate 2017/11/1 19:58
 * @project SpringBootLayIM
 */
@MappedSuperclass
public class DomainBase {

    public DomainBase(){
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
    @GeneratedValue
    private Long id;
    @Column(name = "create_at")

    private Long createAt;

}
