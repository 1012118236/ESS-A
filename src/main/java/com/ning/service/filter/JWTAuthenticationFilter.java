package com.ning.service.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.service.entity.User;
import com.ning.service.utils.JwtTokenUtils;
import com.ning.service.vo.JwtUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenjiang
 * @Description:  用户认证
 * @Date: 2019/5/23 15:02
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            request.setAttribute("user",user);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证成功 生成token并返回
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        User user = jwtUser.getUser();
        RequestDispatcher dispatcher = null;
        if(user.getIsDvailable()!=0){
            dispatcher = request.getRequestDispatcher("/user/isDvailable");
            request.setAttribute("user",user);
        }else{
            //获取用户权限
            List<GrantedAuthority> authorities = jwtUser.getAuthorities();
            //通过用户和权限生成token
            String token = JwtTokenUtils.createToken(jwtUser.getUsername(),authorities ,false);
            // 返回创建成功的token
            // 但是这里创建的token只是单纯的token
            // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
            token = JwtTokenUtils.TOKEN_PREFIX + token;
            response.setHeader("token", token);
            request.setAttribute("token",token);
            request.setAttribute("code",200);
            request.setAttribute("user",user);
            dispatcher = request.getRequestDispatcher("/user/loginType");
        }
        dispatcher.forward(request, response);//执行转发
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        request.setAttribute("code",500);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/loginType");
        dispatcher.forward(request, response);//执行转发
    }



}
