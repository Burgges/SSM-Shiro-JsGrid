package com.hand.service.impl;

import com.hand.dao.UserRoleMapper;
import com.hand.dto.MessageDto;
import com.hand.dto.UserRoleDto;
import com.hand.model.UserRole;
import com.hand.service.UserRoleService;
import com.hand.util.MessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    /*private static final Logger LOGGER = Logger.getLogger(UserRoleServiceImpl.class);*/

    @Resource
    private UserRoleMapper userRoleMapper;

    private MessageUtil<UserRole> messageUtil = new MessageUtil<>();


    @Override
    public MessageDto<UserRole> save(String roleIdStr, Integer userId) throws Exception {
        userRoleMapper.deleteByUserId(userId);
        if(!"".equals(roleIdStr)){
            String[] roleIdArr = roleIdStr.split(",");
            for (String roleId : roleIdArr) {
                UserRole userRole = new UserRole(userId, Integer.parseInt(roleId));
                userRoleMapper.save(userRole);
            }
        }
        return messageUtil.setMessageDto(200, "Role assign success!", null);
    }

    @Override
    public MessageDto<UserRole> deleteByUserId(Integer userId) {
        return null;
    }

    @Override
    public MessageDto<UserRole> deleteByRoleId(Integer roleId) {
        return null;
    }

    @Override
    public List<UserRoleDto> findListByUserId(Integer userId) {
        return userRoleMapper.findListByUserId(userId);
    }

    @Override
    public List<UserRoleDto> findListByRoleId(Integer roleId) {
        return null;
    }

    @Override
    public List<UserRoleDto> findList(Map<String, Object> map) {
        return userRoleMapper.findList(map);
    }
}
