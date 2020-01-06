package com.jgybzx.service.company.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.common.entity.PageResult;
import com.jgybzx.dao.company.CompanyDao;
import com.jgybzx.domain.company.Company;
import com.jgybzx.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/2 17:59
 * @Description: ${TODO}
 * @version:
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAll() {
        return companyDao.finAll();
    }


    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */

    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }


    /**
     * 新增，提供随机id
     *
     * @param company
     */
    @Override
    public void save(Company company) {
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);
    }

    /**
     * 修改数据
     *
     * @param company
     */
    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    @Override
    public PageResult findPage(int pageNumInt, int pageSizeInt) {

        List<Company> companyList;
        //计算分页数据开始
        int startIndex = (pageNumInt - 1) * pageSizeInt;
        //查询分页数据
        companyList = companyDao.findPage(startIndex, pageSizeInt);
        //查询总记录数据
        int total = companyDao.count();
        return new PageResult(total, companyList, pageNumInt, pageSizeInt);

    }

    @Override
    public PageInfo findPageByPageHelper(int page, int size) {
        //使用PageHelper进行分页查询
        PageHelper.startPage(page, size);
        //调用自己的findAll方法，紧跟着的第一个select方法会被分页
        List<Company> companyList = companyDao.finAll();
        return new PageInfo(companyList);
    }
}
