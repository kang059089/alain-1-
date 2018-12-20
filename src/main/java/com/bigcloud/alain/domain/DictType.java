package com.bigcloud.alain.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DictType.
 */
@Entity
@Table(name = "bs_dict_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DictType extends AbstractAuditingEntity implements Serializable,Comparable<DictType> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String label;

    @Column(name = "code")
    private String value;

    @Column(name = "jhi_sort")
    private Integer sort;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dict_pid")
    private Dict dictParent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public DictType label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public DictType value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public DictType sort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Dict getDictParent() {
        return dictParent;
    }

    public DictType dictParent(Dict dict) {
        this.dictParent = dict;
        return this;
    }

    public void setDictParent(Dict dict) {
        this.dictParent = dict;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public int compareTo(DictType dictType) {
        return sort.compareTo(dictType.getSort());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DictType dictType = (DictType) o;
        if (dictType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dictType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DictType{" +
            "id=" + id +
            ", label='" + label + '\'' +
            ", value='" + value + '\'' +
            ", sort=" + sort +
            ", dictParent=" + dictParent +
            '}';
    }
}
