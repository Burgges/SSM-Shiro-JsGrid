package com.hand.model;

import java.io.Serializable;

/**
 * 角色权限关系表
 *
 * Created by huiyu.chen on 9/20/2017.
 */
public class RolePermission implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;

    //{@link Role.roleId}
    private Integer roleId;

    //{@link Permission.pId}
    private Integer pId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
