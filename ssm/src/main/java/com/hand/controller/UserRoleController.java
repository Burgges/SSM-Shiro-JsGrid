package com.hand.controller;

import com.hand.annotation.OperationLog;
import com.hand.dto.JsGridResult;
import com.hand.dto.MessageDto;
import com.hand.dto.UserRoleDto;
import com.hand.model.UserRole;
import com.hand.service.UserRoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by huiyu.chen on 9/21/2017.
 *
 */
@Controller
public class UserRoleController {

    private static final Logger LOGGER = Logger.getLogger(UserRoleController.class);

    @Resource
    private UserRoleService userRoleService;

    @RequestMapping(value = "/api/userRoles/page")
    public String toUserRolePage() {
        return "userRoles";
    }

    @OperationLog("Query all user role")
    @RequestMapping(value = "/api/userRoles", method = RequestMethod.GET)
    @ResponseBody
    public List<UserRoleDto> queryList(@RequestParam Map<String, Object> params) throws Exception {
        LOGGER.info("queryList: /userRoles");
        List<UserRoleDto> userRoleList = userRoleService.findList(params);
        JsGridResult<UserRoleDto> jsGridResult = new JsGridResult<>();
        jsGridResult.setData(userRoleList);
        jsGridResult.setItemsCount(userRoleList.size()+"");
        return userRoleList;
    }

    @OperationLog("Assign role for user")
    @RequestMapping(value = "/api/userRoles", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<UserRole> save(@RequestParam Map<String, Object> params) throws Exception {
        MessageDto<UserRole> messageDto;
        LOGGER.info("Assign role for user: /userRoles");
        String roleIdStr = params.get("roleIdStr") == null ? "" : params.get("roleIdStr").toString();
        Integer userId = params.get("userId") == null ? -1 : Integer.parseInt(params.get("userId").toString());
        messageDto = userRoleService.save(roleIdStr, userId);
        return messageDto;
    }

    @OperationLog("Select roles by user id")
    @RequestMapping(value = "/api/userRoles/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserRoleDto> findRoleByUserId(@PathVariable Integer userId) throws Exception {
        LOGGER.info("Select roles by user id: /userRoles");
        List<UserRoleDto> userRoleList = userRoleService.findListByUserId(userId);
        return userRoleList;
    }
}
