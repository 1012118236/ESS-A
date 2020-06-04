package com.ning.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.service.entity.Permission;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-29
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    public List<Permission> findPermissionByUser(String username);

    /**
     * 查询所有权限
     * @return
     */
    public List<Permission> findAll();

    /**
     * 根据用户ID查询用户权限
     * @param userId
     * @return
     */
    public List<Permission> findByAdminUserId(int userId);
}
