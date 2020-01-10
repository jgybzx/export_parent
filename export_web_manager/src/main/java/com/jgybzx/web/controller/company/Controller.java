package com.jgybzx.web.controller.company;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.company.Company;
import com.jgybzx.service.company.CompanyService;
import com.jgybzx.web.controller.base.BaseController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author: guojy
 * @date: 2020/1/2 18:13
 * @Description: ${TODO}
 * @version:
 */
@Service
@RequestMapping("/company")
public class Controller extends BaseController {

    @Autowired
    private CompanyService companyService;

    /**
     * 查询企业信息
     */
    @RequestMapping(value = "/list", name = "查询企业信息")
    public String findAll(@RequestParam(defaultValue = "1") String page,
                          @RequestParam(defaultValue = "10") String size) {
        int pageNumInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(size);

        //int i = 1/0;//模拟异常
        ///PageResult pageResult = companyService.findPage(pageNumInt, pageSizeInt);
        PageInfo pageInfo = companyService.findPageByPageHelper(pageNumInt,pageSizeInt);
        request.setAttribute("page", pageInfo);
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
     * 企业信息新增或修改
     *
     * @param company
     * @return
     */
    @RequestMapping(value = "/edit", name = "企业新增或修改")
    public String edit(Company company) {
        // 数据库未设置id为自增，所以新增的时候为空
        // 根据id是否为空判断，新增或修改
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
    public String toUpdate(String id, String pageNum, String pageSize) {
        Company company = companyService.findById(id);
        request.setAttribute("company", company);

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
