package com.bigcloud.alain.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Button.
 */
@Entity
@Table(name = "bs_button")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Button implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "acl")
    private String acl;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_sort")
    private Integer sort;

    @ManyToOne
    @JsonIgnoreProperties("")
    @JoinColumn(name = "menu_pid")
    private Menu menuParent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Button name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcl() {
        return acl;
    }

    public Button acl(String acl) {
        this.acl = acl;
        return this;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public String getDescription() {
        return description;
    }

    public Button description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public Button sort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Menu getMenuParent() {
        return menuParent;
    }

    public Button menuParent(Menu menu) {
        this.menuParent = menu;
        return this;
    }

    public void setMenuParent(Menu menu) {
        this.menuParent = menu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Button button = (Button) o;
        if (button.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), button.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Button{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", acl='" + getAcl() + "'" +
            ", description='" + getDescription() + "'" +
            ", sort=" + getSort() +
            "}";
    }
}
