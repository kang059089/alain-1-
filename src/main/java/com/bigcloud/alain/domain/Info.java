package com.bigcloud.alain.domain;

import com.bigcloud.alain.service.dto.MenuDTO;

import java.io.Serializable;
import java.util.List;

public class Info implements Serializable {

    private App app;
    private List<MenuDTO> menuList;

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
}
