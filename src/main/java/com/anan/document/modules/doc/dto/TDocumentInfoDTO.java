package com.anan.document.modules.doc.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yf.boot.base.api.annon.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文档信息表数据传输类
 * </p>
 *
 * @author Panjp
 * @since 2021-08-23 20:30
 */
@Data
@ApiModel(value = "文档信息表", description = "文档信息表")
public class TDocumentInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", required = true)
    private String id;

    @ApiModelProperty(value = "创建人", required = true)
    private String createBy;

    @ApiModelProperty(value = "修改人", required = true)
    private String updateBy;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", required = true)
    private Date updateTime;

    @ApiModelProperty(value = "文档标题")
    private String title;

    @ApiModelProperty(value = "附件类型", required = true)
    private String fileType;

    @Dict(dicCode = "document_type")
    @ApiModelProperty(value = "文档类别", required = true)
    private String docType;
    @Dict(dicCode = "doc_status")
    @ApiModelProperty(value = "文件状态：1草稿，2：待审核，3：审核通过，4：审核不通过", required = true)
    private String status;

    @ApiModelProperty(value = "附件路径")
    private String fileUrl;

    @ApiModelProperty(value = "预览路径")
    private String viewUrl;

    @ApiModelProperty(value = "审核不通过原因", required = true)
    private String failureReason;

    @ApiModelProperty(value = "下载权限")
    private String downloadPower;

    @ApiModelProperty(value = "查看权限")
    private String viewPower;

    @ApiModelProperty(value = "查看次数")
    private Long viewCount;

    @ApiModelProperty(value = "下载次数")
    private Long downloadCount;


    @ApiModelProperty(value = "关键字")
    private String keyWord;

    @ApiModelProperty(value = "资料来源")
    private String docFrom;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "缩略图")
    private String cover;

    @ApiModelProperty(value = "排序字段")
    private String sortType;

    @ApiModelProperty(value = "排序顺序")
    private boolean asc;

    @ApiModelProperty(value = "过期时间", required = false)
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    @ApiModelProperty(value = "文件编号")
    private String fileCode;
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty("设备编号")
    private String equipNum;
    @ApiModelProperty("设备名称")
    private String equipName;
    @ApiModelProperty("设备类型")
    private String equipType;
    @ApiModelProperty("使用部门")
    private String useDepart;

    @ApiModelProperty("生效日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveDate;
    @ApiModelProperty("出厂编号")
    private String exFactoryNum;

    @ApiModelProperty("生产厂家")
    private String manufacturer;

    @ApiModelProperty("测量范围")
    private String measureRange;

    @ApiModelProperty("校验日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkDate;

    private String deptCode;

    private String fileFormat;
    /**
     * ture：覆盖之前的文件
     * false：
     */
    private Boolean isCover;

    /**
     * 文件分类
     * 分厂、通用....
     */
    private String oneType;

    private Integer ViewAuthId;

    private Date reminderTime;

    private Integer isSend;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date runningDate;

    private List<String> docIds;
    private String objective;

    private String content;

    private String completion;

    private Integer state;
    private String accuracy;
    @Override
    public String toString() {
        return "TDocumentInfoDTO{" +
                "id='" + id + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", title='" + title + '\'' +
                ", fileType='" + fileType + '\'' +
                ", docType='" + docType + '\'' +
                ", status='" + status + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", viewUrl='" + viewUrl + '\'' +
                ", failureReason='" + failureReason + '\'' +
                ", downloadPower='" + downloadPower + '\'' +
                ", viewPower='" + viewPower + '\'' +
                ", viewCount=" + viewCount +
                ", downloadCount=" + downloadCount +
                ", keyWord='" + keyWord + '\'' +
                ", docFrom='" + docFrom + '\'' +
                ", remarks='" + remarks + '\'' +
                ", cover='" + cover + '\'' +
                ", sortType='" + sortType + '\'' +
                ", asc=" + asc +
                ", expireTime=" + expireTime +
                ", fileCode='" + fileCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", equipNum='" + equipNum + '\'' +
                ", equipName='" + equipName + '\'' +
                ", equipType='" + equipType + '\'' +
                ", useDepart='" + useDepart + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", exFactoryNum='" + exFactoryNum + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", measureRange='" + measureRange + '\'' +
                ", checkDate=" + checkDate +
                ", deptCode='" + deptCode + '\'' +
                '}';
    }
}
