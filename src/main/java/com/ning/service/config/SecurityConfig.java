package com.ning.service.config;

import com.ning.service.filter.JWTAuthenticationEntryPoint;
import com.ning.service.filter.JWTAuthenticationFilter;
import com.ning.service.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author shenjiang
 * @Description: spring security 配置类
 * @Date: 2019/5/23 15:08
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入自己的接口实现类
     */

    private final UserDetailsService userDetailsService;

    /**
     * 密码加密
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //.addFilterAfter(dynamicallyUrlInterceptor(),FilterSecurityInterceptor.class)
                .authorizeRequests()
                .antMatchers("/image/**").permitAll() //静态资源访问无需认证
                .antMatchers("/kafkaDemo").permitAll() //静态资源访问无需认证
                .antMatchers("/websocket/**").permitAll()
                .antMatchers("/user/loginLose").permitAll()
                .antMatchers("/tasks/**").permitAll()
                .antMatchers("/employee/**").permitAll()
                //只有USER角色才能访问
                //.antMatchers("/tasks/**").hasRole("USER")
                //.antMatchers("/menus/**").hasRole("HAHAS")
                //.antMatchers(HttpMethod.DELETE,"/tasks/**").hasAnyRole("ADMIN") //admin开头的请求，需要admin权限
                //.antMatchers(HttpMethod.DELETE,"/tasks/**").hasRole("ADMIN")
                //.antMatchers("/article/**").hasRole("USER") //需登陆才能访问的url
                // 测试用资源，需要验证了的用户才能访问
                //.antMatchers("/tasks/**").authenticated()
                // 需要角色为ADMIN才能删除该资源
                //.antMatchers(HttpMethod.DELETE, "/tasks/**").hasAuthority("role_haha")
                // 其他都放行了
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());
    }

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }


    //    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }

//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterAfter(dynamicallyUrlInterceptor(),FilterSecurityInterceptor.class)
//                .authorizeRequests()
//                .antMatchers("/menus/queryMenus").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/user/login")
//                .permitAll()
//                .and()
//                .logout().permitAll();
//    }

//    @Bean
//    public DynamicallyUrlInterceptor dynamicallyUrlInterceptor(){
//        DynamicallyUrlInterceptor interceptor = new DynamicallyUrlInterceptor();
//        interceptor.setSecurityMetadataSource(new MyFilterSecurityMetadataSource());
//
//        //配置RoleVoter决策
//        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
//        decisionVoters.add(new RoleVoter());
//        //设置认证决策管理器
//        interceptor.setAccessDecisionManager(new DynamicallyUrlAccessDecisionManager(decisionVoters));
//        return interceptor;
//    }
}
