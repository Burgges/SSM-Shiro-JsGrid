package com.hand.util;

import com.alibaba.fastjson.JSON;
import com.hand.annotation.OperationLog;
import com.hand.model.Log;
import com.hand.service.LogService;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by huiyu.chen on 2017/7/17.
 *
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = Logger.getLogger(LogAspect.class);

    @Resource
    private LogService logService;

    @Pointcut("@annotation(com.hand.annotation.OperationLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LOGGER.info("AOP log around");
        Long beginTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        Long operationTime = System.currentTimeMillis() - beginTime;
        saveLog(proceedingJoinPoint, operationTime);
        return result;
    }


    /**
     * save user operation log
     * @param joinPoint point cut
     * @param operationTime operation time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long operationTime) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();

        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        if (operationLog != null) {
            log.setLogDescription(operationLog.value());
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setFun(className + "." + methodName + "()");

        Object[] args = joinPoint.getArgs();

        String params = JSON.toJSONString(args[0]);
        log.setParams(params);

        HttpServletRequest request = CommonUtil.getHttpServletRequest();
        String ipAddress = CommonUtil.getIpAddress(request);

        log.setIpAddress(ipAddress);


        HttpSession session = request.getSession();
        Integer userId = (Integer)
                (session.getAttribute("UID") == null ? 1 : session.getAttribute("UID"));

        log.setUserId(userId);
        log.setOperationTime(new Date());
        log.setOperationTimes(operationTime);
        logService.save(log);

    }

}
