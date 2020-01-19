package com.jgybzx.dao.export;


import com.jgybzx.domain.export.ExportProduct;
import com.jgybzx.domain.export.ExportProductExample;

import java.util.List;

public interface ExportProductDao {
	/**
	 * 根据id删除
	 */
    int deleteByPrimaryKey(String id);

	/**
	 * 保存
	 */
    int insertSelective(ExportProduct record);

	/**
	 * 条件查询
	 */
    List<ExportProduct> selectByExample(ExportProductExample example);
    List<ExportProduct> selectByExportId(String  exportId);

	/**
	 * 根据id查询
	 */
    ExportProduct selectByPrimaryKey(String id);

	/**
	 * 更新
	 */
    int updateByPrimaryKeySelective(ExportProduct record);
}