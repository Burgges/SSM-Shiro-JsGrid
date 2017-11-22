package com.hand.dao;

import com.hand.dto.UserRoleDto;
import com.hand.model.UserRole;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by huiyu.chen on 2017/7/6.
 *
 */
public interface UserRoleMapper {

    /**
     * create userRole
     * @param userRole create object
     */
    void save(UserRole userRole);

    /**
     * delete userRole by userId
     * @param userId user id
     */
    void deleteByUserId(Integer userId);

    /**
     * delete userRole by roleId
     * @param roleId role id
     */
    void deleteByRoleId(Integer roleId);

    /**
     * update userRole by userId
     * @param userRole new userRole
     */
    void update(UserRole userRole);

    /**
     * select userRole list by user Id
     * @param userId condition of user Id
     * @return UserRoleDto list
     */
    List<UserRoleDto> findListByUserId(Integer userId);

    /**
     * select userRole list by roleId
     * @param roleId condition of roleId
     * @return UserRoleDto list
     */
    List<UserRoleDto> findListByRoleId(Integer roleId);

    /**
     * select userRole list
     * @return UserRoleDto list
     */
    List<UserRoleDto> findList(Map<String, Object> map);
}
