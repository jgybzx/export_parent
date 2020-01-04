package com.jgybzx.web.controller.base;

import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: guojy
 * @date: 2020/1/3 12:06
 * @Description: ${TODO}
 * @version:
 */
public class BaseCompanyController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpServletSession session;

}
