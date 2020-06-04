package com.ning.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ning.service.entity.Menus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/6/27 17:13
 */
public class MenusVo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    @TableField("menuName")
    private String menuName;

    private String url;

    private String image;

    private Integer orderValue;

    private Integer tier;

    private List<MenusVo> children;

    public MenusVo() {
    }

    public MenusVo(Menus menus) {
        this.id = menus.getId();
        this.pid = menus.getPid();
        this.menuName = menus.getMenuName();
        this.url = menus.getUrl();
        this.orderValue = menus.getOrderValue();
        this.image = menus.getImage();
        this.tier = menus.getTier();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MenusVo(Integer id, Integer pid, String menuName, String url, Integer orderValue) {
        this.id = id;
        this.pid = pid;
        this.menuName = menuName;
        this.url = url;
        this.orderValue = orderValue;
    }

    public List<MenusVo> getChildren() {
        return children==null?new ArrayList<MenusVo>():children;
    }

    public void setChildren(List<MenusVo> menusList) {
        this.children = menusList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public MenusVo(Integer id, Integer pid, String menuName, String url, String image, Integer orderValue, Integer tier, List<MenusVo> children) {
        this.id = id;
        this.pid = pid;
        this.menuName = menuName;
        this.url = url;
        this.image = image;
        this.orderValue = orderValue;
        this.tier = tier;
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenusVo{" +
                "id=" + id +
                ", pid=" + pid +
                ", menuName='" + menuName + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", orderValue=" + orderValue +
                ", tier=" + tier +
                ", children=" + children +
                '}';
    }
}
