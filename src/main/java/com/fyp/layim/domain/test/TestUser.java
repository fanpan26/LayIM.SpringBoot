package com.fyp.layim.domain.test;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/4 10:29
 * @project SpringBootLayIM
 */
@Entity
@Table
public class TestUser {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private Integer age;

    public List<TestArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<TestArticle> articles) {
        this.articles = articles;
    }

    //spring.jpa.open-in-view=true (这个配置也可以解决懒加载导致 no Session的问题)
    //could not initialize proxy - no Session (如果lazy是true的话会出现这个问题)
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<TestArticle> articles;
}
