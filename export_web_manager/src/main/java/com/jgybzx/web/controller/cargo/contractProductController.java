package com.jgybzx.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.cargo.ContractProduct;
import com.jgybzx.domain.cargo.ContractProductExample;
import com.jgybzx.domain.cargo.Factory;
import com.jgybzx.domain.cargo.FactoryExample;
import com.jgybzx.service.cargo.ContractProductService;
import com.jgybzx.service.cargo.FactoryService;
import com.jgybzx.web.controller.base.BaseController;
import com.jgybzx.web.controller.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/12 17:55
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/cargo/contractProduct")
public class contractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;
    @Reference
    private FactoryService factoryService;
    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * 合同下货物详情
     *
     * @param contractId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(String contractId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        // 下部的 货物列表信息
        ContractProductExample example = new ContractProductExample();
        ContractProductExample.Criteria criteria = example.createCriteria();
        // 查询条件是 合同id
        criteria.andContractIdEqualTo(contractId);
        PageInfo pageInfo = contractProductService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);

        // 准备 新增货物的 时候的生产厂家 下拉框的信息
        // 查询 co_factory ctyp = "货物"
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        // 查询条件 ctyp = "货物"
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        //3.新增货物的时候，最后新增完，跳转的还是 合同信息界面，然后要显示合同信息，需要一个合同id
        // 所以传递给 合同详细信息界面 一个id，放在表单的中，供新增完跳转使用
        request.setAttribute("contractId", contractId);


        // 跳转 合同货物详情界面
        return "cargo/product/product-list";
    }

    /**
     * 合同中的货物新增或修改
     *
     * @param contractProduct
     * @return
     */
    @RequestMapping(value = "/edit", name = "合同中的货物新增或修改")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) {
        try {
            contractProduct.setCompanyId(super.companyId);
            contractProduct.setCompanyName(super.companyName);
            // 此处遗留问题，更新无法获取图片
            ///String originalFilename = productPhoto.getOriginalFilename();
            ///System.out.println("图片名字"+originalFilename);
            // 文件上传到七牛云
            if (productPhoto != null) {
                String filepath = fileUploadUtil.upload(productPhoto);
                contractProduct.setProductImage(filepath);
            }
            if (StringUtils.isEmpty(contractProduct.getId())) {
                // id 为空新增
                contractProductService.save(contractProduct);
            } else {
                // id 不为空修改
                contractProductService.update(contractProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 保存完还是需要重定向到本页面，相当于再查一次，但是注意要有合同id才能查
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }

    /**
     * 跳转货物修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id) {
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct", contractProduct);
        // 获取下拉框数据
        // 准备 新增货物的 时候的生产厂家 下拉框的信息
        // 查询 co_factory ctyp = "货物"
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria = factoryExample.createCriteria();
        // 查询条件 ctyp = "货物"
        criteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        return "cargo/product/product-update";
    }

    /**
     * 删除货物  删除之后还是重定向到 合同货物详情页面，注意不要忘记传递合同id
     *
     * @param id
     * @param contractId
     * @return
     */
    @RequestMapping("delete")
    public String delete(String id, String contractId) {

        contractProductService.delete(id);
        // 保存完还是需要重定向到本页面，相当于再查一次，但是注意要有合同id才能查
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

}
