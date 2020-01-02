package com.jgybzx.test;

import com.jgybzx.dao.company.CompanyDao;
import com.jgybzx.domain.company.Company;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/2 17:24
 * @Description: ${TODO}
 * @version:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-dao.xml")
public class TestDemo {
    @Autowired
    private CompanyDao companyDao;
    @Test
    public void test(){
        System.out.println(companyDao.finAll());
    }
/*    @Test
    public void test() throws IOException {
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = build.openSession();
        CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
        List<Company> companies = mapper.finAll();

        System.out.println(companies);
    }*/


}
