package com.jgybzx.web.controller.company;

import com.jgybzx.common.entity.PageResult;
import com.jgybzx.domain.company.Company;
import com.jgybzx.service.company.CompanyService;
import com.jgybzx.web.controller.base.BaseCompanyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class CompanyController extends BaseCompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 查询企业信息
     */
    @RequestMapping(value = "/list", name = "查询企业信息")
    public String findAll(@RequestParam(defaultValue = "1") String pageNum,
                          @RequestParam(defaultValue = "10") String pageSize) {
        int pageNumInt = 1;
        int pageSizeInt = 10;
        pageNumInt = Integer.parseInt(pageNum);
        pageSizeInt = Integer.parseInt(pageSize);

        //int i = 1/0;//模拟异常

        PageResult pageResult = companyService.findPage(pageNumInt, pageSizeInt);
        System.out.println(pageResult);
        request.setAttribute("pageResult", pageResult);
        return "company/company-list";
    }

    /**
     * 跳转企业新增页面
     *
     * @return
     */
    @RequestMapping(value = "/toAdd", name = "跳转企业新增页面")
    public String toAdd() {
        return "company/company-add";
    }

    /**
     * 新增或修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", name = "企业新增或修改")
    public String edit(Company company) {

        //数据库未设置id为自增，所以新增的时候为空
        //根据id是否为空判断，新增或修改
        if (StringUtils.isEmpty(company.getId())) {
            //新增
            companyService.save(company);
        } else {
            //修改
            companyService.update(company);
        }
        return "redirect:/company/list.do";
    }


    /**
     * 根据id查询企业信息，并跳转到修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdate", name = "跳转编辑页面")
    public String toUpdate(String id,String pageNum,String pageSize) {
        System.out.println(pageNum);
        System.out.println(pageSize);
        Company company = companyService.findById(id);
        request.setAttribute("company", company);
        request.setAttribute("pageNum",pageNum);
        request.setAttribute("pageSize",pageSize);

        return "company/company-update";
    }

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", name = "跳转编辑页面")
    public String delete(String id) {
        companyService.delete(id);
        return "redirect:/company/list.do";
    }


}
