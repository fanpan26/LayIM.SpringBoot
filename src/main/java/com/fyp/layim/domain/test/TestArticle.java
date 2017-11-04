package com.fyp.layim.domain.test;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * @author fyp
 * @crate 2017/11/4 10:33
 * @project SpringBootLayIM
 */
@Entity
@Table
public class TestArticle {
    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TestUser getUser() {
        return user;
    }

    public void setUser(TestUser user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreate() {
        return createat;
    }

    public void setCreateAt(long create) {
        this.createat = createat;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private TestUser user;

    private String title;
    private long createat;
}
