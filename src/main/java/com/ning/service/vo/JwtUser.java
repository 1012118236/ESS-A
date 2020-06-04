package com.ning.service.vo;

import com.ning.service.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/5/23 14:56
 */
public class JwtUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private User user;
    private List<GrantedAuthority> authorities;

    public JwtUser() {
    }

    public JwtUser(User users, List<GrantedAuthority> grantedAuthorities){
        user = users;
        username = users.getUsername();
        id=users.getId();
        password = users.getPassword();
        authorities = grantedAuthorities;
    }

    //获取权限信息
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账号是否未过期，默认是false，记得要改一下
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 账号是否未锁定，默认是false，记得也要改一下
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 账号凭证是否未过期，默认是false，记得还要改一下
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
