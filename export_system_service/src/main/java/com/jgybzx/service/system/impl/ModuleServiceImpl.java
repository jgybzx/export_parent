package com.jgybzx.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.system.ModuleDao;
import com.jgybzx.domain.system.Module;
import com.jgybzx.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/6 14:33
 * @Description: ${TODO}
 * @version:
 */
@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo findByPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Module> list = moduleDao.findAll();

        return new PageInfo(list);
    }

    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    @Override
    public void save(Module module) {
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);

    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }
}
