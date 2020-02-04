package com.jgybzx.dao.stat;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: guojy
 * @date: 2020/2/4 19:00
 * @Description: ${TODO}
 * @version:
 */
public interface StatDao {
    List<Map> findFactory(String companyId);

    List<Map> findSell(String companyId);

    List<Map> getOnlineData( String companyId);
}
