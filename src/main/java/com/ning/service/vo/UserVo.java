package com.ning.service.vo;

import java.util.List;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/6/14 10:53
 */
public class UserVo {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String telephone;
    private List<Integer> roleids;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Integer> getRoleids() {
        return roleids;
    }

    public void setRoleids(List roleids) {
        this.roleids = roleids;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
