package com.jgybzx.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.cargo.ContractDao;
import com.jgybzx.dao.cargo.ExtCproductDao;
import com.jgybzx.domain.cargo.Contract;
import com.jgybzx.domain.cargo.ExtCproduct;
import com.jgybzx.domain.cargo.ExtCproductExample;
import com.jgybzx.service.cargo.ExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/14 17:18
 * @Description: ${TODO}
 * @version:
 */
@Service
public class ExtCproductServiceImpl implements ExtCproductService {
    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;

    /**
     * 添加附件对象
     * 1.附件表
     *   1.1 附件的数量和单价 计算总金额
     *   1.2 赋值随机的id
     *   1.3 正常保存附件表即可
     * 2.合同表
     *   2.1 根据附件对象获得合同对象
     *   2.2 修改合同的总金额
     *   2.3 修改合同中附件的种类数量
     *   2.4 保存合同
     *
     * @param extCproduct
     */
    @Override
    public void save(ExtCproduct extCproduct) {
        extCproduct.setId(UUID.randomUUID().toString());
        Double amount = 0d;
        if (extCproduct.getPrice() != null && extCproduct.getCnumber() != null) {
            amount = extCproduct.getPrice() * extCproduct.getCnumber();
        }
        extCproduct.setAmount(amount);
        extCproductDao.insertSelective(extCproduct);
        // 合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        contract.setExtNum(contract.getExtNum() + 1);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 修改附件
     *   最开始查询到原来的附件对象
     * 1.附件表
     *   1.1 计算修改之后的总金额
     *   1.2 重新赋值回去
     *   1.3 直接修改即可
     * 2.合同表
     *   2.1 根据附件得到合同对象
     *   2.2 修改合同的总金额= 合同总金额 - 原来的附件金额 + 新增加的附件金额
     *   2.3 修改合同即可
     *
     * @param extCproduct
     */
    @Override
    public void update(ExtCproduct extCproduct) {
        System.out.println("更新附件");
    }

    /**
     * 1.附件表
     *   1.1 获得附件即可
     *   1.2 删除附件
     * 2.合同表
     *   2.1 获得合同对象
     *   2.2 修改合同对象的总金额
     *   2.3 修改合同对象附件数量
     *   2.4 修改合同
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        extCproductDao.deleteByPrimaryKey(id);

        // 更新合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
        contract.setExtNum(contract.getExtNum() - 1);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void deleteByContractID(String ContractID) {

    }
}
