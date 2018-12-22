package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Button;
import com.bigcloud.alain.domain.Menu;

import java.util.stream.Collectors;

public class AclTreeDTO extends MenuTreeDTO {

    // 前台角色管理中的权限树中key需要唯一性，但构造函数中的menuId和buttonId存在相同的情况，
    // 所以增加id属性，而key为其中的acl属性
    private String id;
    private Boolean isBtn;
    private Integer sort;

    public AclTreeDTO() {

    }

    public AclTreeDTO(Menu menu) {
        this.id = menu.getId() + "";
        this.key = menu.getAcl();
        if (null != menu.getParent()) this.pid = menu.getParent().getId() + "";
        this.title = menu.getName();
        this.acl = menu.getAcl();
        this.expanded = true; // 树结构默认展开
        this.isLeaf = null;
        if (!menu.getMenus().isEmpty()) {
            this.children = menu.getMenus().stream().sorted().map(AclTreeDTO::new).collect(Collectors.toList());
        } else {
            if (!menu.getButtons().isEmpty()) {
                this.isBtn = true; // 表示包含按钮节点
                this.children = menu.getButtons().stream().sorted().map(AclTreeDTO::new).collect(Collectors.toList());
            } else {
                this.isLeaf = true;
            }
        }
    }

    public AclTreeDTO(Button button) {
        this.id = button.getId() + "";
        this.key = button.getAcl();
        if (null != button.getMenuParent()) this.pid = button.getMenuParent().getId() + "";
        this.title = button.getName();
        this.acl = button.getAcl();
        this.sort = button.getSort();
        this.isLeaf = true;
        this.isBtn = true; // 表示按钮节点
        this.description = button.getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getBtn() {
        return isBtn;
    }

    public void setBtn(Boolean btn) {
        isBtn = btn;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
