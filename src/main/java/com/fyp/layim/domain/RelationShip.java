package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.Entity;

/**
 * @author fyp
 * @crate 2017/11/1 23:03
 * @project SpringBootLayIM
 */
@Entity
public class RelationShip extends DomainBase {
    private Long uid;
    private Long friendUid;
}
