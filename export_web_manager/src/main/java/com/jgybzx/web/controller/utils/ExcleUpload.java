package com.jgybzx.web.controller.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author: guojy
 * @date: 2020/1/14 12:19
 * @Description: 表格上传，数据读取demo
 * @version:
 */
public class ExcleUpload {
    public static void main(String[] args) throws IOException {
        Workbook wb = new XSSFWorkbook("d:/demo.xlsx");
        // 获取该表格 中的某个sheet页
        Sheet sheetAt = wb.getSheetAt(0);
        // 双层循环解析 数据
        int lastRowNum = sheetAt.getLastRowNum();//本页最后一行 索引
        for (int i = 0; i <= lastRowNum; i++) {
            // 获取每一行
            Row row = sheetAt.getRow(i);
            // 获取本行最后一列  注意 不是索引
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                // 如果单元格没有数据，会直接读取出 null，所以需要判断
                if (cell != null) {
                    // 获取每一个cell 中的value
                    // 由于表格中有不同的数据 类型，所以获取的时候也需要判断数据类型 使用不同的api
                    // 收取方法
                    Object o = getCellValue(cell);
                    System.out.println(o);
                }

            }
        }
    }

    private static Object getCellValue(Cell cell) {
        //传递一个cell，判断cell的 数据类型 获取对应的值
        // poi 不直接提供工具类

        // 获取cell的类型
        CellType cellType = cell.getCellType();
        Object o = null;
        switch (cellType) {
            // 布尔类型
            case BOOLEAN:
                o = cell.getBooleanCellValue();
                break;
            // 字符串类型
            case STRING:
                o = cell.getStringCellValue();
                break;
            /**
             * 数字类型:
             * 1、在表格中，日期格式是数字类型，所以需要在NUMERIC 中操作
             * 2、并且也需要判断 是日期还是数字，这两个对应的api一样
             */
            case NUMERIC:
                // poi提供工具类，判断是不是日期类型
                boolean flag = DateUtil.isCellDateFormatted(cell);
                if (flag) {
                    Date date = cell.getDateCellValue();
                    o = new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    o = cell.getNumericCellValue();
                }
                break;
            default:
                break;
        }
        return o;
    }
}
