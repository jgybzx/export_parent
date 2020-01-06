package com.jgybzx.dao.system;

import com.jgybzx.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleDao {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Role findById(String id);

    /**
     * 查询全部用户
     *
     * @param companyId
     * @return
     */
    List<Role> findAll(String companyId);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 添加
     *
     * @param role
     * @return
     */
    int save(Role role);

    /**
     * 更新
     *
     * @param role
     * @return
     */
    int update(Role role);

    /**
     * 根据用户id查询该用户有多少角色
     * SELECT * FROM pe_role WHERE role_id IN (
     *          SELECT role_id FROM pe_role_user WHERE user_id = 'id')
     *
     * @param id
     * @return
     */
    List<Role> findByUid(String id);
}