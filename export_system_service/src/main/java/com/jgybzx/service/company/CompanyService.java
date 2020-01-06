package com.jgybzx.service.company;

import com.github.pagehelper.PageInfo;
import com.jgybzx.common.entity.PageResult;
import com.jgybzx.domain.company.Company;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/2 17:59
 * @Description: ${TODO}
 * @version:
 */
public interface CompanyService {
    public List<Company> findAll();

    //根据id查询信息
    Company findById(String id);

    //新增
    void save(Company company);

    //修改
    void update(Company company);

    //删除
    void delete(String id);

    //查询分页数据
    PageResult findPage(int pageNumInt, int pageSizeInt);


    /**
     * 使用  PageHelper  返回分页数据
     * @param page 页码
     * @param size 每页显示多少数据
     * @return
     */
    PageInfo findPageByPageHelper(int page, int size);
}
