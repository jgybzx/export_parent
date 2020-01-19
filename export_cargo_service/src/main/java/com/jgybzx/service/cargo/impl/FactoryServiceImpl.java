package com.jgybzx.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jgybzx.dao.cargo.FactoryDao;
import com.jgybzx.domain.cargo.Factory;
import com.jgybzx.domain.cargo.FactoryExample;
import com.jgybzx.service.cargo.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/12 14:16
 * @Description: ${TODO}
 * @version:
 */
@Service
public class FactoryServiceImpl implements FactoryService {
    @Autowired
    private FactoryDao factoryDao;
    @Override
    public void save(Factory factory) {
        
    }

    @Override
    public void update(Factory factory) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Factory findById(String id) {
        return null;
    }

    @Override
    public List<Factory> findAll(FactoryExample example) {
        List<Factory> factoryList = factoryDao.selectByExample(example);
        return factoryList;
    }
}
