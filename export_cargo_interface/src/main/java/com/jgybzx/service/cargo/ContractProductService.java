package com.jgybzx.service.cargo;



import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.cargo.ContractProduct;
import com.jgybzx.domain.cargo.ContractProductExample;

import java.util.List;
import java.util.Map;

/**
 * 业务层接口
 */
public interface ContractProductService {

	/**
	 * 保存
	 */
	void save(ContractProduct contractProduct);

	/**
	 * 更新
	 */
	void update(ContractProduct contractProduct);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	ContractProduct findById(String id);

	/**
	 * 分页查询
	 */
	PageInfo findAll(ContractProductExample example, int page, int size);

	/**
	 * 根据合同id删除
	 * @param ContractID
	 */
	void deleteByContractID(String ContractID);

	/**
	 * 货物上传保存
	 * @param list
	 */
    public void saveList(List<ContractProduct> list);


}
