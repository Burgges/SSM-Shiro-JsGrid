package com.hand.controller;

import com.hand.dto.JsGridResult;
import com.hand.dto.RolePermissionDto;
import com.hand.dto.UserRoleDto;
import com.hand.service.RolePermissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 权限分配控制器
 * Created by huiyu.chen on 9/21/2017.
 *
 */
@Controller
public class RolePermissionController {

    private static final Logger LOGGER = Logger.getLogger(RolePermissionController.class);

    @Autowired
    private RolePermissionService rolePermissionService;

    @RequestMapping(value = "/api/rolePermissions/page")
    public String toUserRolePage() {
        return "rolePermissions";
    }


    @RequestMapping(value = "/api/rolePermissions", method = RequestMethod.GET)
    @ResponseBody
    public List<RolePermissionDto> selectRolePermission(@RequestParam Map<String, Object> params){
        LOGGER.info("");
        List<RolePermissionDto> rolePermissionDtos = rolePermissionService.findList(params);
        JsGridResult<RolePermissionDto> jsGridResult = new JsGridResult<>();
        jsGridResult.setData(rolePermissionDtos);
        jsGridResult.setItemsCount(rolePermissionDtos.size()+"");
        return rolePermissionDtos;
    }

}
