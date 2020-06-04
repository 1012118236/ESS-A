package com.ning.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author shenjiang
 * @since 2019-06-14
 */
@Getter
@Setter
public class MenusRole extends Model<MenusRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer menusId;

    private Integer roleId;

    public MenusRole(Integer menusId, Integer roleId) {
        this.menusId = menusId;
        this.roleId = roleId;
    }

    public MenusRole() {
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MenusRole{" +
        "id=" + id +
        ", menusId=" + menusId +
        ", roleId=" + roleId +
        "}";
    }
}
