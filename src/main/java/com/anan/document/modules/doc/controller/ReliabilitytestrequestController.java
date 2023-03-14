package com.anan.document.modules.doc.controller;

import cn.hutool.core.util.StrUtil;
import com.anan.document.modules.doc.entity.IdRequest;
import com.anan.document.modules.doc.entity.Reliabilitytestrequest;
import com.anan.document.modules.doc.service.ReliabilitytestrequestService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yf.boot.base.api.api.ApiRest;
import com.yf.boot.base.api.api.controller.BaseController;
import com.anan.document.modules.doc.dto.PageDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/11 16:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/doc/test")
public class ReliabilitytestrequestController extends BaseController {
    @Resource
    ReliabilitytestrequestService reliabilitytestrequestService;
    @PostMapping("/add")
    public ApiRest addReliability(@RequestBody Reliabilitytestrequest reliabilitytestrequest){
        boolean save = reliabilitytestrequestService.save(reliabilitytestrequest);
        if (!save) {
            throw new RuntimeException("Reliability test request save failed");
        }
        return success();
    }

    @PostMapping("/update")
    public ApiRest updateReliability(@RequestBody Reliabilitytestrequest reliabilitytestrequest){
        boolean update = reliabilitytestrequestService.updateById(reliabilitytestrequest);
        if (!update) {
            throw new RuntimeException("Reliability test request save failed");
        }
        return success();
    }
    @PostMapping("/detail")
    public ApiRest getByid(@RequestBody IdRequest id){
        Reliabilitytestrequest reliabilitytestrequest = reliabilitytestrequestService.getById(id.getId());
        if (reliabilitytestrequest == null) {
            throw new RuntimeException("Reliability test request getByid failed");
        }
        return success(reliabilitytestrequest);
    }

    @PostMapping("/paging")
    public ApiRest getPage(@RequestBody PageDto<Reliabilitytestrequest> reqDto){
        Integer current = reqDto.getCurrent();
        Integer size = reqDto.getSize();
        String query = reqDto.getQuery();
        Reliabilitytestrequest params = reqDto.getParams();
        if (current==null || size==null){
            throw new IllegalArgumentException("参数为空");
        }
        Page<Reliabilitytestrequest> page = new Page<>(current, size);
        LambdaQueryWrapper<Reliabilitytestrequest> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query)){
            queryWrapper.like(Reliabilitytestrequest::getLotnum,query);
            queryWrapper.or();
            queryWrapper.like(Reliabilitytestrequest::getOrdernum,query);
            queryWrapper.or();
            queryWrapper.like(Reliabilitytestrequest::getChipmodel,query);
            queryWrapper.or();
            queryWrapper.like(Reliabilitytestrequest::getPrincipal,query);
        }
        reliabilitytestrequestService.page(page,queryWrapper);
        return success(page);
    }

//    public ApiRest delete()
}
