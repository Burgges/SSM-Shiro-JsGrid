package com.hand.controller;

import com.hand.annotation.OperationLog;
import com.hand.dto.JsGridResult;
import com.hand.dto.MessageDto;
import com.hand.model.Role;
import com.hand.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Role controller
 * Created by huiyu.chen on 2017/7/18.
 *
 */
@Controller
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/api/toRoles", method = RequestMethod.GET)
    public String toRoles () {
        return "roles";
    }

    /**
     * Select all roles
     * @param params find condition
     * @return role list
     */
    @OperationLog("Query all roles")
    @RequestMapping(value = "/api/roles", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> selectRole (@RequestParam Map<String, Object> params) {

        JsGridResult<Role> jsGridResult = new JsGridResult<>();
        List<Role> roleList = roleService.selectRole(params);
        if (roleList != null) {
            jsGridResult.setItemsCount(roleList.size()+"");
        }
        jsGridResult.setData(roleList);
        return roleList;
    }

    /**
     * Add role
     * @param role new role
     * @param request http request
     * @return return info
     */
    @OperationLog("Add role")
    @RequestMapping(value = "/api/roles", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<Role> save (Role role, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer uId = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        role.setCreatedBy(uId);
        role.setLastUpdatedBy(uId);
        return roleService.save(role);
    }

    /**
     * Update role by id
     * @param params new role
     * @param id primary key of roleId
     * @param request http request
     * @return return info
     */
    @OperationLog("Update role")
    @RequestMapping(value = "/api/roles/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<Role> update (@RequestParam Map<String, Object> params,
                                    @PathVariable Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String roleName = params.get("roleName") == null ? "" : params.get("roleName").toString();
        String roleDescription = params.get("roleDescription") == null ? "" : params.get("roleDescription").toString();
        String authorityCode = params.get("authorityCode") == null ? "" : params.get("authorityCode").toString();
        Boolean enabledFlag = false;
        if(!"false".equals(params.get("enabledFlag"))){
            enabledFlag = true;
        }
        Role role = new Role();
        Integer uId = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        role.setRoleId(id);
        role.setLastUpdatedBy(uId);
//        role.setAuthorityCode(authorityCode);
        role.setRoleDescription(roleDescription);
        role.setRoleName(roleName);
        role.setEnabledFlag(enabledFlag);
        return roleService.update(role);
    }

    /**
     * Delete role by id
     * @param id primary key of roleId
     * @return return info
     */
    @OperationLog("Delete role")
    @RequestMapping(value = "/api/roles/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public int delete (@PathVariable Integer id) {
        roleService.delete(id);
        return 1;
    }



}
