package com.bigcloud.alain.domain;

import com.bigcloud.alain.service.dto.MenuDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Info implements Serializable {

    private App app;
    private List<MenuDTO> menuList;
    private Set<String> acls;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public List<MenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuDTO> menuList) {
        this.menuList = menuList;
    }

    public Set<String> getAcls() {
        return acls;
    }

    public void setAcls(Set<String> acls) {
        this.acls = acls;
    }

    @Override
    public String toString() {
        return "Info{" +
            "app=" + app +
            ", menuList=" + menuList +
            ", acls=" + acls +
            '}';
    }
}
