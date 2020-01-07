package com.jgybzx.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.system.ModuleDao;
import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.Role;
import com.jgybzx.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 根据 ctype 查找  父级模块
     * ctype="0"> 主菜单======无父级模块
     * ctype="1"> 二级菜单====主菜单
     * ctype="2"> 按钮========二级菜单
     *
     * @param ctype
     * @return
     */
    @Override
    public List<Module> findParent(String ctype) {
        List<Module> list = new ArrayList<>();
        // 只有 当 ctype= 1，2的时候才有父级目录
        String s1 = "1";
        String s2 = "2";
        if (ctype.equals(s1)) {
            list = moduleDao.findParent("0");
        } else if (ctype.equals(s2)) {
            list = moduleDao.findParent("1");
        }
        return list;
    }
    @Override
    public List<Module> findRoleModuleById(String roleId) {
        List<Module> list = new ArrayList<>();
        list=moduleDao.findRoleModuleById(roleId);
        return list;

    }

    @Override
    public void updateRoleModule(String roleId, String moduleIds) {
        //根据角色id 删除，角色模块中间表数据
        moduleDao.deleteRoleModule(roleId);
        // 构建书节点id数组
        String[] arrays = moduleIds.split(",");
        // 遍历向中间表保存数据
        for (String moduleId : arrays) {
            moduleDao.saveRoleModule(roleId,moduleId);
        }
    }
}
