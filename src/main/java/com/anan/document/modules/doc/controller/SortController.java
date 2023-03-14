package com.anan.document.modules.doc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yf.boot.base.api.api.ApiRest;
import com.yf.boot.base.api.api.controller.BaseController;
import com.anan.document.modules.doc.dto.TDocumentInfoDTO;
import com.anan.document.modules.doc.entity.PagingDTO;
import com.anan.document.modules.doc.entity.TDocumentInfo;
import com.anan.document.modules.doc.service.TDocumentInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/21 16:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/sort")
@Slf4j
@Api(tags = "排序")
public class SortController extends BaseController {
    @Autowired
    private TDocumentInfoService baseService;

    @PostMapping("/measureMange/desc")
    public ApiRest sortMeasureDesc(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {
        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
        TDocumentInfoDTO params = reqDTO.getParams();
        queryWrapper.eq(TDocumentInfo::getDocType,params.getDocType());
        queryWrapper.orderByDesc(TDocumentInfo::getEquipNum);
        IPage<TDocumentInfo> page = new Page<>(reqDTO.getCurrent(), reqDTO.getSize());
        baseService.page(page, queryWrapper);
        return super.success(page);
    }
    @PostMapping("/measureMange/asc")
    public ApiRest sortMeasureAsc(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {
        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
        TDocumentInfoDTO params = reqDTO.getParams();
        queryWrapper.eq(TDocumentInfo::getDocType,params.getDocType());
        queryWrapper.orderByAsc(TDocumentInfo::getEquipNum);
        IPage<TDocumentInfo> page = new Page<>(reqDTO.getCurrent(), reqDTO.getSize());
        baseService.page(page, queryWrapper);
        return super.success(page);
    }

    @PostMapping("/qualification/asc")
    public ApiRest sortAsc(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {
        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
        TDocumentInfoDTO params = reqDTO.getParams();
        queryWrapper.eq(TDocumentInfo::getDocType, params.getDocType());
        if ("1603569196700811265".equals(params.getDocType()) || "1603569246575280129".equals(params.getDocType()) ||
                "1607564404251893761".equals(params.getDocType()) || "1607169208351711234".equals(params.getDocType())) {
            queryWrapper.orderByAsc(TDocumentInfo::getUpdateTime);
        } else if ("1603576853188853762".equals(params.getDocType())) {
            queryWrapper.orderByAsc(TDocumentInfo::getEffectiveDate);
        } else {
            queryWrapper.orderByAsc(TDocumentInfo::getCreateTime);
        }


        IPage<TDocumentInfo> page = new Page<>(reqDTO.getCurrent(), reqDTO.getSize());
        baseService.page(page, queryWrapper);

        return super.success(page);
    }

    @PostMapping("/qualification/desc")
    public ApiRest sortDesc(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {
        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TDocumentInfo::getDocType, reqDTO.getParams().getDocType());
        TDocumentInfoDTO params = reqDTO.getParams();
        IPage<TDocumentInfo> page = new Page<>(reqDTO.getCurrent(), reqDTO.getSize());

        if ("1603569196700811265".equals(params.getDocType()) || "1603569246575280129".equals(params.getDocType()) || "1607564404251893761".equals(params.getDocType())) {
            queryWrapper.orderByDesc(TDocumentInfo::getUpdateTime);
        } else if ("1603576853188853762".equals(params.getDocType())) {
            queryWrapper.orderByDesc(TDocumentInfo::getEffectiveDate);
        } else {
            queryWrapper.orderByDesc(TDocumentInfo::getCreateTime);
        }
        baseService.page(page, queryWrapper);

        return super.success(page);
    }
}
