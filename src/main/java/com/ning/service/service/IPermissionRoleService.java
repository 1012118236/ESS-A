package com.ning.service.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.service.entity.PermissionRole;
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
public interface IPermissionRoleService extends IService<PermissionRole> {

    /**
     * 根据角色ID查询角色权限
     * @param id
     * @return
     */
    ResData findPermissionByRoleId(Integer id);

    ResData savePermissionRole(Map<String,Object> map);
}
