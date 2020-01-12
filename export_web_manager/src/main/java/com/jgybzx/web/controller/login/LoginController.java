package com.jgybzx.web.controller.login;


import com.jgybzx.common.utils.Encrypt;
import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.ModuleService;
import com.jgybzx.service.system.UserService;
import com.jgybzx.web.controller.base.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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


    /* @RequestMapping(value = "/login", name = "用户登陆")
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

            Object loginUser1 = session.getAttribute("loginUser");
            // 不同的用户，有不同的身份 SaaS管理员 企业管理员 企业员工，所看到的菜单也是不一样的
            List<Module> moduleList = moduleService.findModuleByUserId(loginUser);
            session.setAttribute("modules", moduleList);

            // 跳转主页
            return "home/main";
        }
    }*/

    /**
     * 使用 shiro 来进行 登陆验证
     * subject：一个对象，负责和shiro框架交互
     * 1、所以需要获取 subject ,获取方式，shrio框架提供获取该对象的工具类 SecurityUtils
     * 2、将我们的用户名密码数据交给shrio，让shrio进行认证，
     * ---shrio提供自己的一个对象，UsernamePasswordToken 我们需要将自己的user封装成UsernamePasswordToken
     * 3、使用subject的 login方法，会自动调用 shrio的验证管理器，也就是我们自己写的
     * ---void login(AuthenticationToken token)
     * 4、认证管理器会对传递的登陆对象进行验证，注意：只要验证不成功，login方法都会抛异常，所以需要捕获一下
     *
     * @param email
     * @param password
     * @return
     */
    /*@RequestMapping(value = "/login", name = "用户登陆")
    public String login(String email, String password) {
        //非空判断
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            request.setAttribute("error", "用户名或密码不能为空");
            return "forward:/login.jsp";
        }
        try {
            // 获取subject ，与shiro交互
            Subject subject = SecurityUtils.getSubject();
            // 将我们的用户名密码数据交给shrio，让shrio进行认证
            UsernamePasswordToken upToken = new UsernamePasswordToken(email, password);
            // 使用subject的 login方法，会自动调用 shrio的验证管理器，也就是我们自己写的
            // void login(AuthenticationToken token)
            subject.login(upToken);
            // 如果此时没有抛异常，说明用户验证成功，所以需要从realm中获取登陆用户
            User loginUser = (User) subject.getPrincipal();
            // 获取之后将 登陆用户放入session
            session.setAttribute("loginUser", loginUser);
            // 根据用户获取其对象的module信息
            List<Module> moduleList = moduleService.findModuleByUser(loginUser);
            session.setAttribute("modules", moduleList);
            /// 跳转主页
            return "home/main";
        } catch (AuthenticationException e) {
            System.out.println("用户名或密码不正确");
            // 如果出现异常 说明用户登陆不成功，直接提示登陆失败
            request.setAttribute("error", "未找到用户或者密码不正确");
            return "forward:/login.jsp";
        }
    }*/

    /**
     * 用户登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", name = "用户登出")
    public String logout() {
        // 退出时 清空缓存
        SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping(value = "/home", name = "返回主页")
    public String home() {
        return "home/home";
    }



    @RequestMapping(value = "/login", name = "用户登陆")
    public String login(String email, String password) {
        System.out.println("登陆方法执行");
        // 根据email查询是否 存在该用户
        User loginUser = userService.login(email);
        if (loginUser==null){
            request.setAttribute("error","没有该用户");
            return "forward:/login.jsp";
        }

        try {
            // 获取 subject对象
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken upToken =  new UsernamePasswordToken(email,password);
            // 调用login 同时会执行我们自定义的 认证方法
            subject.login(upToken);
            System.out.println("认证成功");
            // 如果 不抛异常，说明认证成功，获取用户数据，放入session
            User user = (User) subject.getPrincipal();
            session.setAttribute("loginUser",user);

            List<Module> moduleList = moduleService.findModuleByUser(loginUser);
            session.setAttribute("modules",moduleList);
            return "home/main";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            request.setAttribute("msg","密码错误");
            return "forward:login.jsp";
        }
    }
}
