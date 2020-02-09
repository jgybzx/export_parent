package com.jgybzx.dao.cargo;


import com.jgybzx.domain.cargo.Contract;
import com.jgybzx.domain.cargo.ContractExample;
import com.jgybzx.domain.vo.ContractProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);
    //查询某个公司的某个时间短的所有出货信息
    List<ContractProductVo> findByShipTime(@Param("inputDate") String inputDate, @Param("companyId") String companyId);

    // 1.购销合同中交货日期
    //     *             DeliveryPeriod字段
    //     *   以当前今天的时间为标准，如果有到期的购销合同，今天要交货，可以在早上8:00发出邮件
    void findBydeliveryPeriod(@Param("data") String date, @Param("companyId") String companyId);
}