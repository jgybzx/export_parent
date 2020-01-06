package com.jgybzx.service.system;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.Dept;

import java.util.List;


/**
 * @author: guojy
 * @date: 2020/1/5 16:06
 * @Description: ${TODO}
 * @version:
 */
public interface DeptService {
    /**
     * 分页查询部门数据
     *
     * @param page      页码
     * @param size      每页显示多少条
     * @param companyId 企业id
     * @return
     */
    PageInfo findByPage(Integer page, Integer size, String companyId);

    /**
     * 根据企业id查询所有的部门数据
     * @param companyId
     * @return
     */
    List<Dept> findAll(String companyId);

    /**
     *新增部门
     * @param dept
     */
    void save(Dept dept);

    /**
     * 修改部门数据
     * @param dept
     */
    void update(Dept dept);

    /**
     * 根据id获取部门数据
     * @param id
     * @return
     */
    Dept findById(String id);

    /**
     * 根据id删除部门数据
     * @param id
     */
    void delete(String id);
}
