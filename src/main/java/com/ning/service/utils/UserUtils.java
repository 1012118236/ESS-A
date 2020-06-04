package com.ning.service.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/5/29 15:34
 */
public class UserUtils {

    /**
     * 获取当前登陆用户名
     * @return
     */
    public static String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 获取当前用户权限和角色
     * @return
     */
    public static Map<String, Object> getPermission(){
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Map<String, Object> data = new HashMap<>();
        List<String> permission = new ArrayList<>();
        List<String> role = new ArrayList<>();
        if(authorities!=null) {
            for (GrantedAuthority authority : authorities) {
                String authority1 = authority.getAuthority();
                if(authority1.matches("^ROLE_.*$")){
                    role.add(authority1);
                }else{
                    permission.add(authority1);
                }
            }
        }
        data.put("permission",permission);
        data.put("role",role);
        return data;
    }
}
