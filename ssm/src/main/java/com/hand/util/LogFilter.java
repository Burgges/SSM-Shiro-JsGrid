package com.hand.util;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by huiyu.chen on 2017/7/17.
 *
 */
public class LogFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LoginFilter.class);

    private final static Integer DEFAULT_USER = 1;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Log Filter init");
    }

    @Override
    public void doFilter(ServletRequest arg1, ServletResponse arg2,
                         FilterChain chain) throws IOException, ServletException {
       /* HttpServletRequest request = (HttpServletRequest)arg1;
        HttpSession session = request.getSession();
        if(session == null){
            MDC.put("userId",DEFAULT_USER);
        } else {
            if (session.getAttribute("UID") == null || "".equals(session.getAttribute("UID"))) {
                MDC.put("userId",DEFAULT_USER);
            } else {
                MDC.put("userId",session.getAttribute("UID"));
            }
        }
        chain.doFilter(arg1, arg2);*/
    }

    @Override
    public void destroy() {
        LOGGER.info("Log Filter destroy");
    }
}
