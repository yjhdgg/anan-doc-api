package com.anan.document.utils;

import com.anan.document.modules.doc.entity.QuartzBean;
import lombok.extern.java.Log;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/1 13:05
 * @Version 1.0
 */
@Log
public class QuartzUtils {
    /**
     * 创建定时任务 定时任务创建之后默认启动状态
     *
     * @param scheduler  调度器
     * @param quartzBean 定时任务信息类
     * @throws Exception
     */
    public static void createScheduleJob(Scheduler scheduler, QuartzBean quartzBean) {
        try {
            //获取到定时任务的执行类  必须是类的绝对路径名称
            //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzBean.getJobClass());
            // 构建定时任务信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzBean.getJobName()).build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            // 构建触发器trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzBean.getJobName()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            System.out.println("定时任务类路径出错：请输入类的绝对路径");
        } catch (SchedulerException e) {
            System.out.println("创建定时任务出错：" + e.getMessage());
        }
    }

    /**
     * 根据任务名称暂停定时任务
     *
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     * @throws SchedulerException
     */
    public static void pauseScheduleJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            System.out.println("暂停定时任务出错：" + e.getMessage());
        }
    }

    /**
     * 根据任务名称恢复定时任务
     *
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     * @throws SchedulerException
     */
    public static void resumeScheduleJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            System.out.println("启动定时任务出错：" + e.getMessage());
        }
    }

    /**
     * 根据任务名称立即运行一次定时任务
     *
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     * @throws SchedulerException
     */
    public static void runOnce(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            System.out.println("运行定时任务出错：" + e.getMessage());
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler  调度器
     * @param quartzBean 定时任务信息类
     * @throws SchedulerException
     */
    public static void updateScheduleJob(Scheduler scheduler, QuartzBean quartzBean) {
        try {
            //获取到对应任务的触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName());
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            //重新构建任务的触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置对应的job
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务出错：" + e.getMessage());
        }
    }

    /**
     * 根据定时任务名称从调度器当中删除定时任务
     *
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     * @throws SchedulerException
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            System.out.println("删除定时任务出错：" + e.getMessage());
        }
    }

    public static Boolean isToday(Date date) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        if (localDateTime.isBefore(endTime) && localDateTime.isAfter(startTime)) {
            return true;
        }
        return false;
    }

    public static Boolean isAfter(Date date) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        if (localDateTime.isAfter(endTime)) {
            return true;
        }
        return false;
    }

    public static Boolean isBefore(Date date){
        Date cruuentDate = new Date();
        if (date.getTime()>cruuentDate.getTime()){
            // 之后
            return false;
        }
        //之前
        return true;
    }

    /**
     * Date转为cron表达式
     *
     * @param date
     * @return
     */
    public static String getCron(java.util.Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

    private static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static List<String> f(Scheduler scheduler) {
        ArrayList<String> names = null;
        try {
            names = new ArrayList<String>();

            //再获取Scheduler下的所有group
            List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
            for (String groupName : triggerGroupNames) {
                //组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
                GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
                //获取所有的triggerKey
                Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
                for (TriggerKey triggerKey : triggerKeySet) {
                    //通过triggerKey在scheduler中获取trigger对象
                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                    //获取trigger拥有的Job
                    JobKey jobKey = trigger.getJobKey();
                    JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                    log.info("任务是：" + jobDetail.getName());
                    names.add(jobDetail.getName());
                }
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

}
