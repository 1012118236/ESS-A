package com.ning.service.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.entity.UserRole;
import com.ning.service.mapper.UserRoleMapper;
import com.ning.service.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjiang
 * @since 2019-06-14
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean updateUserRoleByUserRoleid(int userid, Integer[] roleid) {
        try {
            userRoleMapper.deleteRoleByUserId(userid);
            for (int rid : roleid) {
                UserRole userRole = new UserRole(userid, rid);
                userRoleMapper.insert(userRole);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
