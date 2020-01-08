package com.jgybzx.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.system.DeptDao;
import com.jgybzx.domain.system.Dept;
import com.jgybzx.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/5 16:07
 * @Description: ${TODO}
 * @version:
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    /**
     * 分页查询部门数据
     *
     * @param page      页码
     * @param size      每页显示多少条
     * @param companyId 企业id
     * @return
     */
    @Override
    public PageInfo findByPage(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page, size);
        List<Dept> deptList = deptDao.findAll(companyId);
        return new PageInfo(deptList);
    }

    /**
     * 根据 企业id查询部门数据
     * @param companyId
     * @return
     */
    @Override
    public List<Dept> findAll(String companyId) {
        List<Dept> deptList = deptDao.findAll(companyId);
        return deptList;
    }

    /**
     * 保存
     * @param dept
     */

    @Override
    public void save(Dept dept) {
        //设置部门的id,随机生成
        dept.setId(UUID.randomUUID().toString());
        deptDao.save(dept);
    }

    /**
     * 更新
     * @param dept
     */
    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    /**
     * 根据部门id，获取部门数据
     * @param id
     * @return
     */
    @Override
    public Dept findById(String id) {

        return deptDao.findById(id);
    }

    /**
     * 根据 id删除部门数据
     * @param id
     */
    @Override
    public void delete(String id) {
        deptDao.delete(id);
    }
}
