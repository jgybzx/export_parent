package com.jgybzx.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.cargo.ContractDao;
import com.jgybzx.dao.cargo.ContractProductDao;
import com.jgybzx.dao.cargo.ExtCproductDao;
import com.jgybzx.domain.cargo.*;
import com.jgybzx.service.cargo.ContractProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/12 14:15
 * @Description: ${TODO}
 * @version:
 */

@Service
public class ContractProductServiceImpl implements ContractProductService {
    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ExtCproductDao extCproductDao;

    /**
     * 货物新增方法
     * 1、需要货物表新增数据
     * --1.1 赋值货物id
     * --1.2 获取单价和数量，计算货物总价
     * --1.3 正常保存
     * 2、需要修改订单，修改订单金额=原金额+货物的单价*数量
     * --2.1 获得合同对象，得到原始金额
     * --2.2 设置增加货物之后的总金额
     * --2.3 计算货物的数量，此处是货物的种类 直接原种类+1
     *
     * @param contractProduct
     */
    @Override
    public void save(ContractProduct contractProduct) {

        System.out.println("货物保存方法");
        // 1、需要货物表新增数据
        // 1.1 赋值货物id
        contractProduct.setId(UUID.randomUUID().toString());
        // 1.2 获取单价和数量，计算货物总价
        Double amount = 0D;
        if (contractProduct.getPrice() != null && contractProduct.getCnumber() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        contractProduct.setAmount(amount);
        // 1.3 正常保存
        contractProductDao.insertSelective(contractProduct);

        // 2、需要修改订单，修改订单金额=原金额+货物的单价*数量
        // 2.1 获得合同对象，得到原始金额
        String contractId = contractProduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        // 2.2 设置增加货物之后的总金额 订单金额=原金额+货物的单价*数量
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        // 2.3 计算货物的数量，此处是货物的种类 直接原种类+1
        contract.setProNum(contract.getProNum() + 1);
        // 更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 更新货物
     * 修改货物表之前 需要先查询原来货物的总金额
     * 1.货物表
     * 1.1 获得当前货物修改完的总金额-计算得到
     * 1.2 修改数据库中的货物表中总金额
     * 2.合同表
     * 2.1 拿到合同的对象
     * 2.2 赋值总金额-计算
     * 2.3 修改合同对象
     *
     * @param contractProduct
     */
    @Override
    public void update(ContractProduct contractProduct) {
        System.out.println("更新货物");
        // 更新货物
        //修改货物表之前 需要先查询原来货物的总金额
        ContractProduct oldContractProduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());

        Double amount = 0d;
        //1.货物表
        //1.1 获得当前货物修改完的总金额-计算得到
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        System.out.println("旧货物金额" + amount);
        //1.2 修改数据库中的货物表中总金额
        contractProduct.setAmount(amount);
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        //2.合同表
        //2.1 拿到合同的对象
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //2.2 赋值总金额-计算 先减旧的货物金额 后加新的货物金额
        contract.setTotalAmount(contract.getTotalAmount() - oldContractProduct.getAmount() + amount);
        //2.3 修改合同对象
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除货物
     * 删除货物
     * 1.货物表-删除操作
     * -- 1.1 先将这个货物表中的对象查询出来
     * -- 1.2 正常删除即可
     * 2.附件表-删除操作
     * -- 2.1 根据货物表对象查询到当前附件数据
     * -- 2.2 删除附件数据即可
     * 3.合同表-修改操作
     * -- 3.1 根据货物对象查询到合同对象
     * -- 3.2 修改总金额
     * -- 3.3 修改货物种类数量 和 附件种类数量
     * -- 3.4 保存到数据库中即可
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        ContractProduct oldContractProduct = contractProductDao.selectByPrimaryKey(id);
        // 删除货物数据
        contractProductDao.deleteByPrimaryKey(id);

        Double amount = oldContractProduct.getAmount();
        // 获取货物对应的附件 list集合，计算附件总价格
        ExtCproductExample example = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = example.createCriteria();
        criteria.andContractProductIdEqualTo(oldContractProduct.getId());
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(example);
        // 循环获取金额，并删除
        for (ExtCproduct extCproduct : extCproductList) {
            amount += extCproduct.getAmount();
            // 删除附件数据
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }


        // 更新合同数据 总金额，货物数量，附件数量
        // 获取合同
        Contract contract = contractDao.selectByPrimaryKey(oldContractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() - amount);
        contract.setProNum(contract.getProNum() - 1);
        contract.setExtNum(contract.getExtNum() - extCproductList.size());
        // 更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ContractProduct findById(String id) {
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        return contractProduct;
    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ContractProduct> list = contractProductDao.selectByExample(example);

        return new PageInfo(list);
    }

    /**
     * 根据合同id 删除
     *
     * @param contractID
     */
    @Override
    public void deleteByContractID(String contractID) {
        System.out.println("根据合同id删除货物");
        contractProductDao.deleteByContractID(contractID);
    }

    /**
     * 货物上传 excel保存
     * @param list
     */
    @Override
    public void saveList(List<ContractProduct> list) {
        System.out.println(list);
        for (ContractProduct contractProduct : list) {
            this.save(contractProduct);
        }
    }

}
