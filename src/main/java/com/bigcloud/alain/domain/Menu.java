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
 * A Menu.
 */
@Entity
@Table(name = "bs_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Menu extends AbstractAuditingEntity implements Serializable,Comparable<Menu> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "i18n")
    private String i18n;

    @Column(name = "jhi_group")
    private Boolean group;

    @Column(name = "jhi_link")
    private String link;

    @Column(name = "link_exact")
    private Boolean linkExact;

    @Column(name = "external_link")
    private String externalLink;

    @Column(name = "target")
    private String target;

    @Column(name = "icon")
    private String icon;

    @Column(name = "badge")
    private Integer badge;

    @Column(name = "badge_dot")
    private Boolean badgeDot;

    @Column(name = "badge_status")
    private String badgeStatus;

    @Column(name = "hide")
    private Boolean hide;

    @Column(name = "hide_in_breadcrumb")
    private Boolean hideInBreadcrumb;

    @Column(name = "acl")
    private String acl;

    @Column(name = "shortcut")
    private Boolean shortcut;

    @Column(name = "shortcu_root")
    private Boolean shortcuRoot;

    @Column(name = "jhi_reuse")
    private Boolean reuse;

    @Column(name = "jhi_sort")
    private Integer sort;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
    private Set<Menu> menus = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "menuParent")
    private Set<Button> buttons = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Menu parent;

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

    public Menu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geti18n() {
        return i18n;
    }

    public Menu i18n(String i18n) {
        this.i18n = i18n;
        return this;
    }

    public void seti18n(String i18n) {
        this.i18n = i18n;
    }

    public Boolean isGroup() {
        return group;
    }

    public Menu group(Boolean group) {
        this.group = group;
        return this;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public String getLink() {
        return link;
    }

    public Menu link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean isLinkExact() {
        return linkExact;
    }

    public Menu linkExact(Boolean linkExact) {
        this.linkExact = linkExact;
        return this;
    }

    public void setLinkExact(Boolean linkExact) {
        this.linkExact = linkExact;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public Menu externalLink(String externalLink) {
        this.externalLink = externalLink;
        return this;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getTarget() {
        return target;
    }

    public Menu target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public Menu icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getBadge() {
        return badge;
    }

    public Menu badge(Integer badge) {
        this.badge = badge;
        return this;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public Boolean isBadgeDot() {
        return badgeDot;
    }

    public Menu badgeDot(Boolean badgeDot) {
        this.badgeDot = badgeDot;
        return this;
    }

    public void setBadgeDot(Boolean badgeDot) {
        this.badgeDot = badgeDot;
    }

    public String getBadgeStatus() {
        return badgeStatus;
    }

    public Menu badgeStatus(String badgeStatus) {
        this.badgeStatus = badgeStatus;
        return this;
    }

    public void setBadgeStatus(String badgeStatus) {
        this.badgeStatus = badgeStatus;
    }

    public Boolean isHide() {
        return hide;
    }

    public Menu hide(Boolean hide) {
        this.hide = hide;
        return this;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean isHideInBreadcrumb() {
        return hideInBreadcrumb;
    }

    public Menu hideInBreadcrumb(Boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
        return this;
    }

    public void setHideInBreadcrumb(Boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
    }

    public String getAcl() {
        return acl;
    }

    public Menu acl(String acl) {
        this.acl = acl;
        return this;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public Boolean isShortcut() {
        return shortcut;
    }

    public Menu shortcut(Boolean shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public void setShortcut(Boolean shortcut) {
        this.shortcut = shortcut;
    }

    public Boolean isShortcuRoot() {
        return shortcuRoot;
    }

    public Menu shortcuRoot(Boolean shortcuRoot) {
        this.shortcuRoot = shortcuRoot;
        return this;
    }

    public void setShortcuRoot(Boolean shortcuRoot) {
        this.shortcuRoot = shortcuRoot;
    }

    public Boolean isReuse() {
        return reuse;
    }

    public Menu reuse(Boolean reuse) {
        this.reuse = reuse;
        return this;
    }

    public void setReuse(Boolean reuse) {
        this.reuse = reuse;
    }

    public Integer getSort() {
        return sort;
    }

    public Menu sort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Menu menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Menu addMenu(Menu menu) {
        this.menus.add(menu);
        return this;
    }

    public Menu removeMenu(Menu menu) {
        this.menus.remove(menu);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Button> getButtons() {
        return buttons;
    }

    public Menu buttons(Set<Button> buttons) {
        this.buttons = buttons;
        return this;
    }

    public Menu addButton(Button button) {
        this.buttons.add(button);
        return this;
    }

    public Menu removeButton(Button button) {
        this.buttons.remove(button);
        return this;
    }

    public void setButtons(Set<Button> buttons) {
        this.buttons = buttons;
    }

    public Menu getParent() {
        return parent;
    }

    public Menu parent(Menu menu) {
        this.parent = menu;
        return this;
    }

    public void setParent(Menu menu) {
        this.parent = menu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public int compareTo(Menu menu) {
        return sort.compareTo(menu.getSort());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        if (menu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", i18n='" + geti18n() + "'" +
            ", group='" + isGroup() + "'" +
            ", link='" + getLink() + "'" +
            ", linkExact='" + isLinkExact() + "'" +
            ", externalLink='" + getExternalLink() + "'" +
            ", target='" + getTarget() + "'" +
            ", icon='" + getIcon() + "'" +
            ", badge=" + getBadge() +
            ", badgeDot='" + isBadgeDot() + "'" +
            ", badgeStatus='" + getBadgeStatus() + "'" +
            ", hide='" + isHide() + "'" +
            ", hideInBreadcrumb='" + isHideInBreadcrumb() + "'" +
            ", acl='" + getAcl() + "'" +
            ", shortcut='" + isShortcut() + "'" +
            ", shortcuRoot='" + isShortcuRoot() + "'" +
            ", reuse='" + isReuse() + "'" +
            ", sort=" + getSort() +
            "}";
    }
}
