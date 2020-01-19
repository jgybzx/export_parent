package com.jgybzx.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jgybzx.dao.export.ExportProductDao;
import com.jgybzx.domain.export.ExportProduct;
import com.jgybzx.domain.export.ExportProductExample;
import com.jgybzx.service.cargo.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/16 14:43
 * @Description: ${TODO}
 * @version:
 */
@Service
public class ExportProductServiceImpl implements ExportProductService {

    @Autowired
    private ExportProductDao exportProductDao;
    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        List<ExportProduct> list = exportProductDao.selectByExample(example);
        return list;
    }
}
