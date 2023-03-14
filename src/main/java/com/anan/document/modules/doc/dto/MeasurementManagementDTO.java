package com.anan.document.modules.doc.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/6 8:32
 * @Version 1.0
 */
@Data
public class MeasurementManagementDTO {
    @TableField("equip_num")
    private String equipNum;
    @TableField("equip_name")
    private String equipName;
    @TableField("equip_type")
    private String equipType;
    @TableField("use_depart")
    private String useDepart;
    @TableField(value = "effective_date",updateStrategy = FieldStrategy.IGNORED)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveDate;
    @TableField("ex_factory_num")
    private String exFactoryNum;
    @TableField("manufacturer")
    private String manufacturer;

    @TableField("measure_range")
    private String measureRange;
    @TableField(value = "check_date",updateStrategy = FieldStrategy.IGNORED)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date checkDate;
    @TableField(value = "running_date",updateStrategy = FieldStrategy.IGNORED)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date runningDate;
    @TableField("accuracy")
    private String accuracy;
    /**
     * 备注
     */
    private String remarks;
}
