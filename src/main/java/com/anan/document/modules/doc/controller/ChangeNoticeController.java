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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/22 14:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/changeNotice")
public class ChangeNoticeController extends BaseController {
    @Autowired
    private TDocumentInfoService baseService;

    @PostMapping("/tempt")
    public ApiRest tempList(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {

        // 是临时文件
        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TDocumentInfo::getDocType, reqDTO.getParams().getDocType());
        queryWrapper.ge(TDocumentInfo::getExpireTime, LocalDateTime.now());
        IPage<TDocumentInfo> page = new Page<TDocumentInfo>(reqDTO.getCurrent(), reqDTO.getSize());
        baseService.page(page, queryWrapper);
        return super.success(page);

    }

    @PostMapping("/list")
    public ApiRest list(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {
        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
        String nameStr = "zzDCC,zz,jhDCC,jh,mkDCC,mk,mkDCC,yxkjDCC,yxkj";
        String userName = reqDTO.getUserId();
        // 是分厂用户
        if (userName != null) {
            if (nameStr.contains(userName)) {
                TDocumentInfoDTO params = reqDTO.getParams();
                if ("zzDCC".equals(userName) || "zz".equals(userName)) {
                    params.setOneType("2");
                }
                if (userName.equals("jh") || userName.equals("jhDCC")) {
                    params.setOneType("3");
                }
                if (userName.equals("mk") || userName.equals("mkDCC")) {
                    params.setOneType("5");
                }
                if (userName.equals("yxkj") || userName.equals("yxkjDCC")) {
                    params.setOneType("6");
                }
            }
        }

        if (reqDTO.getParams().getOneType() != null) {
            queryWrapper.eq(TDocumentInfo::getOneType, reqDTO.getParams().getOneType());
        }
        queryWrapper.eq(TDocumentInfo::getDocType, reqDTO.getParams().getDocType());
        queryWrapper.ge(TDocumentInfo::getExpireTime, LocalDateTime.now());
        Page<TDocumentInfo> page = new Page<TDocumentInfo>(reqDTO.getCurrent(), reqDTO.getSize());
        baseService.page(page, queryWrapper);
        return super.success(page);

    }

    @PostMapping("/history/list")
    public ApiRest listHistory(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {
        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();

        String nameStr = "zzDCC,zz,jhDCC,jh,mkDCC,mk,mkDCC,yxkjDCC,yxkj";
        String userName = reqDTO.getUserId();
        // 是分厂用户
        if (userName != null) {
            if (nameStr.contains(userName)) {
                TDocumentInfoDTO params = reqDTO.getParams();
                if ("zzDCC".equals(userName) || "zz".equals(userName)) {
                    params.setOneType("2");
                }
                if (userName.equals("jh") || userName.equals("jhDCC")) {
                    params.setOneType("3");
                }
                if (userName.equals("mk") || userName.equals("mkDCC")) {
                    params.setOneType("5");
                }
                if (userName.equals("yxkj") || userName.equals("yxkjDCC")) {
                    params.setOneType("6");
                }
            }
        }

        if (reqDTO.getParams().getOneType() != null) {
            queryWrapper.eq(TDocumentInfo::getOneType, reqDTO.getParams().getOneType());
        }

        queryWrapper.eq(TDocumentInfo::getDocType, "1607562215135584257");
        queryWrapper.lt(TDocumentInfo::getExpireTime, LocalDateTime.now());
        Page<TDocumentInfo> page = new Page<TDocumentInfo>(reqDTO.getCurrent(), reqDTO.getSize());
        baseService.page(page, queryWrapper);
        return super.success(page);

    }

}
