package com.ning.service.service.impl;


import com.ning.service.dao.UserDao;
import com.ning.service.entity.Permission;
import com.ning.service.entity.Role;
import com.ning.service.entity.User;
import com.ning.service.mapper.PermissionMapper;
import com.ning.service.mapper.RoleMapper;
import com.ning.service.vo.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenjiang
 * @Description: JWT 认证服务
 * @Date: 2019/5/23 11:40
 */
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 通过email查询用户
     * @param
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.queryUserByUsername(username);
        if(user!=null){
            //权限不需要
            List<Permission> permissions = permissionMapper.findByAdminUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (Permission permission : permissions) {
                if(permission != null && permission.getName()!=null){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            //角色前需要带ROLE_
            List<Role> roleByUserId = roleMapper.getRoleByUserId(user.getId());
            for (Role role : roleByUserId) {
                if(role!=null&&role.getRoleName()!=null){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new JwtUser(user,grantedAuthorities);
        }else{
            throw new UsernameNotFoundException("admin: "+username+"do not exist!");
        }
    }
}
