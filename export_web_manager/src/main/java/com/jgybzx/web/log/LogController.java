package com.jgybzx.web.log;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.SysLog;
import com.jgybzx.service.system.SysLogService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/7 17:58
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/system/log/")
public class LogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 获取数据
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list",name = "获取日志数据")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size){

        PageInfo<SysLog> logs = sysLogService.findAll(page,size,companyId);
        request.setAttribute("page",logs);
        return "system/log/log-list";
    }
}
