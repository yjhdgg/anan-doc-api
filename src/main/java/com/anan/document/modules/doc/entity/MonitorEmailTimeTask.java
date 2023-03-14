package com.anan.document.modules.doc.entity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.anan.document.modules.doc.service.TDocumentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/3 10:41
 * @Version 1.0
 */
@Component
@Slf4j
public class MonitorEmailTimeTask extends QuartzJobBean {
    @Autowired
    private TDocumentInfoService baseService;

    @Autowired
    Scheduler scheduler;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //  todo 每天凌晨查询数据库是否有今天要发送的邮件
        LambdaQueryWrapper<TDocumentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(TDocumentInfo::getReminderTime, LocalDate.now().atTime(0,0,0),LocalDate.now().atTime(23,59,59    ));
//        List<TDocumentInfo> list = baseService.list(wrapper);
//
//        }

    }
}
