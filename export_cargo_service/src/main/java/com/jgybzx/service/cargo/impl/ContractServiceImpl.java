package com.jgybzx.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.cargo.ContractDao;
import com.jgybzx.domain.cargo.Contract;
import com.jgybzx.domain.cargo.ContractExample;
import com.jgybzx.domain.vo.ContractProductVo;
import com.jgybzx.service.cargo.ContractService;
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
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractDao contractDao;
    @Override
    public Contract findById(String id) {
        System.out.println("查询数据");
        Contract contract = contractDao.selectByPrimaryKey(id);
        return contract;
    }

    @Override
    public void save(Contract contract) {
        System.out.println("保存方法执行");
        // 一些不属于用户填写的字段，需要手动赋值
        contract.setId(UUID.randomUUID().toString());
        contract.setTotalAmount(0d);//总金额
        contract.setState(0);//草稿状态
        contract.setProNum(0);//货物种类数量
        contract.setExtNum(0);//附件种类数量
        contractDao.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        System.out.println("删除合同");
        contractDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ContractExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Contract> list = contractDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public List<ContractProductVo> findByShipTime(String inputDate, String companyId) {
        // 查询某个公司的所有出货信息
        List<ContractProductVo> list = contractDao.findByShipTime(inputDate, companyId);
        return list;
    }
}
