package com.bigcloud.alain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Role.
 */
@Entity
@Table(name = "bs_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "acl")
    private String acl;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Role parent;

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

    public Role name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcl() {
        return acl;
    }

    public Role acl(String acl) {
        this.acl = acl;
        return this;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Role roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Role addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public Role removeRole(Role role) {
        this.roles.remove(role);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Role getParent() {
        return parent;
    }

    public Role parent(Role role) {
        this.parent = role;
        return this;
    }

    public void setParent(Role role) {
        this.parent = role;
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
        Role role = (Role) o;
        if (role.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", acl='" + getAcl() + "'" +
            "}";
    }
}
