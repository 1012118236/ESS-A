package com.ning.service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.service.entity.MenusRole;
import com.ning.service.vo.ResData;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjiang
 * @since 2019-06-14
 */
public interface IMenusRoleService extends IService<MenusRole> {

    ResData findMenusIdByRoleId(Integer roleId);
    
    ResData saveMenusRole(Map<String, Object> map);
    
    
}
