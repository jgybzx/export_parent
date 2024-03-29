package com.jgybzx.dao.cargo;


import com.jgybzx.domain.cargo.ContractProduct;
import com.jgybzx.domain.cargo.ContractProductExample;

import java.util.List;

public interface ContractProductDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(ContractProduct record);

	//条件查询
    List<ContractProduct> selectByExample(ContractProductExample example);

	//id查询
    ContractProduct selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(ContractProduct record);

    /**
     * 根据 合同id删除
     * @param contractID
     */
    void deleteByContractID(String contractID);
    
}