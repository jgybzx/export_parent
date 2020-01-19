package com.jgybzx.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.cargo.*;
import com.jgybzx.service.cargo.ExtCproductService;
import com.jgybzx.service.cargo.FactoryService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/12 23:12
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {
    @Reference
    private ExtCproductService extCproductService;
    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(String contractProductId,
                       String contractId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {

        // ============合同下附件详情==========
        // 获取工厂信息
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);
        // ============合同下附件详情==========

        // ============附件列表===============
        // 根据货物id 查询附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);
        PageInfo info = extCproductService.findAll(extCproductExample, page, size);
        request.setAttribute("page", info);
        // ============附件列表===============

        // 设置合同id和货物id
        request.setAttribute("contractProductId", contractProductId);
        request.setAttribute("contractId", contractId);

        return "cargo/extc/extc-list";
    }

    /**
     * 合同中的货物下的附件新增或修改
     *
     * @param extCproduct
     * @param productPhoto
     * @return
     */
    @RequestMapping(value = "/edit", name = "合同中的货物的附件新增或修改")
    public String edit(ExtCproduct extCproduct, MultipartFile productPhoto) {
        extCproduct.setCompanyId(super.companyId);
        extCproduct.setCompanyName(super.companyName);
        if (StringUtils.isEmpty(extCproduct.getId())) {
            extCproductService.save(extCproduct);
        } else {
            extCproductService.update(extCproduct);
        }
        // 保存完还是需要重定向到本页面，相当于再查一次，但是注意要货物id和公司id才能查
        return "redirect:/cargo/extCproduct/list.do?contractId=" + extCproduct.getContractId() + "&contractProductId=" + extCproduct.getContractProductId();
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id, String contractId, String contractProductId) {
        //1.根据id查询当前的附件信息
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct" , extCproduct);
        //2.附件厂家数据
        FactoryExample factoryExample= new FactoryExample();
        FactoryExample.Criteria exampleCriteria = factoryExample.createCriteria();
        exampleCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList" , factoryList);

        return "cargo/extc/extc-update";
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id, String contractId, String contractProductId) {
        //根据id删除
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId=" + contractId + "&contractProductId=" + contractProductId;
    }
}
