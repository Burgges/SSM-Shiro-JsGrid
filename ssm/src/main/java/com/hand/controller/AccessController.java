package com.hand.controller;

import com.hand.model.Lookup;
import com.hand.service.LookupService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 权限控制器
 * Created by huiyu.chen on 2017/7/21.
 *
 */
@Controller
public class AccessController {

    private static final Logger LOGGER = Logger.getLogger(AccessController.class);

    @Resource
    private LookupService lookupService;

    /**
     * Get user menu
     * @param request request
     * @return menu info
     */
    @RequestMapping(value = "/api/getMenu", method = RequestMethod.GET)
    @ResponseBody
    public String getMenu (HttpServletRequest request) {
        LOGGER.info("api/getMenu");
        HttpSession session = request.getSession();
        Integer uId = (Integer)session.getAttribute("UID");
        Lookup lookup = lookupService.getLookupByUserId(uId, "Menu");
        if(lookup == null){
            return "error";
        }
        return lookup.getMeaning();
    }


    /**
     * Get user access
     * @param request request
     * @return access info
     */
    @RequestMapping(value = "/api/getAccess", method = RequestMethod.GET)
    @ResponseBody
    public String getAccess (HttpServletRequest request) {
        LOGGER.info("api/getAccess");
        HttpSession session = request.getSession();
        Integer uId = (Integer)session.getAttribute("UID");
        Lookup lookup = lookupService.getLookupByUserId(uId, "Access");
        if(lookup == null){
            return "error";
        }
        session.setAttribute("UserRole",lookup.getLookupCode());
        return lookup.getMeaning();
    }
}
