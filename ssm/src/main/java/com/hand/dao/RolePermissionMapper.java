package com.hand.dao;

import com.hand.dto.RolePermissionDto;
import com.hand.dto.UserRoleDto;
import com.hand.model.RolePermission;
import com.hand.model.UserRole;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by huiyu.chen on 2017/7/6.
 *
 */
public interface RolePermissionMapper {

    /**
     * create rolePermission
     * @param rolePermission create object
     */
    void save(RolePermission rolePermission);

    /**
     * delete rolePermission by permissionId
     * @param permissionId permission id
     */
    void deleteByUserId(Integer permissionId);

    /**
     * delete rolePermission by roleId
     * @param roleId role id
     */
    void deleteByRoleId(Integer roleId);

    /**
     * update rolePermission by Id
     * @param rolePermission new rolePermission
     */
    void update(RolePermission rolePermission);

    /**
     * select rolePermission list by permission Id
     * @param permissionId condition of permission Id
     * @return rolePermission list
     */
    List<RolePermissionDto> findListByPermissionId(Integer permissionId);

    /**
     * Query rolePermission list
     * @param params condition
     * @return rolePermission list
     */
    List<RolePermissionDto> findList(Map<String, Object> params);

    /**
     * select rolePermission list by roleId
     * @param roleId condition of roleId
     * @return rolePermission list
     */
    List<RolePermissionDto> findListByRoleId(Integer roleId);
}
