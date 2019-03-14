package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Role;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class RoleDTO {

    private String id;
    private String name;
    private String pid;
    private String acl;
    private Integer sort;
    private List<RoleDTO> children;
    private String createName;
    private Instant createDate;
    private String updateName;
    private Instant updateDate;

    public RoleDTO() {}

    public RoleDTO(Role role) {
        this.id = role.getId() + "";
        this.name = role.getName();
        this.acl = role.getAcl();
        this.sort = role.getSort();
        if (null != role.getParent()) {
            this.pid = role.getParent().getId() + "";
        }
        if (!role.getRoles().isEmpty()) {
            this.children = role.getRoles().stream().map(RoleDTO::new).collect(Collectors.toList());
        }
        this.createName = role.getCreatedBy();
        this.createDate = role.getCreatedDate();
        this.updateName = role.getLastModifiedBy();
        this.updateDate = role.getLastModifiedDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<RoleDTO> getChildren() {
        return children;
    }

    public void setChildren(List<RoleDTO> children) {
        this.children = children;
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
