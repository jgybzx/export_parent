package com.jgybzx.service.stat;

import java.util.List;
import java.util.Map;

/**
 * @author: guojy
 * @date: 2020/2/4 17:16
 * @Description: ${TODO}
 * @version:
 */
public interface StatService {
    List<Map> findFactory(String companyId);

    List<Map> findSell(String companyId);

    List<Map> getOnlineData(String companyId);
}
