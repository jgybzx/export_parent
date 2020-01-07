package com.jgybzx.web.controller.login;


import com.jgybzx.common.utils.Encrypt;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.UserService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
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
        System.out.println(password);
        if (loginUser == null || !(loginUser.getPassword().equals(password))) {
            // 未找到用户或者密码不正确，提示错误信息
            request.setAttribute("error", "未找到用户或者密码不正确");
            return "forward:/login.jsp";
        } else {
            // 登陆成功，将用户信息存入session
            request.getSession().setAttribute("loginUser", loginUser);

            // 不同的用户看到的 菜单（权限或模块）应该是不一样的，所以查询该用户的权限
            /**
             * 根据用户，查询该用户的所有角色id 表：pe_role_user
             * 根据角色id查询该角色所有的权限(module)id，表:pe_role_module
             * 根据权限id查询该权限
             */

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

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }
}
