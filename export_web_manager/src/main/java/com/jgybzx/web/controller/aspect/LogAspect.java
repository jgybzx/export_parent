package com.jgybzx.web.controller.aspect;

import com.jgybzx.domain.system.SysLog;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author: guojy
 * @date: 2020/1/8 17:04
 * @Description: ${TODO}
 * @version:
 */
@Component
@Aspect
public class LogAspect {
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    @Around("execution(* com.jgybzx.web.controller.*.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) {
        Object obj = null;

        try {
            // 获取 切点的方法 签名
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            // 获取切点方法 对象
            Method method = methodSignature.getMethod();

            // 判断方法上是否有 @RequestMapping注解
            boolean flag = method.isAnnotationPresent(RequestMapping.class);

            // 获取登陆用户
            User loginUser = (User) session.getAttribute("loginUser");
            // 当用户登录的时候并且  方法上有@RequestMapping注解  才开始记录
            if (loginUser != null && flag) {
                SysLog sysLog = new SysLog();
                sysLog.setUserName(loginUser.getUserName());
                sysLog.setCompanyId(loginUser.getCompanyId());
                sysLog.setCompanyName(loginUser.getCompanyName());
                sysLog.setTime(new Date());
                sysLog.setId(request.getRemoteAddr());

                // 获取切点方法的名字
                sysLog.setMethod(method.getName());
                // 获取切点方法 上的 @RequestMapping注解
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                // 获取@RequestMapping注解的name属性
                sysLog.setAction(annotation.name());

                // 调用方法 保存
                sysLogService.save(sysLog);
            }

            obj = pjp.proceed();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }

}
