package com.jgybzx.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.cargo.Contract;
import com.jgybzx.domain.cargo.ContractExample;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.cargo.ContractProductService;
import com.jgybzx.service.cargo.ContractService;
import com.jgybzx.service.cargo.ExtCproductService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: guojy
 * @date: 2020/1/12 16:05
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {

        // example对象，支持各种简单条件查询
        ContractExample example = new ContractExample();
        // setOrderByClause 表示按照 create_time 排序插查询
        example.setOrderByClause("create_time desc");
        // example.createCriteria(); 可以用来设置根据每个字段的查询
        ContractExample.Criteria criteria = example.createCriteria();
        // =================细粒度权限查询=================

        /**
         * 根据 degree 字段判断当前登陆人的身份
         *  0作为内部控制，租户企业不能使用
         *      0-saas管理员
         *      1-企业管理员
         *      2-管理所有下属部门和人员
         *      3-管理本部门
         *      4-普通员工
         */
        // 当前登陆用户
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser.getDegree() == 1) {
            // 企业管理员:应该可以看到本企业下的所有数据，查询条件：where 企业id = 登陆人企业id；
            criteria.andCompanyIdEqualTo(super.companyId);
        } else if (loginUser.getDegree() == 2) {
            // 区域管理员 看到本区域下的所有部门的数据，比如华东地区部门id应该是：100100，其下属部门的全部都是 100100***，所以使用通配符
            criteria.andCreateDeptLike( loginUser.getDeptId() + "%" );
        } else if (loginUser.getDegree() == 3) {
            // 部门管理员 看到本部门下的数据，where 创建人所属部门 = ‘登陆人部门’
            criteria.andCreateDeptEqualTo(loginUser.getDeptId());
        } else if (loginUser.getDegree() == 4) {
            // 普通 员工 只能看到自己的
            criteria.andCreateByEqualTo(loginUser.getId());
        }
        // =================细粒度权限查询=================
        PageInfo pageInfo = contractService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/contract/contract-list";
    }

    /**
     * 跳转合同新增页面
     *
     * @return
     */
    @RequestMapping(value = "/toAdd", name = "跳转合同新增页面")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }

    /**
     * 跳转合同修改页面
     *
     * @return
     */
    @RequestMapping(value = "/toUpdate", name = "跳转合同修改页面")
    public String toUpdate(String id) {
        // 根据id查询合同数据
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-update";
    }

    /**
     * 合同信息新增或修改
     *
     * @param contract
     * @return
     */
    @RequestMapping(value = "/edit", name = "合同新增或修改")
    public String edit(Contract contract) {
        contract.setCompanyId(super.companyId);
        contract.setCompanyName(super.companyName);
        // 数据库未设置id为自增，所以新增的时候为空
        // 根据id是否为空判断，新增或修改
        if (StringUtils.isEmpty(contract.getId())) {
            User loginUser =(User) session.getAttribute("loginUser");
            contract.setCreateBy(loginUser.getId());//创建人
            contract.setCreateDept(loginUser.getDeptId()); // 创建人所在的部门
            contractService.save(contract);
        } else {
            System.out.println("修改方法");
            System.out.println(contract);
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 提交合同状态 状态由 0 变为1
     * 草稿:0
     * 已上报:1
     * 已报运:2
     *
     * @param id
     * @return
     */
    @RequestMapping("submit")
    public String submit(String id) {
        Contract contract = contractService.findById(id);
        contract.setState(1);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping("cancel")
    public String cancel(String id) {
        Contract contract = contractService.findById(id);
        contract.setState(0);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * delete 删除合同
     *
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public String delete(String id) {
        System.out.println("删除方法" + id);
        // 查询合同对象
        Contract contract = contractService.findById(id);
        // 删除合同表数据
        contractService.delete(id);
        // 删除 合同的货物数据
        contractProductService.deleteByContractID(contract.getId());
        // 删除 货物的附加数据
        extCproductService.deleteByContractID(contract.getId());
        return "redirect:/cargo/contract/list.do";
    }

    @Reference
    private ContractProductService contractProductService;
    @Reference
    private ExtCproductService extCproductService;
}
