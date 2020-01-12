package com.jgybzx.shiro_02;


import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.ModuleService;
import com.jgybzx.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: guojy
 * @date: 2020/1/10 15:22
 * @Description: ${TODO}
 * @version:
 */
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    /**
     * 授权管理器
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("自定义授权管理器 2 执行");
        AuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        // 获取登陆用户的权限，
        List<Module> moduleList = moduleService.findModuleByUser(user);
        // info 构造需要一个set，list转set
        Set<String> moduleSet = new HashSet<>();
        for (Module module : moduleList) {
            moduleSet.add(module.getName());
        }
        System.out.println(moduleSet);
        ((SimpleAuthorizationInfo) info).setStringPermissions(moduleSet);
        return info;
    }

    /**
     * 认证管理器,比较安全数据和认证数据
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("自定义认证管理器 2 执行");
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        // 认证数据
        String email = upToken.getUsername();
        // 数据库获取安全数据
        User login = userService.login(email);

        // 构建 AuthenticationInfo
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(login,login.getPassword(),this.getName());
        // 调用密码比较器
        return authenticationInfo;
    }
}
