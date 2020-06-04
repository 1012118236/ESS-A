package com.ning.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.service.entity.Menus;
import com.ning.service.vo.ResData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-24
 */
public interface IMenusService extends IService<Menus> {

    /**
     * 根据用户角色获取菜单
     * @return
     */
    ResData queryMenusByPermission();

    /**
     * 查询所有菜单以树结构返回
     * @return
     */
    ResData findAllMenusZtree();

    /**
     * 修改指定菜单
     * @param menus
     * @return
     */
    ResData updateMenu(Menus menus);
}
