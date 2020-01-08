package com.jgybzx.dao.system;

import com.jgybzx.domain.system.SysLog;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/7 17:38
 * @Description: ${TODO}
 * @version:
 */
public interface SysLogDao {
    void save(SysLog sysLog);

    List<SysLog> findAll(String companyId);

}
