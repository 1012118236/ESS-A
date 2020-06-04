package com.ning.service.controller;


import com.ning.service.entity.Status;
import com.ning.service.entity.User;
import com.ning.service.service.IUserService;
import com.ning.service.utils.DateUtils;
import com.ning.service.utils.RedisUtil;
import com.ning.service.utils.RequestUtils;
import com.ning.service.utils.UserUtils;
import com.ning.service.vo.ResData;
import com.ning.service.vo.UserVo;
import com.ning.service.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/saveOrUpdateUser")
    public ResData registerUser(@RequestBody UserVo user){ // 记得注册的时候把密码加密一下
        return userService.saveOrUpdateUserRole(user);

    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public ResData logout(){
        String username = UserUtils.getUsername();
        //redisUtil.del(username);
        return new ResData(200,"","登出成功");
    }

    /**
     * 更改用户状态
     * @param user
     * @return
     */
    @PostMapping("/updateDvailableById")
    public ResData updateDvailableById(@RequestBody User user){
        return userService.updateDvailableById(user);
    }

    /**
     * 用户认证
     * @return
     */
    @RequestMapping(value = "/loginType",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String,Object> loginType(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> data = new HashMap<>();
        Integer code = (Integer)request.getAttribute("code");
        if(code==200){
            User user = (User)request.getAttribute("user");

            String last_ip = RequestUtils.getIpAddress(request);
            Timestamp last_time = DateUtils.getTimestampByDate(new Date());
            user.setLastIp(last_ip);
            user.setLastTime(last_time);
            Integer integer = userService.updateIpTimeByid(user);
            data.put("code",200);
            String token = (String)request.getAttribute("token");
            data.put("userInfo",user);
            data.put("token",token);
            data.put("message","登陆成功");
            //redisUtil.set(user.getUsername(),token,1800l);
        }else{
            data.put("code",Status._500);
            data.put("message","用户名或密码错误!");
        }
        return data;
    }

    /**
     * 用户异常
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/isDvailable")
    @ResponseBody
    public ResData isDvailable(HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getAttribute("user");
        Integer isDvailable = user.getIsDvailable();
        if(isDvailable==1){
            return new ResData(Status._500,null,"账号异常，请联系管理员!");
        }else{
            return null;
        }
    }

    /**
     * 登陆失效 token过期
     * @return
     */
    @RequestMapping(value = "/loginLose")
    @ResponseBody
    public ResData loginLose(){
        return new ResData(Status._599,null,"登陆失效或无权限，请重新登陆");
    }


    /**
     * 用户重复登陆
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/errorUser")
    @ResponseBody
    public ResData errorUser(HttpServletRequest request,HttpServletResponse response){
        return new ResData(Status._501,null,"当前用户已在其它设备登陆!");
    }

    /**
     * 查询用户
     * @return
     */
    @PostMapping(value = "findAllUser")
    @ResponseBody
    public Object findAllUser(){
        WebSocket.sendMessageList("哈哈");
        List<Map<String,Object>> allUser = userService.findAllUser();
        return new ResData(200,allUser,"");

    }
}

