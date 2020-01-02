package com.jgybzx.web.controller.company;

import com.jgybzx.domain.company.Company;
import com.jgybzx.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/2 18:13
 * @Description: ${TODO}
 * @version:
 */
@Service
@RequestMapping("/company")
public class CompanyController {


    @RequestMapping("/test")
    public String test() {
        return "success";
    }

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        List<Company> companyList = companyService.findAll();
        request.setAttribute("companyList", companyList);
        return "forward:/companyList.jsp";
    }
}
