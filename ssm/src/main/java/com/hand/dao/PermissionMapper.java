package com.hand.dao;

import com.hand.model.Permission;

import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
public interface PermissionMapper {

    /**
     * create permission
     * @param permission create object
     */
    void save(Permission permission);

    /**
     * delete permission by permission
     * @param permissionId permission id
     */
    void delete(Integer permissionId);

    /**
     * update permission by permissionId
     * @param permission new permission
     */
    void update(Permission permission);

    /**
     * select permission by permissionId
     * @param permissionId condition of permissionId
     * @return result permission
     */
    Permission findOneById(Integer permissionId);

    /**
     * select permission by url
     * @param  id condition of id
     * @param url condition of url
     * @return result permission
     */
    Permission findOneByUrl(Integer id, String url);

    /**
     * select permission list by permission properties
     * @param map condition of permission properties
     * @return permission list
     */
    List<Permission> findList(Map<String, Object> map);
}
