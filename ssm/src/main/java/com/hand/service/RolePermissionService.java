package com.hand.service;

import com.hand.dto.MessageDto;
import com.hand.dto.RolePermissionDto;
import com.hand.model.RolePermission;

import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 2017/10/16.
 *
 */

public interface RolePermissionService {

    /**
     * Query all role permission
     * @param params condition
     * @return result
     */
    List<RolePermissionDto> findList(Map<String, Object> params);

    /**
     * Assign permission to role
     * @param roleId role
     * @param pIdStr permission list
     * @return result
     */
    MessageDto<RolePermission> save(Integer roleId, String pIdStr);

    /**
     * Find permissions by role id
     * @param roleId role id
     * @return result
     */
    List<RolePermissionDto> findListByRoleId(Integer roleId);

}
