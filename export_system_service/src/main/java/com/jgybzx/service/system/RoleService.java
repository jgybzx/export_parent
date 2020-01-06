package com.jgybzx.service.system;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.Role;

import java.util.List;


/**
 * @author: guojy
 * @date: 2020/1/5 16:06
 * @Description: ${TODO}
 * @version:
 */
public interface RoleService {
    /**
     * 分页查询角色数据
     *
     * @param page      页码
     * @param size      每页显示多少条
     * @param companyId 企业id
     * @return
     */
    PageInfo findByPage(Integer page, Integer size, String companyId);

    /**
     * 根据企业id查询所有的角色数据
     * @param companyId
     * @return
     */
    List<Role> findAll(String companyId);

    /**
     *新增角色
     * @param role
     */
    void save(Role role);

    /**
     * 修改角色数据
     * @param role
     */
    void update(Role role);

    /**
     * 根据id获取角色数据
     * @param id
     * @return
     */
    Role findById(String id);

    /**
     * 根据id删除角色数据
     * @param id
     */
    void delete(String id);

    /**
     * 根据用户id查询，该用户的所有角色
     * @param id
     * @return
     */
    List<Role> findByUid(String id);
}
