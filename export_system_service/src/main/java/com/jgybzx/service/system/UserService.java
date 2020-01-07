package com.jgybzx.service.system;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.User;

import java.util.List;


/**
 * @author: guojy
 * @date: 2020/1/5 16:06
 * @Description: ${TODO}
 * @version:
 */
public interface UserService {
    /**
     * 分页查询用户数据
     *
     * @param page      页码
     * @param size      每页显示多少条
     * @param companyId 企业id
     * @return
     */
    PageInfo findByPage(Integer page, Integer size, String companyId);

    /**
     * 根据企业id查询所有的用户数据
     * @param companyId
     * @return
     */
    List<User> findAll(String companyId);

    /**
     *新增用户
     * @param user
     */
    void save(User user);

    /**
     * 修改用户数据
     * @param user
     */
    void update(User user);

    /**
     * 根据id获取用户数据
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * 根据id删除用户数据
     * @param id
     */
    void delete(String id);

    /**
     * 修改 用户角色
     * @param roleIds
     * @param userId
     */
    void changeRole(String roleIds,String userId);

    /**
     * 根据email  查询用户
     * @param email
     * @return
     */
    User login(String email);
}
