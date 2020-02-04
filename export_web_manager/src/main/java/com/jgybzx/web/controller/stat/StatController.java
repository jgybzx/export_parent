package com.jgybzx.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jgybzx.service.stat.StatService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: guojy
 * @date: 2020/2/4 17:55
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("stat")
public class StatController extends BaseController {
   @Reference
    private StatService statService;
    @RequestMapping("toCharts")
    public String toCharts(String chartsType){
        return "stat/stat-"+chartsType;
    }

    /**
     * ajax 获取工厂销售情况
     *  [
     *   {value: 335, name: '直接访问'},
     *   {value: 310, name: '邮件营销'},
     *  ]
     *  页面需要的数据  类型是  [{key,value}
     *  是一个list<Map>
     * @return
     */
    @RequestMapping("findFactory")
    @ResponseBody
    public List<Map> findFactory(){
        List<Map> factory = statService.findFactory(super.companyId);
        return factory;
    }
    @RequestMapping("findSell")
    @ResponseBody
    public List<Map> findSell(){
        List<Map> sell = statService.findSell(super.companyId);
        System.out.println(sell);
        return sell;
    }
    @RequestMapping("getOnlineData")
    @ResponseBody
    public List<Map> getOnlineData(){
        List<Map> onlineData = statService.getOnlineData(super.companyId);
        return onlineData;
    }
}
