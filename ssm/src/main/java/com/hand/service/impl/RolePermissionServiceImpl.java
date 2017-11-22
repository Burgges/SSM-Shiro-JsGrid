package com.hand.service.impl;

import com.hand.dto.MessageDto;
import com.hand.dto.RolePermissionDto;
import com.hand.model.RolePermission;
import com.hand.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 2017/10/16.
 *
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {


    @Override
    public List<RolePermissionDto> findList(Map<String, Object> params) {
        return null;
    }

    @Override
    public MessageDto<RolePermission> save(Integer roleId, String pIdStr) {
        return null;
    }

    @Override
    public List<RolePermissionDto> findListByRoleId(Integer roleId) {
        return null;
    }
}
