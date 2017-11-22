package com.hand.service.impl;

import com.hand.dao.RoleMapper;
import com.hand.dto.MessageDto;
import com.hand.model.Role;
import com.hand.service.RoleService;
import com.hand.util.MessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/18.
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = Logger.getLogger(RoleServiceImpl.class);

    @Resource
    private RoleMapper roleMapper;

    private MessageUtil<Role> messageUtil = new MessageUtil<>();

    /**
     * insert role
     * @param role new role
     * @return result
     */
    @Override
    public MessageDto<Role> save(Role role) {
        Date date = new Date();
        role.setCreationDate(date);
        role.setLastUpdateDate(date);
        String message = "Insert success";
        if(role.getRoleName() == null || "".equals(role.getRoleName())){
            message = "Role name is not null";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, role);
        }
        if (roleMapper.getRoleByName(role.getRoleName()) != null) {
            message = "Role name was existed";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, role);
        }
        roleMapper.save(role);
        return messageUtil.setMessageDto(200,message, roleMapper.getRoleByName(role.getRoleName()));
    }

    /**
     * delete role by id
     * @param roleId condition of id
     * @return result
     */
    @Override
    public MessageDto<Role> delete(Integer roleId) {
        String message = "Delete success";
        if (roleMapper.getRoleById(roleId) == null) {
            message = "Role not found";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, null);
        }
        roleMapper.delete(roleId);
        return messageUtil.setMessageDto(200,message, null);
    }

    /**
     * update role by id
     * @param role new role
     * @return result
     */
    @Override
    public MessageDto<Role> update(Role role) {
        Date date = new Date();
        String message = "Update success";
        if(role.getRoleName() == null || "".equals(role.getRoleName())){
            message = "Role name is not null";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, role);
        }
        Role role1 = roleMapper.getRoleById(role.getRoleId());
        if (role1 == null) {
            message = "Role not found";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, null);
        }
        Role role2 = roleMapper.getRoleByName(role.getRoleName());
        if (role2 != null && !role1.getRoleId().equals(role2.getRoleId())) {
            message = "Role name was existed";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, role);
        }
        role.setLastUpdateDate(date);
        roleMapper.update(role);
        return messageUtil.setMessageDto(200,message, role);
    }

    /**
     * Select role list
     * @param params condition
     * @return role list
     */
    @Override
    public List<Role> selectRole(Map<String, Object> params) {
        return roleMapper.selectRole(params);
    }

    /**
     * Get role by id
     * @param roleId condition of id
     * @return result
     */
    @Override
    public Role getRoleById(Integer roleId) {
        return roleMapper.getRoleById(roleId);
    }

    /**
     * Get role by name
     * @param roleName condition of name
     * @return result
     */
    @Override
    public Role getRoleByName(String roleName) {
        return roleMapper.getRoleByName(roleName);
    }
}
