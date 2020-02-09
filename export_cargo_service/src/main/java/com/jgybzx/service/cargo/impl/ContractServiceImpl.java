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

import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * 1.购销合同中交货日期
     *             DeliveryPeriod字段
     *   以当前今天的时间为标准，如果有到期的购销合同，今天要交货，可以在早上8:00发出邮件
     *   SELECT *FROM co_contract WHERE DATE_FORMAT(delivery_period,'%d') = '01'
     * @param date
     * @param companyId
     * @return
     */
    @Override
    public List<String> findBydeliveryPeriod(Date date, String companyId) {

        // 获取日期 中的 day
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(date);
        format = "2020-01-01";
        contractDao.findBydeliveryPeriod(format,companyId);
        return null;
    }
}
