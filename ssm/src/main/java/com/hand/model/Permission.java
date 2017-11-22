package com.hand.model;

import java.io.Serializable;

/**
 * 权限类
 *
 * Created by huiyu.chen on 9/20/2017.
 *
 */
public class Permission extends BaseModel implements Serializable {

    private Integer id;

    //访问路径
    private String url;

    //权限描述
    private String permissionDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }
}
