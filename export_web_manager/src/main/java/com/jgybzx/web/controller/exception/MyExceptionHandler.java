package com.jgybzx.web.controller.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: guojy
 * @date: 2020/1/3 14:31
 * @Description: ${TODO}
 * @version:
 */
@Component
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ex.printStackTrace();
        ModelAndView mv = new ModelAndView();

        if (ex instanceof UnauthorizedException) {
            // 基于注解配置授权过滤器，需要自己捕获异常，UnauthorizedException
            // 如果捕获到该异常，直接重定向到权限不足页面
            mv.setViewName("redirect:/unauthorized.jsp");
        } else {
            mv.setViewName("error");
            mv.addObject("msg", "出现不可预期的错误，联系管理员");
        }

        return mv;
    }
}
