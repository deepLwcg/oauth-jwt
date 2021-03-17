package cn.yajienet.oauthjwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-08 12:26
 */
@Slf4j
@Component
public class JwtUtils {

    @Value(value = "${spring.application.name}")
    private String appName;
    @Value(value = "${server.port}")
    private String port;
    @Value(value = "${jwt.secret:hlU5H0WEJV5SoxR01DzGLbG3zE2FXiDs95EH6WrkySK7Itggs0F2ClhILENl6+2NFeTDuqc2CUJUr9mG9tWa6Q==}")
    private String secret;

    @Resource
    private RedisUtils redisUtils;

    //有效期
    @Value("${jwt.expiration:604800}")
    private Long expiration;


    /**
     * 从数据中生成令牌
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Claims claims){
        Date expirationDate = new Date(System.currentTimeMillis()+expiration*1000);
        Key key =  new SecretKeySpec(Decoders.BASE64.decode(secret),SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(key).compact();
    }
    /**
     * 从令牌中获取数据声明
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        }catch (Exception e){
            claims = null;
            e.printStackTrace();
        }
        return claims;
    }
    /**
     * 将token存储到redis
     */
    public void setExpire(String key,String val,long time){
        redisUtils.setExpire(String.format("%s:%s:%s",appName,port,key),val,time);
    }

    /**
     * 将token存储到redis
     */
    public void setExpire(String key,String val){
        redisUtils.setExpire(String.format("%s:%s:%s",appName,port,key),val,expiration);
    }

    /**
     *移除
     */
    public void del(String authToken) {
        redisUtils.del(String.format("%s:%s:%s",appName,port,authToken));
    }

    /**
     * 判断是否有效
     * @param authToken String
     * @param userDetails UserDetails
     * @return boolean
     */
    public boolean validateToken(String authToken, UserDetails userDetails) {
        String username = redisUtils.get(String.format("%s:%s:%s",appName,port,authToken));
        if (null != username && userDetails.getUsername().equals(username)){
            return true;
        }
        return false;
    }



    /**
     * 生成令牌
     * @param userName 用户
     * @return 令牌
     */
    public String generateToken(String userName){
        Claims claims = new DefaultClaims();
        claims.setSubject(userName);
        claims.setExpiration(new Date());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     * @param authToken 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String authToken) {
        String username;
        try {
            Claims claims = getClaimsFromToken(authToken);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
            e.printStackTrace();
        }
        return username;
    }
    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.setExpiration(new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @param userName 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, String userName) {
        String username = getUsernameFromToken(token);
        return (username.equals(userName) && !isTokenExpired(token));
    }
}
