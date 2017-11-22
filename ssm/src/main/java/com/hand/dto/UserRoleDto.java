package com.hand.dto;

import com.hand.model.Role;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by huiyu.chen on 9/21/2017.
 */
public class UserRoleDto {

    private Integer userId;

    private String userName;

    private String userMail;

    private Boolean enabledFlag;

    private List<Role> roleList = new LinkedList<>();

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
