package com.ning.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 *  用户信息类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-22
 */
@Getter
@Setter
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    // 0 正常  1 停用
    private Integer isDvailable;

    private String userUuid;

    private String username;

    private String password;

    private String email;

    private String telephone;

    private String image;

    private String lastIp;

    private Timestamp lastTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
