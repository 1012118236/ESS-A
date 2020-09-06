package com.ning.service.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author shenjiang
 * @since 2020-09-05
 */
public class MessageTwo extends Model<MessageTwo> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 事件类型
     */
    private String type;

    /**
     * "0:新增,1:发布2:已处理3:处理失败"
     */
    private Integer process;

    /**
     * 操作内容
     */
    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MessageTwo{" +
        "id=" + id +
        ", type=" + type +
        ", process=" + process +
        ", content=" + content +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
