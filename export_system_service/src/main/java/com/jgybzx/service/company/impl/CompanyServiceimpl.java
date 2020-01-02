package com.jgybzx.service.company.impl;

import com.jgybzx.dao.company.CompanyDao;
import com.jgybzx.domain.company.Company;
import com.jgybzx.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
