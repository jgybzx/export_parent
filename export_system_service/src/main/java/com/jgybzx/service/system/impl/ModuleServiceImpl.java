package com.jgybzx.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.system.ModuleDao;
import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.Role;
import com.jgybzx.domain.system.User;
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

    // =====================================================================================
    /**
     * 用户类型
     *   - 通过用户数据库表中的==degree==字段区分 ：
     *   - ==0：SaaS管理员==
     *   - ==1：企业管理员==
     *   - ==其他：企业普通员工==
     * 模块从属
     *   - 通过模块对象中的==belong==字段区分
     *   - ==0：Saas管理的内部菜单==
     *   - ==1：企业使用的业务菜单==
     * 不同的用户类型访问不同的模块
     *   - SaaS管理员 ： 访问所有Saas管理的内部菜单
     *     == 查询所有 belong = 0的模块
     *   - 企业管理员：访问企业使用的业务菜单
     *     == 查询所有 belong = 1的模块
     *   - 企业员工：根据RBAC权限模型，从数据库查询
     *     == 多个表联合查询
     */
    //================================================================================

    /**
     * 根据用户id，查询该用户的角色，并查出对应的模块module(或者权限)
     *
     * @param loginuser
     * @return
     */
    @Override
    public List<Module> findModuleByUserId(User loginuser) {

        // 判断用户类型 degree字段 0：SaaS管理员;1：企业管理员;
        Integer degree = loginuser.getDegree();
        if (degree==0){
            //0：SaaS管理员 查询所有 belong = 0的模块
            return moduleDao.findModuleByBelong(0);
        }else if(degree ==1){
            // 1：企业管理员 查询所有 belong = 1的模块
            return moduleDao.findModuleByBelong(1);
        }else {
            // 其他：企业普通员工 根据RBAC权限模型，从数据库查询
            return moduleDao.findModuleByUserId(loginuser.getId());
        }
    }
}
