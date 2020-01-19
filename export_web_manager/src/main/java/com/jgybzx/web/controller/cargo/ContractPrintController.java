package com.jgybzx.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jgybzx.common.utils.DownloadUtil;
import com.jgybzx.domain.vo.ContractProductVo;
import com.jgybzx.service.cargo.ContractService;
import com.jgybzx.web.controller.base.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/15 18:44
 * @Description: 出货表打印（下载）,按照一定的模版拼接数据
 * 客户	   合同号	 货号	数量	  工厂	 工厂交期	船期	    贸易条款
 * 合同表   合同表    货物表  货物表 货物表  合同表     合同表   合同表
 * @version:
 */
@Controller
@RequestMapping("/cargo/contract")
public class ContractPrintController extends BaseController {
    @Reference
    private ContractService contractService;

    /**
     * 进入出货表打印界面
     */
    @RequestMapping("/print")
    public String print() {
        return "cargo/print/contract-print";
    }


    // ===============================读取设置好的excle格式打印==========================================
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws Exception {
        //1.根据日期查询数据
        List<ContractProductVo> list = contractService.findByShipTime(inputDate, super.companyId);

        System.out.println("list = " + list);

        //--------------------------------------------------excel数据填充 start
        //读取本地的excel文件 填充数据
        //读取项目资源两种方式:
        // 1.读取resource下的资源 :  使用的是类加载器 当前类.class.getClassLoader().getResourceAsSteam()
        // 2.读取webapp下的资源 : 项目的根对象.getResourceAsStream()
        InputStream is = session.getServletContext().getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
        //System.out.println(is);

        //2.根据已经查询到的数据构建excel
        //2.1 获得工作薄对象
        Workbook wb = new XSSFWorkbook(is);
        //2.2 获得表
        Sheet sheet = wb.getSheetAt(0);
        //2.3 获得大标题
        Row row = sheet.getRow(0);

        Cell cell = row.getCell(1);//第一列
        //inputDate 2015-01
        inputDate = inputDate.replace("-", "年");
        cell.setCellValue(inputDate + "月份出货表");

        //2.4 先将原来excel中的样式读取出来 重新赋值给每一行即可
        row = sheet.getRow(2);

        //获得每一个cell对象
        short lastCellNum = row.getLastCellNum(); //最大的列数
        //定义数组存放样式
        CellStyle[] styles = new CellStyle[lastCellNum];
        for (int i = 1; i < lastCellNum; i++) {
            //获得每一个cell对象
            cell = row.getCell(i);
            //将每一个单元格样式赋值给数组中的具体位置
            styles[i] = cell.getCellStyle();
        }

        //2.5 构建数据 (重点)
        //每一个对象 相当于excel表格中的一行数据
        int index = 2; //定义行号
        for (ContractProductVo productVo : list) {
            row = sheet.createRow(index);//创建行  从第三行开始都是创建

            // 合同表: 客户 customName
            cell = row.createCell(1);
            cell.setCellStyle(styles[1]);
            cell.setCellValue(productVo.getCustomName());
            // 合同表: 合同号 contractNo
            cell = row.createCell(2);
            cell.setCellStyle(styles[2]);
            cell.setCellValue(productVo.getContractNo());
            // 货物表: 货号 productNo
            cell = row.createCell(3);
            cell.setCellStyle(styles[3]);
            cell.setCellValue(productVo.getProductNo());
            // 货物表: 数量 cnumber
            cell = row.createCell(4);
            cell.setCellStyle(styles[4]);
            cell.setCellValue(productVo.getCnumber());
            // 货物表: 工厂 factoryName
            cell = row.createCell(5);
            cell.setCellStyle(styles[5]);
            cell.setCellValue(productVo.getFactoryName());
            // 合同表: 工厂交期  deliveryPeriod
            cell = row.createCell(6);
            cell.setCellStyle(styles[6]);
            cell.setCellValue(productVo.getDeliveryPeriod());
            // 合同表: 船期 shipTime
            cell = row.createCell(7);
            cell.setCellStyle(styles[7]);
            cell.setCellValue(productVo.getShipTime());
            //合同表: 贸易条款 tradeTerms
            cell = row.createCell(8);
            cell.setCellStyle(styles[8]);
            cell.setCellValue(productVo.getTradeTerms());
            index++;//行号递增
        }


        //--------------------------------------------------excel数据填充 end

        //3.下载 http协议传输
        DownloadUtil downloadUtil = new DownloadUtil();
        //文件的输出流 -含有所有的数据
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);//将文件写入输出流中
        //参数1: 下载的输出
        //参数2: response对象
        //参数3: 下载的文件名称
        downloadUtil.download(os, response, "出货表.xlsx");
    }

    // ====================================手动设置excle格式打印==========================================

    /**
     * 下载某一月份的数据，合同中的船期表示的是货物发货的时间，所以就是真正的卖出货的时间，
     * 所有要想看某个月的出货 信息使用 船期作为条件
     * 客户	   合同号	 货号	数量	  工厂	 工厂交期	船期	    贸易条款
     * 合同表   合同表    货物表  货物表 货物表  合同表     合同表   合同表
     * 封装这几个字段，可以来一个 实体类 ContractProductVo
     * VO：用于表现层传递对象
     *
     * @param inputDate
     * @return
     */
    @RequestMapping("printExcel23")
    public void printExcel1(String inputDate) throws IOException {
        // 不要忘记企业id
        List<ContractProductVo> list = contractService.findByShipTime(inputDate, super.companyId);
        System.out.println(list.size());
        // 根据获得的数据 构造 excle
        // -------------------------------------excle 数据填充---------------------------------------
        // 1、获取 表格对象
        Workbook wb = new XSSFWorkbook();
        // 2、获取sheet
        Sheet sheet = wb.createSheet();

        // 3、获取第一行：大标题行，XXXX年-XX月份出货表
        Row row = sheet.createRow(0);
        /**
         * 合并单元格操作  Merge 合并   Region 区域
         * public CellRangeAddress(int firstRow, int lastRow, int firstCol 从索引开始 , int lastCol 索引结束)
         * 从第0 行到第0行，从第2列到第9 列  都是索引
         */
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 8));
        // 3·1 设置大标题格式,在代码里边写太费劲，抽取出来
        Cell cell = row.createCell(1);
        CellStyle cellStyle = this.bigTitle(wb);
        cell.setCellStyle(cellStyle);
        String dateStr = inputDate.replace("-", "年");
        cell.setCellValue(dateStr + "月份出货表");
        //4、第二行，小标题行,第一列为空，不填  客户	合同号	货号	数量	工厂	工厂交期	船期	贸易条款
        String[] headerNames = {"", "客户", "合同号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
        row = sheet.createRow(1);
        for (int i = 1; i < headerNames.length; i++) {
            cell = row.createCell(i);// 创建单元格
            cellStyle = this.title(wb);// 设置单元格样式
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headerNames[i]);// 赋值数据
        }

        // 5、从list中赋值数据,一行一个对象，对应赋值
        int index = 2;// 从 第三行开始
        for (ContractProductVo contractProductVo : list) {
            row = sheet.createRow(index);
            // 合同表: 客户 customName
            cell = row.createCell(1);
            cell.setCellValue(contractProductVo.getCustomName());
            // 合同表: 合同号 contractNo
            cell = row.createCell(2);
            cell.setCellValue(contractProductVo.getContractNo());
            // 货物表: 货号 productNo
            cell = row.createCell(3);
            cell.setCellValue(contractProductVo.getProductNo());
            // 货物表: 数量 cnumber
            cell = row.createCell(4);
            cell.setCellValue(contractProductVo.getCnumber());
            // 货物表: 工厂 factoryName
            cell = row.createCell(5);
            cell.setCellValue(contractProductVo.getFactoryName());
            // 合同表: 工厂交期  deliveryPeriod
            cell = row.createCell(6);
            cell.setCellValue(contractProductVo.getDeliveryPeriod());
            // 合同表: 船期 shipTime
            cell = row.createCell(7);
            cell.setCellValue(contractProductVo.getShipTime());
            //合同表: 贸易条款 tradeTerms
            cell = row.createCell(8);
            cell.setCellValue(contractProductVo.getTradeTerms());
            index++;//行号递增
        }
        // -------------------------------------excle 数据填充---------------------------------------


        //3.下载 http协议传输
        DownloadUtil downloadUtil = new DownloadUtil();
        //文件的输出流 -含有所有的数据
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);//将文件写入输出流中
        //参数1: 下载的输出流
        //参数2: response对象
        //参数3: 下载的文件名称
        downloadUtil.download(os, response, "出货表.xlsx");
    }

    //大标题的样式
    public CellStyle bigTitle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 20);
        font.setBold(true);//字体加粗
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        cellStyle.setBorderTop(BorderStyle.THIN);                        //上细线
        cellStyle.setBorderBottom(BorderStyle.THIN);                    //下细线
        cellStyle.setBorderLeft(BorderStyle.THIN);                        //左细线
        cellStyle.setBorderRight(BorderStyle.THIN);                        //右细线
        return cellStyle;
    }

    //小标题的样式
    public CellStyle title(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        cellStyle.setBorderTop(BorderStyle.THIN);                        //上细线
        cellStyle.setBorderBottom(BorderStyle.THIN);                    //下细线
        cellStyle.setBorderLeft(BorderStyle.THIN);                        //左细线
        cellStyle.setBorderRight(BorderStyle.THIN);                        //右细线
        return cellStyle;
    }

    //文字样式
    public CellStyle text(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 12);

        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.LEFT);                //横向居左
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        cellStyle.setBorderTop(BorderStyle.THIN);                        //上细线
        cellStyle.setBorderBottom(BorderStyle.THIN);                    //下细线
        cellStyle.setBorderLeft(BorderStyle.THIN);                        //左细线
        cellStyle.setBorderRight(BorderStyle.THIN);                        //右细线

        return cellStyle;
    }
}
