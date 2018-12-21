package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Org;

import java.util.stream.Collectors;

public class OrgTreeDTO extends BaseTreeDTO {

    public OrgTreeDTO(Org org) {
        this.key = org.getId() + "";
        if (null != org.getParent()) this.pid = org.getParent().getId() + "";
        this.title = org.getName();
        this.expanded = true; // 树结构默认展开
        if (!org.getOrgs().isEmpty()) {
            this.children = org.getOrgs().stream().map(OrgTreeDTO::new).collect(Collectors.toList());
        } else {
            this.isLeaf = true;
        }
    }
}
