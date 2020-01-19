package com.jgybzx.web.controller.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: guojy
 * @date: 2020/1/14 12:14
 * @Description: 表格下载 demo
 * @version:
 */
public class ExcleDownload {
    public static void main(String[] args) throws Exception {
        // 创建表格对象
        Workbook wb = new XSSFWorkbook();
        // 创建sheet对象，并命名
        Sheet sheet = wb.createSheet("测试下载");
        // 创建行  参数是索引
        Row row = sheet.createRow(2);
        // 在行的基础上创建 单元格，参数是索引
        Cell cell = row.createCell(2);

        // 设置行高
            row.setHeight((short)(43*20));
        // 设置列宽
        sheet.setColumnWidth(2,24*256);
        //针对单元格设置  单元格 格式================================================================
        CellStyle cellStyle = wb.createCellStyle();
        // 居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置字体
        Font font = wb.createFont();
        font.setBold(true);// 加粗
        font.setFontName("楷体");// 字体
        font.setFontHeightInPoints((short)30);// 字体大小
        cellStyle.setFont(font);// 最后设置给cell

        // 设置表格边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        // =============================================================================
        cell.setCellStyle(cellStyle);
        // 写入数据
        cell.setCellValue("恻恻恻恻恻");
        FileOutputStream out = new FileOutputStream("d:/test.xlsx");
        wb.write(out);
        out.close();
    }
}
