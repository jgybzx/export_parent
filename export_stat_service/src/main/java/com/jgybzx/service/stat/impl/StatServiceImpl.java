package com.jgybzx.service.stat.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jgybzx.dao.stat.StatDao;
import com.jgybzx.service.stat.StatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author: guojy
 * @date: 2020/2/4 17:17
 * @Description: ${TODO}
 * @version:
 */
@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao statDao;

    /**
     * 查询厂家销售情况
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Map> findFactory(String companyId) {

        return statDao.findFactory(companyId);

    }

    @Override
    public List<Map> findSell(String companyId) {
        return statDao.findSell(companyId);

    }

    /**
     * 访问压力图
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Map> getOnlineData(String companyId) {

        return statDao.getOnlineData(companyId);
    }
}
