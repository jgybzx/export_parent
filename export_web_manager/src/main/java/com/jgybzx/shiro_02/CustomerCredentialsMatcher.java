package com.jgybzx.shiro_02;

import com.jgybzx.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author: guojy
 * @date: 2020/1/10 15:46
 * @Description: ${TODO}
 * @version:
 */
public class CustomerCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     * AuthenticationToken 认证数据
     * AuthenticationInfo 安全数据
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("自定义密码比较器2执行");
        // 取出认证数据的 密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String email = upToken.getUsername();
        char[] password = upToken.getPassword();
        String pass = new String(password);
        // 加密
        pass=Encrypt.md5(pass,email);

        // 从安全对象中取出安全数据的密码
        String credentials = (String) info.getCredentials();

        // 比较
        return pass.equals(credentials);
    }
}
