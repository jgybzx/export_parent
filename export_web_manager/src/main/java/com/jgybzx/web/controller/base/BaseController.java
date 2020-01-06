package com.jgybzx.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;

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


    //这两个值以后必须从session中获得
    protected  String companyId = "1";
    protected  String companyName = "传智播客";

}
