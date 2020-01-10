package com.jgybzx.web.controller.base;

import com.jgybzx.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: guojy
 * @date: 2020/1/3 12:06
 * @Description: ${TODO}
 * @version:
 */
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;


    /**
     * 这两个值以后必须从session中获得
     */

    protected  String companyId = "1";
    protected  String companyName = "NY-738星云";

    /**
     * 在所有方法执行执行执行此方法
     */
    @ModelAttribute
    public void before(){
        // 在所有方法执行之前取到登陆的用户  赋值 companyId 和 companyName

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser!=null){
            companyId = loginUser.getCompanyId();
            companyName=loginUser.getCompanyName();
        }
    }

}
