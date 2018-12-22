package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Button;

import java.time.Instant;

public class ButtonDTO {

    private String id;
    private String menuPid;
    private String name;
    private String acl;
    private Integer sort;
    private String description;
    private String createName;
    private Instant createDate;
    private String updateName;
    private Instant updateDate;

    public ButtonDTO() {}

    public ButtonDTO(Button button) {
        this.id = button.getId() + "";
        if (null != button.getMenuParent()) this.menuPid = button.getMenuParent().getId() + "";
        this.name = button.getName();
        this.acl = button.getAcl();
        this.sort = button.getSort();
        this.description = button.getDescription();
        this.createName = button.getCreatedBy();
        this.createDate = button.getCreatedDate();
        this.updateName = button.getLastModifiedBy();
        this.updateDate = button.getLastModifiedDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuPid() {
        return menuPid;
    }

    public void setMenuPid(String menuPid) {
        this.menuPid = menuPid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }
}
