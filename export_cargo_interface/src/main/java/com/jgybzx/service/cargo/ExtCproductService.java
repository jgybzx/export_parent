package com.jgybzx.service.cargo;




import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.cargo.ExtCproduct;
import com.jgybzx.domain.cargo.ExtCproductExample;

import java.util.Map;

/**

 */
public interface ExtCproductService {
	/**
	 * 保存
	 */
	void save(ExtCproduct ExtCproduct);

	/**
	 * 更新
	 */
	void update(ExtCproduct ExtCproduct);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	ExtCproduct findById(String id);

	/**
	 * 分页查询
	 */
	PageInfo findAll(ExtCproductExample example, int page, int size);

	/**
	 * 根据合同id删除
	 * @param ContractID
	 */
	void deleteByContractID(String ContractID);
}
