package com.anan.document.modules.doc.entity;

import cn.hutool.core.util.StrUtil;
import com.anan.document.modules.constant.QuartzConstant;
import com.anan.document.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/1 18:24
 * @Version 1.0
 */
@Slf4j

public class EmailTask extends QuartzJobBean {
    @Autowired
    Scheduler scheduler;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 定时任务的执行逻辑
        System.out.println("简单的定时任务执行时间：" + new Date().toLocaleString());
        String docType = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.DOC_TYPE);
        String title = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.FILE_NAME);
        String jobName = "title:" +title + ",docType:" + docType+new Date().getTime();
        String content = "尊敬的OA用户，您好！\n" +
                title  + "报告即将到期，请及时处理！\n";
        // 测试邮箱提醒
//        String[] strings = {"1363022366@qq.com"};
//        sendEmail(strings, content);

// 上线时开启 邮件提醒
        if (StringUtils.isNotBlank(docType)) {
            String emails[] = null;

            switch (docType) {
                // 环境管理物质报告
                case "1603569246575280129": {
                    emails = QuartzConstant.ENVIRONMENT_REPORT_EMAIL;
                    sendEmail(emails, content);
                    break;
                }
                // MSDS
                case "1607564404251893761": {
                    emails = QuartzConstant.MSDS_EMAIL;
                    sendEmail(emails, content);
                    break;
                }
                // 供应商资质
                case "1607169208351711234": {
                    emails = emails = QuartzConstant.SUPPLIER_EMAIL;
                    String customerName = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.CUSTOMER_NAME);
                    content = "尊敬的OA用户，您好！\n" +
                            customerName + "的" + title + "报告即将到期，请及时处理！\n";
                    sendEmail(emails, content);
                    break;
                }
                // 计量管理
                case "1603576853188853762": {
                    emails = emails = QuartzConstant.MEA_MANAGEMENT_EMAIL;
                    String equipNumber = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.MEA_MANAGEMENT_EQUIP_NUM);
                    String equipName = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.MEA_MANAGEMENT_EQUIP_NAME);
                    content = "尊敬的OA用户，您好！\n" +
                           "设备编号为："+ equipNumber + "的" + equipName + "计量设备校准日期临近，请及时处理！\n";
                    sendEmail(emails, content);
                    break;
                }
                // 临时文件
                case "1603584109112475650": {
                    String oneType = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.ONETYPE);
                    if (StrUtil.isBlank(oneType)) {
                        break;
                    }
                    String fileCode = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.FILE_CODE);
                    content = "尊敬的OA用户，您好！\n" +
                            "文件编号为：" +  fileCode +"的"+ title + "报告即将到期，请及时处理！\n";
                    if (oneType.equals("2")){
                        emails = emails = QuartzConstant.TEMPT_2_FILE_EMAIL;
                    }
                    if (oneType.equals("3")){
                        emails = emails = QuartzConstant.TEMPT_3_FILE_EMAIL;
                    }
                    if (oneType.equals("5")){
                        emails = emails = QuartzConstant.TEMPT_5_FILE_EMAIL;
                    }
                    if (oneType.equals("6")){
                        emails = emails = QuartzConstant.TEMPT_6_FILE_EMAIL;
                    }


                    sendEmail(emails, content);
                    break;
                }
                // 变更通知
                case "1607562215135584257": {
//                    String fileCode = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.FILE_CODE);
//                    content = "尊敬的OA用户，您好！\n" +
//                            "文件编号为：" +  fileCode +"的"+ title + "报告即将到期，请及时处理！\n";
//                    emails = QuartzConstant.CHANGE_NOTICE_OR_TECHNICAL_DOC_EMAIL;
//                    sendEmail(emails, content);
//                    break;
                    String oneType = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.ONETYPE);
                    if (StrUtil.isBlank(oneType)) {
                        break;
                    }
                    String fileCode = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.FILE_CODE);
                    content = "尊敬的OA用户，您好！\n" +
                            "文件编号为：" +  fileCode +"的"+ title + "报告即将到期，请及时处理！\n";
                    if (oneType.equals("2")){
                        emails = emails = QuartzConstant.TEMPT_2_FILE_EMAIL;
                    }
                    if (oneType.equals("3")){
                        emails = emails = QuartzConstant.TEMPT_3_FILE_EMAIL;
                    }
                    if (oneType.equals("5")){
                        emails = emails = QuartzConstant.TEMPT_5_FILE_EMAIL;
                    }
                    if (oneType.equals("6")){
                        emails = emails = QuartzConstant.TEMPT_6_FILE_EMAIL;
                    }


                    sendEmail(emails, content);
                    break;
                }
                case "1607562240435625986": {
//                    String fileCode = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.FILE_CODE);
//                    content = "尊敬的OA用户，您好！\n" +
//                            "文件编号为：" +  fileCode +"的"+ title + "报告即将到期，请及时处理！\n";
//                    emails = QuartzConstant.CHANGE_NOTICE_OR_TECHNICAL_DOC_EMAIL;
//                    sendEmail(emails, content);
//                    break;
                    String oneType = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.ONETYPE);
                    if (StrUtil.isBlank(oneType)) {
                        break;
                    }
                    String fileCode = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzConstant.FILE_CODE);
                    content = "尊敬的OA用户，您好！\n" +
                            "文件编号为：" +  fileCode +"的"+ title + "报告即将到期，请及时处理！\n";
                    if (oneType.equals("2")){
                        emails = emails = QuartzConstant.TEMPT_2_FILE_EMAIL;
                    }
                    if (oneType.equals("3")){
                        emails = emails = QuartzConstant.TEMPT_3_FILE_EMAIL;
                    }
                    if (oneType.equals("5")){
                        emails = emails = QuartzConstant.TEMPT_5_FILE_EMAIL;
                    }
                    if (oneType.equals("6")){
                        emails = emails = QuartzConstant.TEMPT_6_FILE_EMAIL;
                    }


                    sendEmail(emails, content);
                    break;
                }
                default: {
                    throw new JobExecutionException("未找到要发送的文件类型;" + Thread.currentThread().toString());
                }
            }
        }

    }

    public void sendEmail(String[] emails, String content) {
        try {
            EmailUtils.sendEmail(emails, content);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
