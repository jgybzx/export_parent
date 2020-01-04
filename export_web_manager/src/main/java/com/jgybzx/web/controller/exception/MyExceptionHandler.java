package com.jgybzx.web.controller.exception;

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
public class MyExceptionHandler  implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        System.out.println(ex.fillInStackTrace());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("msg" ,"出现不可预期的错误，联系管理员");
        return mv;
    }
}
