package com.ning.service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.service.entity.UserRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjiang
 * @since 2019-06-14
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 修改用户角色信息
     * @param userid  用户id
     * @param roleid  角色id
     */
    public boolean updateUserRoleByUserRoleid(int userid,Integer[] roleid);

}
