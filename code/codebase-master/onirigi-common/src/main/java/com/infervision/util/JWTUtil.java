package com.infervision.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName JWTUtil
 * @Description TODO
 * @Author frq
 * @Date 2019/3/7 22:45
 * @Version 1.0 jwt工具类
 */
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    // 过期时间5分钟
    private static final long EXPIRE_TIME = 12* 5 * 60 * 1000;

    /**
     * @Author fruiqi
     * @Description  创建一个签名
     * @Date 1:57 2019/3/9
     * @Param [username, secret]
     * @return java.lang.String
     **/
    public static String sign(Map<String,Object> userToken, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        // 附带username信息
        return JWT.create()
                .withClaim("userId",userToken.get("userId").toString())
                .withClaim("userName", userToken.get("userName").toString())
                .withClaim("timestamp",Long.parseLong(userToken.get("timestamp").toString()))
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * @Author fruiqi
     * @Description  生成签名
     * @Date 23:23 2019/3/9
     * @Param [userId, userName, timestamp, secret]
     * @return java.lang.String
     **/
    public static String sign(String userId,String userName,Long timestamp, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        // 附带username信息
        return JWT.create()
                .withClaim("userId",userId)
                .withClaim("userName", userName)
                .withClaim("timestamp",timestamp)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, Map<String,Object> userToken, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("userId",userToken.get("userId").toString())
                .withClaim("userName", userToken.get("userName").toString())
                .withClaim("timestamp",Long.parseLong(userToken.get("timestamp").toString()))
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userName").asString();
    }

    /**
     * @Author fruiqi
     * @Description 得到时间戳
     * @Date 2:43 2019/3/9
     * @Param [token]
     * @return java.lang.String
     **/    
    public static Long getTimeStamp(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("timestamp").asLong();
    }

}
