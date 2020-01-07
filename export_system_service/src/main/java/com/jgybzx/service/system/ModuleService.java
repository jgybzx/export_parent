package com.jgybzx.service.system;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.Role;

import java.util.List;


/**
 * @author: guojy
 * @date: 2020/1/5 16:06
 * @Description: ${TODO}
 * @version:
 */
public interface ModuleService {
    /**
     * 分页查询模块数据
     *
     * @param page      页码
     * @param size      每页显示多少条
     * @return
     */
    PageInfo findByPage(Integer page, Integer size);

    /**
     * 查询所有的模块数据
     * @return
     */
    List<Module> findAll();

    /**
     *新增模块
     * @param module
     */
    void save(Module module);

    /**
     * 修改模块数据
     * @param module
     */
    void update(Module module);

    /**
     * 根据id获取模块数据
     * @param id
     * @return
     */
    Module findById(String id);

    /**
     * 根据id删除模块数据
     * @param id
     */
    void delete(String id);

    /**
     * 查找 父级模块
     * @param ctype
     * @return
     */
    List<Module> findParent(String ctype);

    /**
     * 根据角色 id，查询对应的 模块数据
     * @param roleId
     * @return
     */
    List<Module> findRoleModuleById(String roleId);

    /**
     * 更新角色权限
     * @param roleId
     * @param moduleIds
     */
    void updateRoleModule(String roleId, String moduleIds);
}
