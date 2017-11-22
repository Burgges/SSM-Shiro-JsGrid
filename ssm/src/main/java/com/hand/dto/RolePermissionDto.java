package com.hand.dto;

import com.hand.model.Permission;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by huiyu.chen on 9/21/2017.
 */
public class RolePermissionDto {

    private Integer roleId;

    private String roleName;

    private String roleDesc;

    private List<Permission> permissionList = new LinkedList<>();

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
