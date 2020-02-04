package com.jgybzx.web.controller.export;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.BaseEntity;
import com.jgybzx.domain.cargo.ContractExample;
import com.jgybzx.domain.export.Export;
import com.jgybzx.domain.export.ExportExample;
import com.jgybzx.domain.export.ExportProduct;
import com.jgybzx.domain.export.ExportProductExample;
import com.jgybzx.domain.vo.ExportProductVo;
import com.jgybzx.domain.vo.ExportResult;
import com.jgybzx.domain.vo.ExportVo;
import com.jgybzx.service.cargo.ContractService;
import com.jgybzx.service.cargo.ExportProductService;
import com.jgybzx.service.cargo.ExportService;
import com.jgybzx.web.controller.base.BaseController;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
            // 一旦保存会提交两种数据  一种报运单数据，另一种报运单下货物数据

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
        List<ExportProduct> list = export.getExportProducts();
        System.out.println("list = " + list);
        request.setAttribute("eps", list);
        return "cargo/export/export-update";
    }

    /**
     * 电子保运
     * @param id
     * @return
     */
    @RequestMapping("exportE")
    public String exportE(String id){
        //1、根据id查询报运数据，构建海关数据
        Export export = exportService.findById(id);
        // 2、查询报运单货物数据
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> ExportProductList = exportProductService.findAll(exportProductExample);

        // 3、构建海关需要的ExportVo,
        ExportVo exportVo = new ExportVo();
        /**
         * 实体类是海关提供的，所以需要我们构建实体类
         * exportVo.set方法(export.get方法);
         * 由于是我们自己编写的代码，所以ExportVo和Export中的属性差不多一致
         * 但是实际开发中，需要自己一个一个的构建
         */
        // 只能构建基本数据，属性不同的需要我们自己构建
        BeanUtils.copyProperties(export,exportVo);
        exportVo.setExportId(export.getId());
        // 4、构建海关需要的货物数据
        List<ExportProductVo> exportProductVoList = new ArrayList<>();
        for (ExportProduct exportProduct : ExportProductList) {
            ExportProductVo exportProductVo = new ExportProductVo();
            BeanUtils.copyProperties(exportProduct,exportProductVo);
            // 特殊数据
            exportProductVo.setExportProductId(exportProduct.getId());
            // 建立VO之间的关系
            exportProductVo.setExportId(export.getId());
            exportProductVo.setEid(export.getId());
            // 将货物Vo放入集合中
            exportProductVoList.add(exportProductVo);
        }
        exportVo.setProducts(exportProductVoList);

        // 5、发送数据
        WebClient client = WebClient.create("http://localhost:9096/ws/export/user");
        client.post(exportVo);
        // 6、接受返回结果，再次调用另一个接口
        client = WebClient.create("http://localhost:9096/ws/export/user/"+id);
        ExportResult exportResult = client.get(ExportResult.class);
        // 7、修改报运单状态
        if (exportResult.getState()==2){
            // 报运成功，更新报运单
            System.out.println("更新报运单");
            exportService.exportE(exportResult);
        }
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 更新报运单状态 草稿 -》 已提交
     * @param id
     * @return
     */
    @RequestMapping("submit")
    public String submit(String id ){
        Export export = new Export();
        export.setId(id);
        export.setState(1);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }
    /**
     * 更新报运单状态 已提交  -》 草稿
     * @param id
     * @return
     */
    @RequestMapping("cancel")
    public String cancel(String id ){
        Export export = new Export();
        export.setId(id);
        export.setState(0);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

}
