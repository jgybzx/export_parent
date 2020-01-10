package com.jgybzx.shiro;

import com.jgybzx.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author: guojy
 * @date: 2020/1/9 14:40
 * @Description: 继承 SimpleCredentialsMatcher
 * @version:
 */
public class CustomerCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     * 重写doCredentialsMatch方法
     * 执行我们自己的密码比较方法
     * 方法名：执行密码比较
     * 返回值：布尔，密码是否比对成功
     * 参数一：AuthenticationToken 用户输入的用户名和密码对象
     * 参数二：AuthenticationInfo 自定义认证器的返回值，数据库中的数据
     * @param token 用户输入的用户名和密码对象
     * @param info  自定义认证器的返回值
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("自定义密码认证器执行");
        // 获取用户输入的用户名和密码对象 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String email = upToken.getUsername();
        char[] pass = upToken.getPassword();
        String password = new String(pass);
        // 加密
        password = Encrypt.md5(password, email);

        // 获取 安全对象中的密码，也就是从数据中取出来的密码
        String credentials = (String) info.getCredentials();

        // 比较两个密码是否相同
        if (credentials != null && password != null) {

            return password.equals(credentials);
        } else {
            return false;
        }
    }
}
