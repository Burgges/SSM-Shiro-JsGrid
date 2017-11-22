package com.hand.service;

import com.hand.dto.MessageDto;
import com.hand.model.Permission;

import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 10/9/2017.
 *
 */
public interface PermissionService {

    /**
     * create permission
     * @param permission create object
     */
    MessageDto<Permission> save(Permission permission);

    /**
     * delete permission by permission
     * @param permissionId permission id
     */
    void delete(Integer permissionId);

    /**
     * update permission by permissionId
     * @param permission new permission
     */
    MessageDto<Permission> update(Permission permission);

    /**
     * select permission by permissionId
     * @param permissionId condition of permissionId
     * @return result permission
     */
    Permission findOneById(Integer permissionId);

    /**
     * select permission list by permission properties
     * @param map condition of permission properties
     * @return permission list
     */
    List<Permission> findList(Map<String, Object> map);
}
