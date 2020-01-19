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
}