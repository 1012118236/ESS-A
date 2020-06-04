package com.ning.service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.service.entity.Permission;
import com.ning.service.vo.ResData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-29
 */
public interface IPermissionService extends IService<Permission> {

    /**
     *
     * @return
     */
    ResData findAllPermissionWhatKey();

    /**
     * 查询所有权限
     * @return
     */
    ResData findAllPermission();

    /**
     * 保存或更改
     * @param permission
     * @return
     */
    ResData saveOrUpdatePermission(Permission permission);

    ResData findPermissionByUser();
}
