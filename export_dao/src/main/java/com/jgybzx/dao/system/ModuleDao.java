package com.jgybzx.dao.system;

import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface ModuleDao {

    /**
     * 根据id查询
     * @param moduleId
     * @return
     */
    Module findById(String moduleId);

    /**
     * 根据id删除
     * @param moduleId
     * @return
     */
    int delete(String moduleId);

    /**
     * 添加用户
     * @param module
     * @return
     */
    int save(Module module);

    /**
     * 更新用户
     * @param module
     * @return
     */
    int update(Module module);

    /**
     * 查询全部
     * @return
     */
    List<Module> findAll();

    /**
     * 根据 ctype 查找  父级模块
     * ctype="0"> 主菜单======无父级模块
     * ctype="1"> 二级菜单====主菜单
     * ctype="2"> 按钮========二级菜单
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
     * 根据角色id 删除，角色模块中间表数据
     * @param roleId
     */
    void deleteRoleModule(String roleId);

    /**
     * 循环保存 中间表数据
     * @param roleId
     * @param moduleId
     */
    void saveRoleModule(@Param("roleId") String roleId,@Param("moduleId") String moduleId);

    /**
     * 0：SaaS管理员 查询所有 belong = 0的模块
     * 1：企业管理员 查询所有 belong = 1的模块
     * @param belong
     * @return
     */
    List<Module> findModuleByBelong(int belong);

    /**
     * 其他：企业普通员工 根据RBAC权限模型，从数据库查询
     * @param UserId
     * @return
     */
    List<Module> findModuleByUserId(String UserId);
}