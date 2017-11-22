package com.hand.controller;

import com.hand.annotation.OperationLog;
import com.hand.dto.JsGridResult;
import com.hand.dto.MessageDto;
import com.hand.model.Permission;
import com.hand.service.PermissionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by huiyu.chen on 9/21/2017.
 *
 */
@Controller
public class PermissionController {

    private static final Logger LOGGER = Logger.getLogger(PermissionController.class);

    @Resource
    private PermissionService permissionService;

    /**
     * into permission manager page
     * @return page info
     */
    @RequestMapping(value = "/api/permissions/page")
    public String toPermissionPage() {
        return "permissions";
    }

    /**
     * select permissions by params
     * @param params condition
     * @return permission list
     * @throws Exception error
     */
    @OperationLog("Query all permissions")
    @RequestMapping(value = "/api/permissions", method = RequestMethod.GET)
    @ResponseBody
    public List<Permission> queryList(@RequestParam Map<String, Object> params) throws Exception {
        LOGGER.info("queryList: /permissions");
        List<Permission> permissions = permissionService.findList(params);
        JsGridResult<Permission> jsGridResult = new JsGridResult<>();
        jsGridResult.setData(permissions);
        jsGridResult.setItemsCount(permissions.size()+"");
        return permissions;
    }

    /**
     * add permission
     * @param permission new permission
     * @param request request
     * @return info
     * @throws Exception error
     */
    @OperationLog("Add permission")
    @RequestMapping(value = "/api/permissions", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<Permission> save(Permission permission,
                                 HttpServletRequest request) throws Exception {
        MessageDto<Permission> messageDto;
        LOGGER.info("insert: /permission");
        HttpSession session = request.getSession();
        Integer uId = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        permission.setCreatedBy(uId);
        permission.setLastUpdatedBy(uId);
        messageDto = permissionService.save(permission);
        return messageDto;
    }

    /**
     * delete permission by id
     * @param id permission id
     * @return info
     */
    @OperationLog("Delete permission")
    @RequestMapping(value = "/api/permissions/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public int delete (@PathVariable Integer id) {
        permissionService.delete(id);
        return 1;
    }

    /**
     * update permission by id
     * @param params new permission
     * @param id permission id
     * @param request request
     * @return info
     */
    @OperationLog("Update permission")
    @RequestMapping(value = "/api/permissions/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<Permission> update(@RequestParam Map<String, Object> params,
                                    @PathVariable Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Permission permission = new Permission();
        Integer uId = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        permission.setLastUpdatedBy(uId);
        return permissionService.update(permission);
    }

}
