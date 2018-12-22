package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Role;

import java.util.stream.Collectors;

public class RoleTreeDTO extends BaseTreeDTO {

    public RoleTreeDTO(Role role) {
        this.key = role.getId() + "";
        this.acl = role.getAcl();
        if (null != role.getParent()) this.pid = role.getParent().getId() + "";
        this.title = role.getName();
        this.expanded = true; // 树结构默认展开
        if (!role.getRoles().isEmpty()) {
            this.children = role.getRoles().stream().map(RoleTreeDTO::new).collect(Collectors.toList());
        } else {
            this.isLeaf = true;
        }
    }
}
