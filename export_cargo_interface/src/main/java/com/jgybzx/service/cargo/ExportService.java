package com.jgybzx.service.cargo;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.export.Export;
import com.jgybzx.domain.export.ExportExample;
import com.jgybzx.domain.vo.ExportResult;


public interface ExportService {

    Export findById(String id);

    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo findAll(ExportExample example, int page, int size);

	//更新报运结果
	void exportE(ExportResult result);
}
