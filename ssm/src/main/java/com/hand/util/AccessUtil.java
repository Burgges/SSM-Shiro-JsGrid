package com.hand.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 权限控制工具类
 * Created by huiyu.chen on 2017/7/24.
 *
 */
public class AccessUtil {

    /**
     * 判断当前用户是否有权限访问此api
     * @param request HttpServletRequest
     */
    public Boolean isAccessFlag (HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean flag = true;
        if(!"".equals(session.getAttribute("UserRole")) &&
                "USER".equals(session.getAttribute("UserRole"))){
            flag = false;
        }
        return flag;
    }

}
