package com.jgybzx.service.company.impl;

import com.jgybzx.common.entity.PageResult;
import com.jgybzx.dao.company.CompanyDao;
import com.jgybzx.domain.company.Company;
import com.jgybzx.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/2 17:59
 * @Description: ${TODO}
 * @version:
 */
@Service
public class CompanyServiceimpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAll() {
        return companyDao.finAll();
    }

    //根据id查询信息
    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    //新增，提供随机id
    @Override
    public void save(Company company) {
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);
    }

    //修改
    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    //删除
    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    @Override
    public PageResult findPage(int pageNumInt, int pageSizeInt) {

        List<Company> companyList = new ArrayList<>();

        int startIndex = (pageNumInt-1)*pageSizeInt;//计算分页数据开始
        //System.out.println(startIndex);
        companyList = companyDao.findPage(startIndex, pageSizeInt);//查询分页数据
        //System.out.println(companyList);

        int total = companyDao.count();//查询总记录数据
        //pageResult.setTotal(count);//总记录数
        //pageResult.setList(companyList);//分页数据
        //pageResult.setPageNum(pageNumInt);//当前页
        //pageResult.setPageSize(pageSizeInt);//每页几条 数据
        PageResult pageResult = new PageResult(total,companyList,pageNumInt,pageSizeInt);
        return pageResult;

    }
}
