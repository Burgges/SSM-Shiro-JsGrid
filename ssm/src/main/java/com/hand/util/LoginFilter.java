package com.hand.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login Filter
 * Created by huiyu.chen on 2017/7/13.
 *
 */
@Component
public class LoginFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LoginFilter.class);

    public LoginFilter() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Login Filter init");
    }

    @Override
    public void doFilter(ServletRequest arg1, ServletResponse arg2,
                         FilterChain chain) throws IOException, ServletException {
        LOGGER.info(((HttpServletRequest)arg1).getRequestURI());
        HttpServletRequest request = (HttpServletRequest)arg1;
        HttpServletResponse response = (HttpServletResponse)arg2;
        HttpSession session = request.getSession(true);
        String userName =
                session.getAttribute("userName")== null ? "" : session.getAttribute("userName")
                        .toString();
        String url = request.getRequestURI();
        if("".equals(userName) || userName == null) {
            if(url != null && !"".equals(url) && (url.indexOf("login")<0) ){
                response.sendRedirect(request.getContextPath()+"/login");
                return;
            }
        }
        chain.doFilter(arg1, arg2);
    }

    @Override
    public void destroy() {
        LOGGER.info("Login Filter destroy");
    }
}
