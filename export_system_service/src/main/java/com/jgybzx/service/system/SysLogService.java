package com.jgybzx.service.system;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.SysLog;

/**
 * @author: guojy
 * @date: 2020/1/7 17:37
 * @Description: ${TODO}
 * @version:
 */
public interface SysLogService {
    /**
     * 保存日志
     * @param sysLog
     */
    void save(SysLog sysLog);

    /**
     * 获取分页数据
     * @return
     * @param page
     * @param size
     * @param companyId
     */
    PageInfo<SysLog> findAll(Integer page, Integer size, String companyId);

}
