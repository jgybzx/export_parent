package com.jgybzx.shiro;

import com.jgybzx.common.utils.Encrypt;
import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.Role;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.ModuleService;
import com.jgybzx.service.system.RoleService;
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
 * @date: 2020/1/9 14:36
 * @Description: 继承  AuthorizingRealm  实现两个方法
 * @version:
 */
public class AuthRealm extends AuthorizingRealm {
    /**
     * 方法名字：doGetAuthorizationInfo 执行获取授权信息
     * 返回值：AuthorizationInfo 授权数据信息(哪些权限可以被放行)
     * 参数：PrincipalCollection重要数据 的集合(本次登陆用户的数据，数据库中的数据)
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("自定义的授权管理器执行");
        // 构建 AuthorizationInfo 授权信息，AuthorizationInfo是一个接口，所以需要new 一个实现类
        AuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // public SimpleAuthorizationInfo(Set<String> roles) 需要一个 set集合，进行构造,集合中存的就是该用户的所有角色
        Set<String> moduleSet = new HashSet<>();

        // 从数据库中获取 用户的角色信息
        User user = (User) principals.getPrimaryPrincipal();
        List<Module> moduleList = moduleService.findModuleByUser(user);
        // 将Lsit转换为set
        for (Module module : moduleList) {
            moduleSet.add(module.getName());
        }

        // 构建SimpleAuthorizationInfo,在xml中配置要控制的角色信息即可
        //  /system/module/list.do = perms["模块管理"] 表示只有拥有 模块管理的人，才能 访问到这个页面
        ((SimpleAuthorizationInfo) authorizationInfo).setStringPermissions(moduleSet);
        return authorizationInfo;
    }

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private UserService userService;

    /**
     * 认证方法，用于用户登陆
     * 获取认证信息，username password
     * 方法名：doGetAuthenticationInfo 执行获取认证信息
     * 参数：AuthenticationToken 用户输入的用户名和密码
     * 返回值:AuthenticationInfo 数据库中的用户数据
     * 1、获取 login传入的  UsernamePasswordToken对象，里边封装这 我们输入的用户名和密码
     * --- UsernamePasswordToken对象 实现了HostAuthenticationToken
     * --- HostAuthenticationToken 继承了AuthenticationToken
     * --所以，UsernamePasswordToken对象可以认为是 AuthenticationToken 的一个实现类，可以直接强转
     *  UsernamePasswordToken upToken = (UsernamePasswordToken) token
     * 2、从 UsernamePasswordToken中获取登陆数据,根据email查询数据库中的用户信息
     * 3、创建安全数据对象，new SimpleAuthenticationInfo(Object,Object,String);
     * --- 并返回，安全数据对象，就是数据库中查询出的数据
     * 4、执行第三步的时候会直接调用我们自己的 密码比较器
     * @param token 用户输入的用户名和密码
     * @return 数据库中的用户数据
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("自定义的认证管理器执行");
        // 获取 login传入的  UsernamePasswordToken对象，用户自己输入的用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        // 从 UsernamePasswordToken中获取登陆数据,根据email查询数据库中的用户信息
        String email = upToken.getUsername();
        User user = userService.login(email);

        if (user!=null){

            // 返回值为 AuthenticationInfo 这是个接口，所以需要他的一个实现类 SimpleAuthenticationInfo
            /**
             * 创建安全数据对象，并返回，安全数据对象，就是数据库中查询出的数据
             * new SimpleAuthenticationInfo(Object,Object,String);
             * 参数一：安全数据，数据库中的用户
             * 参数二：安全对象的密码，数据库中的密码
             * 参数三：realm域的名称(任意 只要唯一即可) this.getName()表示当前类的名称
             */
            AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());

            return info;
        }else {
            // 如果没有用户直接返回 null  表示登陆 失败
            return null;
        }

    }
}
