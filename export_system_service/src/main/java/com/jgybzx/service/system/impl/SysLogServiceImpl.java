package com.jgybzx.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jgybzx.dao.system.SysLogDao;
import com.jgybzx.domain.system.SysLog;
import com.jgybzx.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author: guojy
 * @date: 2020/1/7 17:37
 * @Description: 日志类
 * @version:
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) {
        sysLog.setId(UUID.randomUUID().toString());
        sysLogDao.save(sysLog);
    }

    @Override
    public PageInfo<SysLog> findAll(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page, size);
        List<SysLog> logList = sysLogDao.findAll(companyId);

        return new PageInfo(logList);
    }
}
