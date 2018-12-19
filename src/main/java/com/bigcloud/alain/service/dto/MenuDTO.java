package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Menu;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class MenuDTO {

    private String id;
    private String pid;
    private String pname;
    private String text;
    private String i18n;
    private Boolean group;
    private String link;
    private Boolean linkExact;
    private String externalLink;
    private String target;
    private IconDTO icon;
    private String acl;
    private Integer badge;
    private Boolean badgeDot;
    private String badgeStatus;
    private Boolean hide;
    private Boolean hideInBreadcrumb;
    private Boolean shortcut;
    private Boolean shortcuRoot;
    private Boolean reuse;
    private Integer sort;
    private String createName;
    private Instant createDate;
    private String updateName;
    private Instant updateDate;
    private List<MenuDTO> children;

    public MenuDTO() {
    }

    public MenuDTO(Menu menu) {
        this.id = menu.getId() + "";
        if (null != menu.getParent()) {
            this.pid = menu.getParent().getId() + "";
            this.pname = menu.getParent().getName();
        }
        this.text = menu.getName();
        this.i18n = menu.geti18n();
        this.group = menu.isGroup();
        this.link = menu.getLink();
        this.linkExact = menu.isLinkExact();
        this.externalLink = menu.getExternalLink();
        this.target = menu.getTarget();
        this.icon = new IconDTO("icon", menu.getIcon(), "outline");
        this.acl = menu.getAcl();
        this.badge = menu.getBadge();
        this.badgeDot = menu.isBadgeDot();
        this.badgeStatus = menu.getBadgeStatus();
        this.hide = menu.isHide();
        this.hideInBreadcrumb = menu.isHideInBreadcrumb();
        this.shortcut = menu.isShortcut();
        this.shortcuRoot = menu.isShortcuRoot();
        this.reuse = menu.isReuse();
        this.sort = menu.getSort();
        this.createName = menu.getCreatedBy();
        this.createDate = menu.getCreatedDate();
        this.updateName = menu.getLastModifiedBy();
        this.updateDate = menu.getLastModifiedDate();
        if (!menu.getMenus().isEmpty()) {
            this.children = menu.getMenus().stream().sorted().map(MenuDTO::new).collect(Collectors.toList());
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getLinkExact() {
        return linkExact;
    }

    public void setLinkExact(Boolean linkExact) {
        this.linkExact = linkExact;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public IconDTO getIcon() {
        return icon;
    }

    public void setIcon(IconDTO icon) {
        this.icon = icon;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public Boolean getBadgeDot() {
        return badgeDot;
    }

    public void setBadgeDot(Boolean badgeDot) {
        this.badgeDot = badgeDot;
    }

    public String getBadgeStatus() {
        return badgeStatus;
    }

    public void setBadgeStatus(String badgeStatus) {
        this.badgeStatus = badgeStatus;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean getHideInBreadcrumb() {
        return hideInBreadcrumb;
    }

    public void setHideInBreadcrumb(Boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
    }

    public Boolean getShortcut() {
        return shortcut;
    }

    public void setShortcut(Boolean shortcut) {
        this.shortcut = shortcut;
    }

    public Boolean getShortcuRoot() {
        return shortcuRoot;
    }

    public void setShortcuRoot(Boolean shortcuRoot) {
        this.shortcuRoot = shortcuRoot;
    }

    public Boolean getReuse() {
        return reuse;
    }

    public void setReuse(Boolean reuse) {
        this.reuse = reuse;
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

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }
}
