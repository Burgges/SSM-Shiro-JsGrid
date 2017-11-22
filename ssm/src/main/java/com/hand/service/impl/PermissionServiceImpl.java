package com.hand.service.impl;

import com.hand.dao.PermissionMapper;
import com.hand.dto.MessageDto;
import com.hand.model.Permission;
import com.hand.service.PermissionService;
import com.hand.util.MessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 10/9/2017.
 *
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private static final Logger LOGGER = Logger.getLogger(PermissionServiceImpl.class);

    @Resource
    private PermissionMapper permissionMapper;

    private MessageUtil<Permission> messageUtil = new MessageUtil<>();

    @Override
    public MessageDto<Permission> save(Permission permission) {
        String resultInfo = checkPermission(permission);
        if("success".equals(resultInfo)){
            permission.setCreationDate(new Date());
            permission.setLastUpdateDate(new Date());
            permissionMapper.save(permission);
            return messageUtil.setMessageDto(200, "Insert success!", permission);
        }
        return messageUtil.setMessageDto(500, resultInfo, permission);
    }

    @Override
    public void delete(Integer permissionId) {
        Permission permission = permissionMapper.findOneById(permissionId);
        if(permission == null){
            LOGGER.error("Permission not found by permission id: {}",new Exception("Permission not found by permission id"));
            return;
        }
        permissionMapper.delete(permissionId);
    }

    @Override
    public MessageDto<Permission> update(Permission permission) {
        Permission permission2 = permissionMapper.findOneByUrl(permission.getId(), permission.getUrl());

        String resultInfo = checkPermission(permission);

        if(permission2 != null){
            LOGGER.error("Permission was existed: {}",new Exception("Permission was existed"));
            return messageUtil.setMessageDto(500, "Permission was existed", permission);
        }

        if("success".equals(resultInfo)){
            permission.setLastUpdateDate(new Date());
            permissionMapper.update(permission);
            return messageUtil.setMessageDto(200, "Update success!", permission);
        }
        return messageUtil.setMessageDto(500, resultInfo, permission);
    }

    @Override
    public Permission findOneById(Integer permissionId) {
        return permissionMapper.findOneById(permissionId);
    }

    @Override
    public List<Permission> findList(Map<String, Object> map) {
        return permissionMapper.findList(map);
    }

    /**
     * 校验权限必输字段是否为空
     * @param permission 被校验的权限
     * @return info
     */
    private String checkPermission(Permission permission){
        if(permission == null){
            LOGGER.error("Permission is not null: {}",new Exception("Permission is not null"));
            return "Permission is not null!";
        }

        if(permission.getUrl() == null || "".equals(permission.getUrl())){
            LOGGER.error("Permission url is not null: {}",new Exception("Permission url is not null"));
            return "Permission url is not null!";
        }

        return "success";
    }
}
