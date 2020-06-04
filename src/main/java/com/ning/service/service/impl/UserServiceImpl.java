package com.ning.service.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.dao.UserDao;
import com.ning.service.entity.User;
import com.ning.service.mapper.UserMapper;
import com.ning.service.service.IUserRoleService;
import com.ning.service.service.IUserService;
import com.ning.service.utils.VoPoConverter;
import com.ning.service.vo.ResData;
import com.ning.service.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<Map<String,Object>> findAllUser() {
        List<Map<String, Object>> userAll = userMapper.findUserAll();
        Map<String, Map<String,Object>> data = new HashMap<String, Map<String,Object>>();
        for (Map<String, Object> map1 : userAll) {
            String id = map1.get("id").toString();
            String role_name = map1.get("role_name")!=null?map1.get("role_name").toString():null;
            String role_id = map1.get("role_id")!=null?map1.get("role_id").toString():null;
            String role_chinese_name = map1.get("role_chinese_name")!=null?map1.get("role_chinese_name").toString():null;
            if(data.containsKey(id)){
                Map<String,Object> map = (Map<String, Object>) data.get(id);
                List<String> role_name_list = map.get("role_name")!=null?(List<String>)map.get("role_name"):new ArrayList<String>();
                List<String> role_id_List = map.get("role_id")!=null?(List<String>)map.get("role_id"):new ArrayList<String>();
                List<String> role_chinese_name_List = map.get("role_chinese_name")!=null?(List<String>)map.get("role_chinese_name"):new ArrayList<String>();
                role_name_list.add(role_name);
                role_id_List.add(role_id);
                role_chinese_name_List.add(role_chinese_name);
            }else{
                if(role_id!=null){
                    List<String> role_name_List = new ArrayList<>();
                    List<String> role_id_List = new ArrayList<>();
                    List<String> role_chinese_name_List = new ArrayList<>();
                    role_id_List.add(role_id);
                    role_name_List.add(role_name);
                    role_chinese_name_List.add(role_chinese_name);
                    map1.put("role_name",role_name_List);
                    map1.put("role_id",role_id_List);
                    map1.put("role_chinese_name",role_chinese_name_List);
                }
                data.put(id,map1);
            }

        }
        userAll = new ArrayList<>();
        for (String s : data.keySet()) {
            userAll.add(data.get(s));
        }
        return userAll;
    }

    /**
     * 根据用户ID修改用户最后登录时间，IP
     */
    @Override
    public Integer updateIpTimeByid(User user) {
        return userMapper.updateIpTimeByid(user);
    }

    /**
     * 保存修改用户和角色信息
     * @param uservo
     * @return
     */
    @Override
    public ResData saveOrUpdateUserRole(UserVo uservo) {
        try {
            uservo.setPassword(bCryptPasswordEncoder.encode(uservo.getPassword()));
            User user = new User();
            VoPoConverter.copyProperties(uservo,user);
            boolean b = this.saveOrUpdate(user);
            user = userDao.queryUserByUsername(user.getUsername());
            if(b){
                List<Integer> roleids = uservo.getRoleids();
                Integer[] integers = roleids.toArray(new Integer[roleids.size()]);
                boolean type = userRoleService.updateUserRoleByUserRoleid(user.getId(),integers);
            }
            return new ResData(200,this.findAllUser(),"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResData(500,"","保存失败");
        }

    }

    @Override
    public ResData updateDvailableById(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.lambda().eq(User::getId,user.getId());
        User userOne = userMapper.selectOne(queryWrapper);
        userOne.setIsDvailable(user.getIsDvailable());
        int i = userMapper.updateById(userOne);
        if(i==1){
            return new ResData(200,userOne,"");
        }
        return new ResData(500,"","");
    }
}
