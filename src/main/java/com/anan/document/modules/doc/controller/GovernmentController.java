package com.anan.document.modules.doc.controller;

import com.anan.document.modules.doc.entity.PagingDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yf.boot.base.api.api.ApiRest;
import com.yf.boot.base.api.api.controller.BaseController;
import com.anan.document.modules.doc.entity.TDocumentInfo;
import com.anan.document.modules.doc.service.TDocumentInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/10 15:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/doc/government")
public class GovernmentController extends BaseController {

    @Autowired
    private TDocumentInfoService baseService;

    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/paging", method = {RequestMethod.POST})
    public ApiRest<IPage<TDocumentInfo>> paging(@RequestBody PagingDTO<TDocumentInfo> reqDTO, HttpServletResponse response) throws IOException {
        Page<TDocumentInfo> page = new Page<>(reqDTO.getCurrent(), reqDTO.getSize());
        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TDocumentInfo::getDocType,"1603584049377198082");
        queryWrapper.orderByDesc(TDocumentInfo::getCreateTime);
        baseService.page(page,queryWrapper);
        return success(page);
    }
}