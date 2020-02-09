package com.jgybzx.service.cargo;


import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.cargo.Contract;
import com.jgybzx.domain.cargo.ContractExample;
import com.jgybzx.domain.vo.ContractProductVo;

import java.util.Date;
import java.util.List;


public interface ContractService {

	//根据id查询
    Contract findById(String id);

    //保存
    void save(Contract contract);

    //更新
    void update(Contract contract);

    //删除
    void delete(String id);

    //分页查询
	PageInfo findAll(ContractExample example, int page, int size);

    /**
     * 根据船期，货物某一月的数据
     * @param inputDate
     * @param companyId
     * @return
     */
    List<ContractProductVo> findByShipTime(String inputDate, String companyId);

    /**
     * 1.购销合同中交货日期
     *             DeliveryPeriod字段
     *   以当前今天的时间为标准，如果有到期的购销合同，今天要交货，可以在早上8:00发出邮件
     * @param date
     * @param companyId
     * @return
     */
    List<String> findBydeliveryPeriod(Date date, String companyId);
}
