package com.anan.document.modules.doc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_doc_integrated
 */
@TableName(value ="t_doc_integrated")
@Data
public class Integrated implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件编号
     */
    @TableField(value = "file_code")
    private String fileCode;

    /**
     * 资料名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 资料分类
     */
    @TableField(value = "doc_type")
    private String docType;

    /**
     * 生效日期
     */
    @TableField(value = "effective_date")
    private Date effectiveDate;

    /**
     * 附件路径
     */
    @TableField(value = "file_url")
    private String fileUrl;

    /**
     * 资料简介
     */
    @TableField(value = "remarks")
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Integrated other = (Integrated) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFileCode() == null ? other.getFileCode() == null : this.getFileCode().equals(other.getFileCode()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getDocType() == null ? other.getDocType() == null : this.getDocType().equals(other.getDocType()))
            && (this.getEffectiveDate() == null ? other.getEffectiveDate() == null : this.getEffectiveDate().equals(other.getEffectiveDate()))
            && (this.getFileUrl() == null ? other.getFileUrl() == null : this.getFileUrl().equals(other.getFileUrl()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFileCode() == null) ? 0 : getFileCode().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getDocType() == null) ? 0 : getDocType().hashCode());
        result = prime * result + ((getEffectiveDate() == null) ? 0 : getEffectiveDate().hashCode());
        result = prime * result + ((getFileUrl() == null) ? 0 : getFileUrl().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileCode=").append(fileCode);
        sb.append(", title=").append(title);
        sb.append(", docType=").append(docType);
        sb.append(", effectiveDate=").append(effectiveDate);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", remarks=").append(remarks);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}