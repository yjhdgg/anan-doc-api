package com.anan.document.modules.doc.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.anan.document.modules.doc.dto.MeasurementManagementDTO;
import com.anan.document.modules.doc.entity.TDocumentInfo;
import com.anan.document.modules.doc.service.TDocumentInfoService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/6 8:27
 * @Version 1.0
 */
@RestController
@RequestMapping("/export")
public class ExportExcelController {

    @Autowired
    private TDocumentInfoService baseService;

    @GetMapping("/mm")
    public void exportExcel(HttpServletResponse response) throws IOException {
        String fileName = "计量管理数据表.xls";
        response.setContentType("application/vnd.ms-excel");//让客户端浏览器区分不同类型的数据
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));

        List<TDocumentInfo> list = baseService.list(new LambdaQueryWrapper<TDocumentInfo>().eq(TDocumentInfo::getDocType, "1603576853188853762"));
        List<MeasurementManagementDTO> mmDTO = new LinkedList<MeasurementManagementDTO>();
        for (TDocumentInfo tDocumentInfo : list) {
            MeasurementManagementDTO copyProperties = BeanUtil.copyProperties(tDocumentInfo, MeasurementManagementDTO.class);
            mmDTO.add(copyProperties);
        }
        // excel表头设置名字
        String[] excelHeader = {"设备编号", "设备名", "规格类型", "使用部门", "过期时间", "出厂编号",
                "生产厂家", "测量范围", "校验日期", "投运日期", "精准度", "备注"};
        // 创建excel
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // 创建表
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("计量管理数据表");
        // 创建第一行
        HSSFRow hssfRow = hssfSheet.createRow(0);
        // 创建样式
        HSSFCellStyle hssfCellStyle = hssfWorkbook.createCellStyle();
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中

        // 遍历设置表头
        for (int i = 0; i < excelHeader.length; i++) {
            // 创建单元格第一行第i个单元格
            HSSFCell hssfCell = hssfRow.createCell(i);
            // 为其写入excelHeader数组的第i个表头名称
            hssfCell.setCellValue(excelHeader[i]);
            // 遍历样式
            hssfCell.setCellStyle(hssfCellStyle);
            // 设置自动调整宽度
            hssfSheet.autoSizeColumn(i);
            hssfSheet.setColumnWidth(hssfCell.getColumnIndex(), 100 * 50);

        }

        // 遍历追加数据库数据
        for (int i = 0; i < list.size(); i++) {
            // 从第i + i行开始遍历追加，因为前面第一行用做表头了
            hssfRow = hssfSheet.createRow(i + 1);
            // 将获取到的数据库数据遍历进实体类，在进行逐行的添加
            MeasurementManagementDTO tDocumentInfo = mmDTO.get(i);
            // 开始添加
            if (tDocumentInfo.getEquipNum() != null) {
                hssfRow.createCell(0).setCellValue(tDocumentInfo.getEquipNum());
            }
            if (tDocumentInfo.getEquipName() != null) {
                hssfRow.createCell(1).setCellValue(tDocumentInfo.getEquipName());
            }
            if (tDocumentInfo.getEquipType() != null) {
                hssfRow.createCell(2).setCellValue(tDocumentInfo.getEquipType());
            }
            if (tDocumentInfo.getUseDepart() != null) {
                hssfRow.createCell(3).setCellValue(tDocumentInfo.getUseDepart());
            }
            if (tDocumentInfo.getEffectiveDate() != null) {
                hssfRow.createCell(4).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tDocumentInfo.getEffectiveDate()));
            }
            if (tDocumentInfo.getExFactoryNum() != null) {
                hssfRow.createCell(5).setCellValue(tDocumentInfo.getExFactoryNum());
            }

            if (tDocumentInfo.getManufacturer() != null) {
                hssfRow.createCell(6).setCellValue(tDocumentInfo.getManufacturer());
            }
            if (tDocumentInfo.getMeasureRange() != null) {
                hssfRow.createCell(7).setCellValue(tDocumentInfo.getMeasureRange());
            }
            if (tDocumentInfo.getCheckDate() != null) {
                hssfRow.createCell(8).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tDocumentInfo.getCheckDate()));
            }
            if (tDocumentInfo.getRunningDate() != null) {
                hssfRow.createCell(9).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tDocumentInfo.getRunningDate()));
            }
            if (tDocumentInfo.getAccuracy() != null) {
                hssfRow.createCell(10).setCellValue(tDocumentInfo.getAccuracy());
            }
            if (tDocumentInfo.getRemarks() != null) {
                hssfRow.createCell(11).setCellValue(tDocumentInfo.getRemarks());
            }

        }
        // 最后返回整个表
        hssfWorkbook.write(response.getOutputStream());
//        return hssfWorkbook;
    }

    public <T> List<T> copyList2(List<?> list, Class<T> clazz) {
        List<T> result = new ArrayList<>(list.size());
        for (Object source : list) {
            T target;
            try {
                target = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException();
            }
            BeanUtils.copyProperties(source, target);
            result.add(target);
        }
        return result;
    }

}
