package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.config.Constants;

import com.bigcloud.alain.domain.Authority;
import com.bigcloud.alain.domain.Org;
import com.bigcloud.alain.domain.Role;
import com.bigcloud.alain.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    private String rolePid;

    private String orgPid;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    private String password;

    @Size(max = 50)
    private String realName;

    @Size(max = 50)
    private String nickName;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private String telephone;

    @Size(max = 256)
    private String imageUrl;

    @Size(max = 255)
    private String introduce;

    @Size(max = 255)
    private String address;

    private boolean activated = false;

    @Size(min = 2, max = 6)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    private Set<Long> roles;

    private Set<Long> orgs;

    private Set<RoleDTO> roleDTOS;

    private Integer passwordState;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(String login, String email, String imageUrl, String lastName, Boolean activated) {
        this.login = login;
        this.email = email;
        this.imageUrl = imageUrl;
        this.lastName = lastName;
        this.activated = activated;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = "888888";
        this.passwordState = user.getPasswordState();
        this.realName = user.getRealName();
        this.nickName = user.getNickName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.activated = user.getActivated();
        this.imageUrl = user.getImageUrl();
        this.introduce = user.getIntroduce();
        this.address = user.getAddress();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.authorities = user.getAuthorities().stream()
            .map(Authority::getName)
            .collect(Collectors.toSet());
        if (!user.getRoles().isEmpty()) {
            this.roles = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
            this.roleDTOS = user.getRoles().stream().map(RoleDTO::new).collect(Collectors.toSet());
        }
        if (!user.getOrgs().isEmpty()) {
            this.orgs = user.getOrgs().stream().map(Org::getId).collect(Collectors.toSet());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolePid() {
        return rolePid;
    }

    public void setRolePid(String rolePid) {
        this.rolePid = rolePid;
    }

    public String getOrgPid() {
        return orgPid;
    }

    public void setOrgPid(String orgPid) {
        this.orgPid = orgPid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<Long> getRoles() {
        return roles;
    }

    public void setRoles(Set<Long> roles) {
        this.roles = roles;
    }

    public Set<Long> getOrgs() {
        return orgs;
    }

    public void setOrgs(Set<Long> orgs) {
        this.orgs = orgs;
    }

    public Set<RoleDTO> getRoleDTOS() {
        return roleDTOS;
    }

    public void setRoleDTOS(Set<RoleDTO> roleDTOS) {
        this.roleDTOS = roleDTOS;
    }

    public Integer getPasswordState() {
        return passwordState;
    }

    public void setPasswordState(Integer passwordState) {
        this.passwordState = passwordState;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + id +
            ", rolePid='" + rolePid + '\'' +
            ", orgPid='" + orgPid + '\'' +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", realName='" + realName + '\'' +
            ", nickName='" + nickName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", telephone='" + telephone + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", introduce='" + introduce + '\'' +
            ", address='" + address + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            ", roles=" + roles +
            ", orgs=" + orgs +
            ", roleDTOS=" + roleDTOS +
            ", passwordState=" + passwordState +
            '}';
    }
}
