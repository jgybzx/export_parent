package com.jgybzx.web.controller.quartz;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jgybzx.common.utils.MailUtil;
import com.jgybzx.domain.cargo.Contract;
import com.jgybzx.service.cargo.ContractService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

/**
 * @author: guojy
 * @date: 2020/2/7 19:59
 * @Description: ${TODO}
 * @version:
 */

public class Myjob extends BaseController {
    @Reference
    private ContractService contractService;
    public void run() throws Exception {
        String s = new Date().toLocaleString();
        //System.out.println("定时任务每五秒输出："+s);
        // 发送定时邮件
        String to = "1325621332@qq.com";
        String subject = "定时发送";
        int i= 0;
        String content = "七子表达式发送数据";
       // MailUtil.sendMsg(to,subject,content);

        //  每天上午8点，自动的查询数据库中所有今天需要交付的购销合同。向企业管理员（发送一封邮件）

        // 获取购销合同船期为 今天的 合同数据
        Date date = new Date();
       // System.out.println("获取数据");
        List<String> list =  contractService.findBydeliveryPeriod(date,super.companyId);
        System.out.println(list);
    }
}
