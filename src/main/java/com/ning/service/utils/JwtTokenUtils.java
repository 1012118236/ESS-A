package com.ning.service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/5/23 14:41
 */
public class JwtTokenUtils {
    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer";

    private static final String SECRET = "jwtsecretdemo";

    private static final String ISS = "echisan";

    //权限key
    private static final String PERMISSION_CLAIMS = "permission";

    //过期时间3600秒
    private static final long EXPIRATION = 3600L;
    //记住密码过期时间7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    /**
     * 创建token
     * @param username 用户名
     * @param permission 用户角色
     * @param isRememberMe 是否记住用户 true 记住
     * @return
     */
    public static String createToken(String username, List<GrantedAuthority> permission, boolean isRememberMe){
        long sxpiration = isRememberMe?EXPIRATION_REMEMBER:EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(PERMISSION_CLAIMS,permission);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+sxpiration*1000))
                .compact();
    }
    //从token中获取用户名
    public static String getUsername(String token){
        boolean expiration = isExpiration(token);//false没有过期
        if(expiration){
            return null;
        }else{
            return getTokenBody(token).getSubject();
        }

    }

    //是否过期
    public static boolean isExpiration(String token){
        boolean status = true;
        try {
            status = getTokenBody(token).getExpiration().before(new Date());
        } catch (Exception e) {
            status=true;
        }
        return  status;
    }

    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    // 获取用户角色
    public static Collection<? extends GrantedAuthority> getUserPermission(String token){
        List<LinkedHashMap> permissionList= ( List<LinkedHashMap>)getTokenBody(token).get(PERMISSION_CLAIMS);
        List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
        for (LinkedHashMap linkedHashMap : permissionList) {
            String authority = linkedHashMap.get("authority").toString();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            grantedAuthorities.add(grantedAuthority);
        }

        return grantedAuthorities;
    }
}
