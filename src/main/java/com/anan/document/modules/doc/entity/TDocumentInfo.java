package com.anan.document.modules.doc.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文档信息表实体类
 * </p>
 *
 * @author Panjp
 * @since 2021-08-23 20:30
 */
@Data
@TableName("t_document_info")
public class TDocumentInfo extends Model<TDocumentInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 附件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文档类别
     */
    @TableField("doc_type")
    private String docType;

    /**
     * 文件状态：1草稿，2：待审核，3：审核通过，4：审核不通过
     */

    private String status;

    /**
     * 附件路径
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 预览路径
     */
    @TableField("view_url")
    private String viewUrl;

    /**
     * 审核不通过原因
     */
    @TableField("failure_reason")
    private String failureReason;

    /**
     * 下载权限
     */
    @TableField("download_power")
    private String downloadPower;

    /**
     * 查看权限
     */
    @TableField("view_power")
    private String viewPower;

    /**
     * 查看次数
     */
    @TableField("view_count")
    private Long viewCount;

    /**
     * 下载次数
     */
    @TableField("download_count")
    private Long downloadCount;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 关键字
     */
    @TableField("key_word")
    private String keyWord;

    /**
     * 资料来源
     */
    @TableField("doc_from")
    private String docFrom;
    /**
     * 缩略图
     */
    private String cover;


    @TableField(value = "expire_time",updateStrategy = FieldStrategy.IGNORED)
    private Date expireTime;
    @TableField("file_code")
    private String fileCode;
    @TableField("customer_name")
    private String customerName;

    @TableField("equip_num")
    private String equipNum;
    @TableField("equip_name")
    private String equipName;
    @TableField("equip_type")
    private String equipType;
    @TableField("use_depart")
    private String useDepart;

    @TableField(value = "effective_date",updateStrategy = FieldStrategy.IGNORED)
    private Date effectiveDate;
    @TableField("ex_factory_num")
    private String exFactoryNum;

    @TableField("manufacturer")
    private String manufacturer;

    @TableField("measure_range")
    private String measureRange;
    @TableField(value = "check_date",updateStrategy = FieldStrategy.IGNORED)
    private Date checkDate;
    @TableField("dept_code")
    private String deptCode;

    @TableField("file_format")
    private String fileFormat;

    @TableField("one_type")
    private String oneType;

    @TableField("view_auth_id")
    private Integer viewAuthId;

    @TableField(value = "reminder_time",updateStrategy = FieldStrategy.IGNORED)
    private Date reminderTime;

    @TableField("is_send")
    private Integer isSend;
    @TableField(value = "running_date",updateStrategy = FieldStrategy.IGNORED)
    private Date runningDate;
    @TableField("objective")
    private String objective;
    @TableField("content")
    private String content;
    @TableField("completion")
    private String completion;

    @TableField("state")
    private Integer state;

    @TableField("accuracy")
    private String accuracy;


}
