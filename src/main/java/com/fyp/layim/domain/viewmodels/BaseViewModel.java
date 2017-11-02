package com.fyp.layim.domain.viewmodels;

/**
 * @author fyp
 * @crate 2017/11/2 23:00
 * @project SpringBootLayIM
 */
public class BaseViewModel {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
}
