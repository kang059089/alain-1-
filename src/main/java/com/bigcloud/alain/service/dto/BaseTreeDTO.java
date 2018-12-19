package com.bigcloud.alain.service.dto;

import java.util.List;

public class BaseTreeDTO {

    public String key; // ngalain前端格式，节点id
    public String pid; // 父节点id
    public String title; // 节点名称
    public String acl; // 权限控制
    public String isLeaf; // 是否是叶子节点（true：叶子节点；false：非叶子节点）
    public String expanded; // 是否展开树结构（true：展开；false：不展开）
    public String description; // 描述
    public List<BaseTreeDTO> children; // 子节点集合

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getExpanded() {
        return expanded;
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BaseTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<BaseTreeDTO> children) {
        this.children = children;
    }
}
