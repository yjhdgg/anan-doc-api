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
 * @TableName reliabilitytestrequest
 */
@TableName(value ="reliabilitytestrequest")
@Data
public class Reliabilitytestrequest implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 委托人
     */
    @TableField(value = "principal")
    private String principal;

    /**
     * 车间
     */
    @TableField(value = "workshop")
    private String workshop;

    /**
     * 委托单号
     */
    @TableField(value = "orderNum")
    private String ordernum;

    /**
     * 产品批号
     */
    @TableField(value = "lotNum")
    private String lotnum;

    /**
     * 芯片型号
     */
    @TableField(value = "chipModel")
    private String chipmodel;

    /**
     * 实验类型
     */
    @TableField(value = "experimentType")
    private String experimenttype;

    /**
     * 实验等级
     */
    @TableField(value = "experimentLevel")
    private String experimentlevel;

    /**
     * 湿敏等级
     */
    @TableField(value = "humiditySensitivityLevel")
    private String humiditysensitivitylevel;

    /**
     * 贴片胶
     */
    @TableField(value = "heraeus")
    private String heraeus;

    /**
     * 载带/框架
     */
    @TableField(value = "carrierFrame")
    private String carrierframe;

    /**
     * 筑坝胶
     */
    @TableField(value = "dammingGlue")
    private String dammingglue;

    /**
     * 线材
     */
    @TableField(value = "wireRod")
    private String wirerod;

    /**
     * 塑封料/滴胶
     */
    @TableField(value = "suLiaoFengJiao")
    private String suliaofengjiao;

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
    private String fileurl;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    /**
     * 
     */
    @TableField(value = "createTime")
    private Date createtime;

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
        Reliabilitytestrequest other = (Reliabilitytestrequest) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPrincipal() == null ? other.getPrincipal() == null : this.getPrincipal().equals(other.getPrincipal()))
            && (this.getWorkshop() == null ? other.getWorkshop() == null : this.getWorkshop().equals(other.getWorkshop()))
            && (this.getOrdernum() == null ? other.getOrdernum() == null : this.getOrdernum().equals(other.getOrdernum()))
            && (this.getLotnum() == null ? other.getLotnum() == null : this.getLotnum().equals(other.getLotnum()))
            && (this.getChipmodel() == null ? other.getChipmodel() == null : this.getChipmodel().equals(other.getChipmodel()))
            && (this.getExperimenttype() == null ? other.getExperimenttype() == null : this.getExperimenttype().equals(other.getExperimenttype()))
            && (this.getExperimentlevel() == null ? other.getExperimentlevel() == null : this.getExperimentlevel().equals(other.getExperimentlevel()))
            && (this.getHumiditysensitivitylevel() == null ? other.getHumiditysensitivitylevel() == null : this.getHumiditysensitivitylevel().equals(other.getHumiditysensitivitylevel()))
            && (this.getHeraeus() == null ? other.getHeraeus() == null : this.getHeraeus().equals(other.getHeraeus()))
            && (this.getCarrierframe() == null ? other.getCarrierframe() == null : this.getCarrierframe().equals(other.getCarrierframe()))
            && (this.getDammingglue() == null ? other.getDammingglue() == null : this.getDammingglue().equals(other.getDammingglue()))
            && (this.getWirerod() == null ? other.getWirerod() == null : this.getWirerod().equals(other.getWirerod()))
            && (this.getSuliaofengjiao() == null ? other.getSuliaofengjiao() == null : this.getSuliaofengjiao().equals(other.getSuliaofengjiao()))
            && (this.getObjective() == null ? other.getObjective() == null : this.getObjective().equals(other.getObjective()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCompletion() == null ? other.getCompletion() == null : this.getCompletion().equals(other.getCompletion()))
            && (this.getFileurl() == null ? other.getFileurl() == null : this.getFileurl().equals(other.getFileurl()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPrincipal() == null) ? 0 : getPrincipal().hashCode());
        result = prime * result + ((getWorkshop() == null) ? 0 : getWorkshop().hashCode());
        result = prime * result + ((getOrdernum() == null) ? 0 : getOrdernum().hashCode());
        result = prime * result + ((getLotnum() == null) ? 0 : getLotnum().hashCode());
        result = prime * result + ((getChipmodel() == null) ? 0 : getChipmodel().hashCode());
        result = prime * result + ((getExperimenttype() == null) ? 0 : getExperimenttype().hashCode());
        result = prime * result + ((getExperimentlevel() == null) ? 0 : getExperimentlevel().hashCode());
        result = prime * result + ((getHumiditysensitivitylevel() == null) ? 0 : getHumiditysensitivitylevel().hashCode());
        result = prime * result + ((getHeraeus() == null) ? 0 : getHeraeus().hashCode());
        result = prime * result + ((getCarrierframe() == null) ? 0 : getCarrierframe().hashCode());
        result = prime * result + ((getDammingglue() == null) ? 0 : getDammingglue().hashCode());
        result = prime * result + ((getWirerod() == null) ? 0 : getWirerod().hashCode());
        result = prime * result + ((getSuliaofengjiao() == null) ? 0 : getSuliaofengjiao().hashCode());
        result = prime * result + ((getObjective() == null) ? 0 : getObjective().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCompletion() == null) ? 0 : getCompletion().hashCode());
        result = prime * result + ((getFileurl() == null) ? 0 : getFileurl().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", principal=").append(principal);
        sb.append(", workshop=").append(workshop);
        sb.append(", ordernum=").append(ordernum);
        sb.append(", lotnum=").append(lotnum);
        sb.append(", chipmodel=").append(chipmodel);
        sb.append(", experimenttype=").append(experimenttype);
        sb.append(", experimentlevel=").append(experimentlevel);
        sb.append(", humiditysensitivitylevel=").append(humiditysensitivitylevel);
        sb.append(", heraeus=").append(heraeus);
        sb.append(", carrierframe=").append(carrierframe);
        sb.append(", dammingglue=").append(dammingglue);
        sb.append(", wirerod=").append(wirerod);
        sb.append(", suliaofengjiao=").append(suliaofengjiao);
        sb.append(", objective=").append(objective);
        sb.append(", content=").append(content);
        sb.append(", completion=").append(completion);
        sb.append(", fileurl=").append(fileurl);
        sb.append(", remarks=").append(remarks);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}