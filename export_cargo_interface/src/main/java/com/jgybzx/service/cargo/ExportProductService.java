package com.jgybzx.service.cargo;


import com.jgybzx.domain.export.ExportProduct;
import com.jgybzx.domain.export.ExportProductExample;

import java.util.List;


public interface ExportProductService {

	//根据条件查询
	List<ExportProduct> findAll(ExportProductExample example);
}
