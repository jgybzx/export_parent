package com.jgybzx.web.controller.export;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jgybzx.common.utils.BeanMapUtils;
import com.jgybzx.common.utils.DownloadUtil;
import com.jgybzx.domain.export.Export;
import com.jgybzx.domain.export.ExportProduct;
import com.jgybzx.domain.export.ExportProductExample;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.cargo.ExportProductService;
import com.jgybzx.service.cargo.ExportService;
import com.jgybzx.web.controller.base.BaseController;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: guojy
 * @date: 2020/2/7 9:58
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/cargo/export")
public class ExportPDFDownload extends BaseController {
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;
    @RequestMapping("/exportPdf")
    public ResponseEntity<byte[]> exportPdf(String id) throws Exception {
        // 读取模板
        InputStream is = session.getServletContext().getResourceAsStream("/jasper/export.jasper");
        // 获取报运单数据
        Export export = exportService.findById(id);
        // 将对象转换为 map
        Map<String, Object> exportMap = BeanMapUtils.beanToMap(export);
        // 获取货物数据
        List<ExportProduct> list = new ArrayList<>();
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(example);

        // 转换list
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(exportProductList);

        JasperPrint jasperPrint =  JasperFillManager.fillReport(is, exportMap, dataSource);
        byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        // 设置下载的响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode("报运单.pdf","utf-8"));
        // 构建下载的对象并直接返回
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        return responseEntity;
    }



    /**
     * 页面点击此链接，将生成的pdf预览下载
     * 不需要返回值
     */
    @RequestMapping("/exportPdf1")
    public void exportPdf1() throws Exception {
       /* //1.获取模板的路径
        String path = session.getServletContext().getRealPath("/") + "/jasper/test01.jasper";
        //2.加载模板，创建JasperPrint对象
        //在加载博班创建对象的时候，可以进行数据填充
        *//**
         * 1.模板路径
         * 2.map类型的参数
         * 3.list类型的参数（JrDataSource对象）
         *//*
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, new HashMap<>(), new JREmptyDataSource());
        //3.输出pdf文件（预览，下载）
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());*/
        // 获取模板
       // InputStream is = session.getServletContext().getResourceAsStream("/jasper/test02.jasper");
        InputStream is = session.getServletContext().getResourceAsStream("/jasper/testMap.jasper");
        Map<String,Object> map = new HashMap<>();
        map.put("username","测试");
        map.put("password","测试");
        JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }

    @RequestMapping("/exportPdf2")
    public void exportPdflist() throws Exception {
        InputStream is = session.getServletContext().getResourceAsStream("/jasper/testList.jasper");
        List<User> list = new ArrayList<>();
        // 模拟多条数据
        for (int i = 0; i <10 ; i++) {
            User user  = new User();
            user.setUserName("测试list填充"+i);
            user.setPassword("测试list填充"+i);
            list.add(user);
        }
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(is, new HashMap<>(), dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }
}
