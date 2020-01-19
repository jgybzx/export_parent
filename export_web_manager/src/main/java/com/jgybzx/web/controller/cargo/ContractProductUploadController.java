package com.jgybzx.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jgybzx.domain.cargo.ContractProduct;
import com.jgybzx.service.cargo.ContractProductService;
import com.jgybzx.web.controller.base.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/14 20:41
 * @Description: 获取批量上传
 * @version:
 */
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductUploadController extends BaseController {
    @Reference
    private ContractProductService contractProductService;



    /**
     * 跳转上传页面
     *
     * @return
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId) {
        //合同的id 隐藏在用户上传的页面中的
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-import";
    }

    /**
     * 上传货物
     * 1、解析excle
     * 2、保存数据
     *
     * @param contractId
     * @param file
     * @return
     */
    @RequestMapping("/import")
    public String importExcel(String contractId, MultipartFile file) throws IOException {
        List<ContractProduct> list = new ArrayList<ContractProduct>();
        // ================================= 解析excle===========================================
        // 1、根据文件流创建工作薄对象
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        // 2、获取sheet对象
        Sheet sheet = wb.getSheetAt(0);
        // 3、遍历获取每一行，由于第一行为标题行，不用获取.索引从1开始
        int lastRowNum = sheet.getLastRowNum();// 索引
        for (int i = 1; i <= lastRowNum; i++) {
            // 获取 每一行
            Row row = sheet.getRow(i);
            // 遍历每一行的单元格
            short lastCellNum = row.getLastCellNum();// 获取到的不是索引 = 10
            /**
             * 第一列为空 跳过获取，获取每一个单元格的数据，并封装
             * 第一种方式：1、在循环中，使用switch，给每一个属性赋值，从第一个赋值到最后一个，比较麻烦，但是清晰
             * 第二种方式：不直接赋值，而是取出每个单元个的数据，放到一个数组里，然后从数据里给对象赋值
             * -对象提供一个构造方法，可以根据数组赋值 public ContractProduct(Object []objs, String companyId, String companyName)
             * - 此时就可以构建对象
             */
            Object[] objects = new Object[lastCellNum];
            for (int j = 1; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                // 4、获取每一个单元格数据，放到 object数组中
                objects[j] = getCellValue(cell);
            }
            // 利用构造方法构建货物对象
            ContractProduct contractProduct = new ContractProduct(objects, super.companyId, super.companyName,contractId);
            // 一行循环完 就是一个对象，直接添加到集合中
            list.add(contractProduct);
        }
        // ======================================= 解析excle===================================================================
        // 保存对象
        //5.保存数据 service必须增加一个方法(service层控制事务)
        contractProductService.saveList(list);
        return "cargo/product/product-import";
    }

    /**
     * 获取cell中的数据，根据不同的类型使用不同的api
     *
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell) {
        Object o = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                // 数字类型  接受数字和日期
                boolean flag = DateUtil.isCellDateFormatted(cell);
                if (flag) {
                    // 日期
                    o = cell.getDateCellValue();
                } else {
                    o = cell.getNumericCellValue();
                }
                break;
            case STRING:
                // 字符串
                o = cell.getStringCellValue();
                break;
            case BOOLEAN:
                o = cell.getBooleanCellValue();
                break;
            default:
                break;
        }
        return o;
    }
}
