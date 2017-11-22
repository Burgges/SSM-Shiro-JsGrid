package com.hand.service;

import com.hand.dto.MessageDto;
import com.hand.dto.UserRoleDto;
import com.hand.model.UserRole;

import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
public interface UserRoleService {

    /**
     * grant role to user
     * @param roleIdStr role id array
     * @param userId grant user id
     * @return return info
     * @throws Exception save error
     */
    MessageDto<UserRole> save(String roleIdStr, Integer userId) throws Exception ;

    /**
     * delete userRole by userId
     * @param userId user id
     */
    MessageDto<UserRole> deleteByUserId(Integer userId);

    /**
     * delete userRole by roleId
     * @param roleId role id
     */
    MessageDto<UserRole> deleteByRoleId(Integer roleId);


    /**
     * update user by userId
     * @param user new user
     * @param userId user Id
     */
//    MessageDto<User> update(User user, Integer userId) throws Exception;

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
