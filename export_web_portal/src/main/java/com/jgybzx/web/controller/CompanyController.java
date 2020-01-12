package com.jgybzx.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jgybzx.domain.company.Company;
import com.jgybzx.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: guojy
 * @date: 2020/1/11 20:26
 * @Description: ${TODO}
 * @version:
 */
@Controller
public class CompanyController {
    @Reference
    private CompanyService companyService;
    @RequestMapping("/apply")
    @ResponseBody
    public  String apply(Company company) {
        try {
            companyService.save(company);
            return "1";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "2";
    }
}
