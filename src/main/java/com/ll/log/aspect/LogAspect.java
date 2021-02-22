package com.ll.log.aspect;

import com.ll.log.entity.Log;
import com.ll.log.service.LogService;
import com.ll.log.utils.LogUtils;
import com.ll.log.utils.RequestHolder;
import com.ll.log.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lihaoxuan
 * @date 2020/12/28 10:03
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private LogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.ll.log.annotation.Log)")
    public void logPoinCut() {
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPoinCut()")
    public Object saveSysLog(ProceedingJoinPoint joinPoint)throws Throwable{
        Object result;
        currentTime.set(System.currentTimeMillis());//记录方法的执行时间
        result = joinPoint.proceed();
        Log log = new Log("INFO",System.currentTimeMillis() - currentTime.get());//定义日志类型
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(SecurityUtils.getCurrentUsername(), LogUtils.getBrowser(request), LogUtils.getIp(request),joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPoinCut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log("ERROR",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptiondetail(LogUtils.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(SecurityUtils.getCurrentUsername(), LogUtils.getBrowser(request), LogUtils.getIp(request), (ProceedingJoinPoint)joinPoint, log);
    }
}
