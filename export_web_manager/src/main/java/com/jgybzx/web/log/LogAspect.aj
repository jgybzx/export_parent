package com.jgybzx.web.log;

import com.jgybzx.domain.system.SysLog;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.SysLogService;
import com.jgybzx.web.controller.base.BaseController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/7 17:35
 * @Description: ${TODO}
 * @version:
 */
@Component
@Aspect
public class LogAspect extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 配置环绕通知，进行 日志管理，
     * 增强所有的 controller
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.jgybzx.web.controller.*.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("AOP拦截");
        SysLog sysLog = new SysLog();
        //取出session中的登陆用户
        User loginUser = (User) session.getAttribute("loginUser");
        sysLog.setId(UUID.randomUUID().toString());
        sysLog.setUserName(loginUser.getUserName());
        // 获取ip
        sysLog.setIp(request.getRemoteAddr());
        sysLog.setTime(new Date());
        sysLog.setCompanyId(loginUser.getCompanyId());
        sysLog.setCompanyName(loginUser.getCompanyName());

        // 获得方法签名
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        // 获得执行的方法
        Method method = methodSignature.getMethod();
        // 获得方法名称
        String methodName = method.getName();
        sysLog.setMethod(methodName);

        // 如果执行的 方法有 RequestMapping 注解，那么需要获取注解的name属性
        if (method.isAnnotationPresent(RequestMapping.class)){
            // 获取 controller中的 @RequestMapping的nam属性
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            String action = annotation.name();
            sysLog.setAction(action);
        }

        // 保存 对象
        sysLogService.save(sysLog);
       return pjp.proceed();
    }
}
