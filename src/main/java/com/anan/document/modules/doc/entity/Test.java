package com.anan.document.modules.doc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 实验室表
 * @TableName test
 */
@TableName(value ="test")
@Data
public class Test implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 委托人
     */
    @TableField(value = "trustee")
    private String trustee;

    /**
     * 资料分类
     */
    @TableField(value = "docType")
    private String docType;

    /**
     * 委托单号
     */
    @TableField(value = "ticketNum")
    private String ticketNum;

    /**
     * 产品批号
     */
    @TableField(value = "prodNum")
    private String prodNum;

    /**
     * 芯片型号
     */
    @TableField(value = "chipModel")
    private String chipModel;

    /**
     * 贴片胶
     */
    @TableField(value = "smdGlue")
    private String smdGlue;

    /**
     * 载带
     */
    @TableField(value = "zd")
    private String zd;

    /**
     * 线材
     */
    @TableField(value = "wireRod")
    private String wireRod;

    /**
     * 封装料
     */
    @TableField(value = "encapsulation")
    private String encapsulation;

    /**
     * 筑坝胶
     */
    @TableField(value = "damGlue")
    private String damGlue;

    /**
     * 实验目的
     */
    @TableField(value = "objective")
    private String objective;

    /**
     * 实验内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 完成情况
     */
    @TableField(value = "completion")
    private String completion;

    /**
     * 上传附件
     */
    @TableField(value = "fileUrl")
    private String fileUrl;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}