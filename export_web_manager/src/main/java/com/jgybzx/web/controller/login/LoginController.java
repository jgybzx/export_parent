package com.jgybzx.web.controller.login;


import com.jgybzx.common.utils.Encrypt;
import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.ModuleService;
import com.jgybzx.service.system.UserService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;
    @RequestMapping(value = "/login",name = "用户登陆")
    public String login(String email, String password) {

        //非空判断
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            request.setAttribute("error", "用户名或密码不能为空");
            return "forward:/login.jsp";
        }
        //根据email查询用户
        User loginUser = userService.login(email);

        //加密密码
        password = Encrypt.md5(password, email);
        if (loginUser == null || !(loginUser.getPassword().equals(password))) {
            // 未找到用户或者密码不正确，提示错误信息
            request.setAttribute("error", "未找到用户或者密码不正确");
            return "forward:/login.jsp";
        } else {
            // 登陆成功，将用户信息存入session
            session.setAttribute("loginUser", loginUser);

            // 不同的用户，有不同的身份 SaaS管理员 企业管理员 企业员工，所看到的菜单也是不一样的
            List<Module> moduleList= moduleService.findModuleByUserId(loginUser);

            session.setAttribute("modules",moduleList);
            // 跳转主页
            return "home/main";
        }


    }

    /**
     * 用户登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", name = "用户登出")
    public String logout() {
        //SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping(value = "/home",name = "返回主页")
    public String home() {
        return "home/home";
    }
}
