package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Dict;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class DictDTO {

    private String id;
    private String name;
    private String type;
    private String createName;
    private Instant createDate;
    private String updateName;
    private Instant updateDate;
    private List<DictTypeDTO> children;

    public DictDTO() {}

    public DictDTO(Dict dict) {
        this.id = dict.getId() + "";
        this.name = dict.getName();
        this.type = dict.getType();
        this.createName = dict.getCreatedBy();
        this.createDate = dict.getCreatedDate();
        this.updateName = dict.getLastModifiedBy();
        this.updateDate = dict.getLastModifiedDate();
        if (!dict.getDictTypes().isEmpty()) {
            this.children = dict.getDictTypes().stream().sorted().map(DictTypeDTO::new).collect(Collectors.toList());
        }
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<DictTypeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<DictTypeDTO> children) {
        this.children = children;
    }
}
