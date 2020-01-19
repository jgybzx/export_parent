package com.jgyzbx.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author: guojy
 * @date: 2020/1/11 20:01
 * @Description: ${TODO}
 * @version:
 */
public class CompanyProvider {
    public static void main(String[] args) throws IOException {
        System.out.println("加载配置文件");
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        context.start();
        System.out.println("启动成功");
        System.in.read(); // 按任意键退出
    }
}
