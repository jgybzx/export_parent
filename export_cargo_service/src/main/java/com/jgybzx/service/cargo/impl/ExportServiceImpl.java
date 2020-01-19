package com.jgybzx.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.cargo.ContractDao;
import com.jgybzx.dao.cargo.ContractProductDao;
import com.jgybzx.dao.cargo.ExtCproductDao;
import com.jgybzx.dao.export.ExportDao;
import com.jgybzx.dao.export.ExportProductDao;
import com.jgybzx.dao.export.ExtEproductDao;
import com.jgybzx.domain.cargo.*;
import com.jgybzx.domain.export.*;
import com.jgybzx.service.cargo.ExportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author: guojy
 * @date: 2020/1/16 14:42
 * @Description: ${TODO}
 * @version:
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao; //合同货物dao

    @Autowired
    private ExportProductDao exportProductDao;//报运单商品dao

    @Autowired
    private ExtCproductDao extCproductDao;  //合同附件Dao

    @Autowired
    private ExtEproductDao extEproductDao;  //报运单附件Dao


    @Override
    public Export findById(String id) {
        Export export = exportDao.selectByPrimaryKey(id);
        return export;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Export> list = exportDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void update(Export export) {

    }

    /**
     * 新增出口保运单：不仅仅是保存到数据库，需要做很多
     * 表关系：本次业务设计6张表，
     * 数据来源：合同表，合同货物表，合同附件表
     * 数据组装：报运单表，报运货物表，报运附件表
     * 1、将合同数据(Contract)转化为报运单(Export)数据
     * 2、将合同货物数据(ContractProduc)转化为报运货物数据(ExportProduct)
     * 3、将合同附件数据转(ExtCproduct)化为报运附件数据(ExtEproduct)
     * 最后将保存的合同数据状态改为已报运 state = 2
     *
     * @param export
     */
    @Override
    public void save(Export export) {
        //1、将合同数据(Contract)转化为报运单(Export)数据

        //1.1 查询购销合同
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractExampleCriteria = contractExample.createCriteria();
        // 将合同id转化为集合，用于条件查询
        List<String> ContractIdList = Arrays.asList(export.getContractIds().split(","));
        contractExampleCriteria.andIdIn(ContractIdList);// example条件查询，in语法，需要一个集合
        List<Contract> contractList = contractDao.selectByExample(contractExample);


        // 1.2 构建报运单,页面中没有填写的数据：合同号，制单日期，所有货物数量，所有附件数量，默认状态：0
        export.setId(UUID.randomUUID().toString());// 报运单id
        export.setState(0);// 默认草稿
        export.setInputDate(new Date());// 制单日期
        String contractNO = "";// 拼接合同号
        for (Contract contract : contractList) {

            contractNO += contract.getContractNo() + ",";

            // 注意要设置购销合同状态 为已报运
            contract.setState(2);
            contractDao.updateByPrimaryKeySelective(contract);
        }
        export.setCustomerContract(contractNO);// 合同号

        //export.setExtNum();// 所有附件数量
        //export.setProNum();// 所有货物数量
        // ==============================合同数据保存完毕========================

        // ==============================货物数据========================

        //2、将合同货物数据(ContractProduc)转化为报运货物数据(ExportProduct)
        // 2.1 根据合同id，查询货物信息，封装到报运货物对象中，并保存
        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria contractProductExampleCriteria = contractProductExample.createCriteria();
        contractProductExampleCriteria.andContractIdIn(ContractIdList);
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);
        //此map就是用来描述 合同货物id & 报运货物id 之间的关系
        Map<String , String> idMap =new HashMap<String , String>();
        // 2.2封装报运单 货物数据
        for (ContractProduct contractProduct : contractProductList) {
            ExportProduct exportProduct = new ExportProduct();
            // 由于合同下货物和报运单货物 字段起的差不多
            // 直接  BeanUtils.copyProperties，copyProperties : 将参数1的数据赋值给参数2的数据( 字段名称一致 )
            BeanUtils.copyProperties(contractProduct, exportProduct);
            // 设置每一个报运货物的id
            exportProduct.setId(UUID.randomUUID().toString());
            // 2.4设置报运单数据和货物之间的关系
            exportProduct.setExportId(export.getId());
            // 2.3 保存报运单数据
            exportProductDao.insertSelective(exportProduct);
            // 2.4 设置合同货物id & 报运货物id 之间的关系
            idMap.put(contractProduct.getId(),exportProduct.getId());
        }
        // ==============================货物数据保存完毕========================

        // ==============================附件数据========================
        // 1.查询附件数据
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria extCproductExampleCriteria = extCproductExample.createCriteria();
        extCproductExampleCriteria.andContractIdIn(ContractIdList);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        // 2.转换附件数据
        for (ExtCproduct extCproduct : extCproductList) {
            ExtEproduct extEproduct = new ExtEproduct();
            BeanUtils.copyProperties(extCproduct,extEproduct);
            extEproduct.setId(UUID.randomUUID().toString());
            // 建立报运单附件和报运单货物的关系，需要借助 合同附件和合同货物的关系，
            /**
             * 货物在转换到时候，可以将两者（合同货物&报运货物）的id放到map中，Map<合同货物id，报运货物id>
             * 由于合同附件和合同货物之间有关系，所以在附件转换的时候，合同附件可以拿到合同货物，再经过map，可以拿到对应的报运货物id
             * 所以 就可以得到报运附件和报运货物之间的关系，
             * 需要在货物转换的时候，构建map
             */
            // 获得合同货物id
            String contractProductId = extCproduct.getContractProductId();
            // 通过map获取 报运货物id,并建立关系
            extEproduct.setExportProductId(idMap.get(contractProductId));
            // 建立报运单附件和报运单的关系
            extEproduct.setExportId(export.getId());
            // 保存数据
            extEproductDao.insertSelective(extEproduct);
        }
        // ==============================附件数据保存完毕========================

        export.setExtNum(extCproductList.size());// 所有附件数量
        export.setProNum(contractProductList.size());// 所有货物数量
        // 保存报运对象
        exportDao.insertSelective(export);
    }
}
