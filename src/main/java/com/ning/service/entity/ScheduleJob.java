package com.ning.service.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author shenjiang
 * @since 2019-07-15
 */
@Getter
@Setter
public class ScheduleJob extends Model<ScheduleJob> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("jobName")
    private String jobName;

    @TableField("cronExpression")
    private String cronExpression;

    @TableField("springId")
    private String springId;

    @TableField("methodName")
    private String methodName;

    @TableField("jobStatus")
    private Integer jobStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
        "id=" + id +
        ", jobName=" + jobName +
        ", cronExpression=" + cronExpression +
        ", springId=" + springId +
        ", methodName=" + methodName +
        ", jobStatus=" + jobStatus +
        "}";
    }
}
