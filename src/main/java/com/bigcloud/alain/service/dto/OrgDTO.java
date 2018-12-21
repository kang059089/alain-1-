package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Org;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class OrgDTO {

    private String id;
    private String pid;
    private String pname;
    private String title;
    private String code;
    private String dictOrgTypeId;
    private DictTypeDTO dictOrgType;
    private String telephone;
    private String fax;
    private String address;
    private Double longitude;
    private Double latitude;
    private String description;
    private Integer sort;
    private String createName;
    private Instant createDate;
    private String updateName;
    private Instant updateDate;
    private List<OrgDTO> children;

    public OrgDTO() {
    }

    public OrgDTO(Org org) {
        this.id = org.getId() + "";
        if (null != org.getParent()) {
            this.pid = org.getParent().getId() + "";
            this.pname = org.getParent().getName();
        }
        this.title = org.getName();
        this.code = org.getCode();
        this.dictOrgType = new DictTypeDTO(org.getType());
        this.dictOrgTypeId = this.dictOrgType.getId();
        this.telephone = org.getTelephone();
        this.fax = org.getFax();
        this.address = org.getAddress();
        this.latitude = org.getLatitude();
        this.longitude = org.getLongitude();
        this.description = org.getDescription();
        this.sort = org.getSort();
        this.createName = org.getCreatedBy();
        this.createDate = org.getCreatedDate();
        this.updateName = org.getLastModifiedBy();
        this.updateDate = org.getLastModifiedDate();
        if (!org.getOrgs().isEmpty()) {
            this.children = org.getOrgs().stream().sorted().map(OrgDTO::new).collect(Collectors.toList());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDictOrgTypeId() {
        return dictOrgTypeId;
    }

    public void setDictOrgTypeId(String dictOrgTypeId) {
        this.dictOrgTypeId = dictOrgTypeId;
    }

    public DictTypeDTO getDictOrgType() {
        return dictOrgType;
    }

    public void setDictOrgType(DictTypeDTO dictOrgType) {
        this.dictOrgType = dictOrgType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public List<OrgDTO> getChildren() {
        return children;
    }

    public void setChildren(List<OrgDTO> children) {
        this.children = children;
    }
}
