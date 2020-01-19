package com.jgybzx.web.controller.export;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.cargo.ContractExample;
import com.jgybzx.domain.export.Export;
import com.jgybzx.domain.export.ExportExample;
import com.jgybzx.domain.export.ExportProduct;
import com.jgybzx.domain.export.ExportProductExample;
import com.jgybzx.service.cargo.ContractService;
import com.jgybzx.service.cargo.ExportProductService;
import com.jgybzx.service.cargo.ExportService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/16 13:39
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ContractService contractService;

    @Reference
    private ExportProductService exportProductService;

    /**
     * 查询公司下，状态是已上报的合同 state = 1
     *
     * @return
     */
    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "5") Integer size) {

        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        criteria.andStateEqualTo(1);
        PageInfo info = contractService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/export/export-contractList";
    }

    /**
     * 合同管理界面。报运按钮跳转
     * 1.基本的报运信息
     * 2.将购销合同数据转换成报运单数据
     * 3.将购销合同下货物数据转换成报运单下货物数据
     * 4.将购销合同下附件数据转换成报运单下附件数据
     *
     * @return
     */
    @RequestMapping("/toExport")
    public String toExport(String id) { //接收了多个购销合同id 以逗号方式拼接
        System.out.println(id);
        //传入 购销合同的id 方便一会报运使用
        // <input type="text" name="contractIds" value="${id}"> 页面使用 存入隐藏域中
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }

    /**
     * 新增或修改报运单
     * 新增保运单需要做的事情，查询合同，合同下的货物，合同下的附件
     *
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Export export) {
        export.setCompanyId(super.companyId);
        export.setCompanyName(super.companyName);
        if (StringUtils.isEmpty(export.getId())) {
            // 新增出口报运单
            exportService.save(export);
        } else {
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    @Reference
    private ExportService exportService;

    /**
     * 出口报运  数据查询
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        //1.查询出口报运单的数据
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/export/export-list";
    }

    /**
     * 跳转出口报运详细数据查看界面
     *
     * @param id
     * @return
     */
    @RequestMapping("toView")
    public String toView(String id) {
        // 根据id 查询报运数据
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        return "cargo/export/export-view";
    }

    /**
     * 跳转报运单修改界面
     *
     * @param id
     * @return
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id) {
        // 根据id 查询报运数据
        Export export = exportService.findById(id);
        System.out.println("export = " + export);
        request.setAttribute("export", export);
//        request.setAttribute("eps", list);
        return "cargo/export/export-update";
    }
}
