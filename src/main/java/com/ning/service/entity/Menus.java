package com.ning.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *  菜单栏
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-24
 */
@Getter
@Setter
@ToString
public class Menus extends Model<Menus> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    @TableField("menuName")
    private String menuName;

    private String url;

    private String image;

    private Integer orderValue;

    //第几层
    private Integer tier;

    @Override
    public String toString() {
        return "Menus{" +
                "id=" + id +
                ", pid=" + pid +
                ", menuName='" + menuName + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", orderValue=" + orderValue +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
