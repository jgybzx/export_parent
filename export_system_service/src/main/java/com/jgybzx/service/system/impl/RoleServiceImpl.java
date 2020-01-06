package com.jgybzx.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.system.RoleDao;
import com.jgybzx.domain.system.Role;
import com.jgybzx.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/6 9:09
 * @Description: ${TODO}
 * @version:
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public PageInfo findByPage(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page,size);
        List<Role> list = roleDao.findAll(companyId);
        return new PageInfo(list);
    }

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public void save(Role role) {
        role.setId(UUID.randomUUID().toString());
        roleDao.save(role);
    }

    @Override
    public void update(Role role) {

        roleDao.update(role);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }
}
