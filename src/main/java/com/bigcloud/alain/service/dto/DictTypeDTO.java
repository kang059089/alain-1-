package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.DictType;

import java.time.Instant;

public class DictTypeDTO {

    private String id;
    private String label;
    private String value;
    private Integer sort;
    private String dictPid;
    private String createName;
    private Instant createDate;
    private String updateName;
    private Instant updateDate;

    public DictTypeDTO() {}

    public DictTypeDTO(DictType dictType) {
        this.id = dictType.getId() + "";
        if (null != dictType.getDictParent()) this.dictPid = dictType.getDictParent().getId() + "";
        this.label = dictType.getLabel();
        this.value = dictType.getValue();
        this.sort = dictType.getSort();
        this.createName = dictType.getCreatedBy();
        this.createDate = dictType.getCreatedDate();
        this.updateName = dictType.getLastModifiedBy();
        this.updateDate = dictType.getLastModifiedDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDictPid() {
        return dictPid;
    }

    public void setDictPid(String dictPid) {
        this.dictPid = dictPid;
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
