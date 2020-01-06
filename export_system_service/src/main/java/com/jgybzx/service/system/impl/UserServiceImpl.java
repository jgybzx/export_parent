package com.jgybzx.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.system.UserDao;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/5 16:07
 * @Description: ${TODO}
 * @version:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 分页查询用户数据
     *
     * @param page      页码
     * @param size      每页显示多少条
     * @param companyId 企业id
     * @return
     */
    @Override
    public PageInfo findByPage(Integer page, Integer size, String companyId) {
        System.out.println("PageInfo");
        PageHelper.startPage(page, size);
        List<User> userList = userDao.findAll(companyId);
        return new PageInfo(userList);
    }

    /**
     * 根据 企业id查询用户数据
     *
     * @param companyId
     * @return
     */
    @Override
    public List<User> findAll(String companyId) {
        List<User> userList = userDao.findAll(companyId);
        return userList;
    }

    /**
     * 保存
     *
     * @param user
     */

    @Override
    public void save(User user) {
        //设置用户的id,随机生成
        user.setId(UUID.randomUUID().toString());
        userDao.save(user);
    }

    /**
     * 更新
     *
     * @param user
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * 根据用户id，获取用户数据
     *
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {

        return userDao.findById(id);
    }

    /**
     * 根据 id删除用户数据
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    /**
     * 修改用户角色
     * 1、删除该用户所有角色信息，
     * 2、保存前台页面传递的数据
     *
     * @param roleIds
     * @param userId
     */
    @Override
    public void changeRole(String roleIds, String userId) {
        // 根据 id 删除 pe_role_user 表中的信息
        userDao.deleteRoleById(userId);

        // 拆分  roleIds
        String[] roleIdArray = roleIds.split(",");
        //非空判断
        if (!StringUtils.isEmpty(roleIdArray) & roleIdArray.length > 0) {
            // 向 pe_role_user 表中循环插入数据
            for (String roleId : roleIdArray) {
                userDao.saveUserRole(userId, roleId);
            }

        }
    }


}
