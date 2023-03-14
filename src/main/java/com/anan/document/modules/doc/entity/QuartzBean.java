package com.anan.document.modules.doc.entity;


import lombok.Data;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/1 18:22
 * @Version 1.0
 */
@Data
public class QuartzBean {
    private String id;
    private String jobName;
    private String jobClass;
    private Integer status;
    private String cronExpression;
}
