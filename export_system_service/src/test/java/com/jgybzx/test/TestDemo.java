package com.jgybzx.test;

import com.jgybzx.domain.company.Company;
import com.jgybzx.service.company.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/2 18:02
 * @Description: ${TODO}
 * @version:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class TestDemo {
    @Autowired
    private CompanyService companyService;
    @Test
    public void test(){
        List<Company> all = companyService.findAll();
        System.out.println(all);
    }
}
