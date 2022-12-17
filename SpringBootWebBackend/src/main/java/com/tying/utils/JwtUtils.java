package com.tying.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 工具类
 * @author Tying
 * @version 1.0
 */
public class JwtUtils {

    /**
     * token 有效时间
     */
    public static Long JWT_TTL = 60 * 60 * 1000L;
    /**
     * 设置密钥明文
     */
    public static final String JWT_KEY = "ying";

    /**
     * 生成 jwt
     * @param subject：token 中存放的数据（可以是 json 格式）
     * @return
     */
    public static String createJWT(String subject) {

        JwtBuilder builder = getJwtBuilder(getUUID(), subject, null);
        return builder.compact();
    }

    /**
     *
     * @param subject：token 中存放的数据（可以是 json 格式）
     * @param ttlMillis：超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {

        JwtBuilder builder = getJwtBuilder(getUUID(), subject, ttlMillis);
        return builder.compact();
    }

    /**
     *
     * @param uuid
     * @param subject:就是 jwt 中需要携带的数据，比如用户id
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String uuid, String subject, Long ttlMillis) {

        JwtBuilder builder = getJwtBuilder(uuid, subject, ttlMillis);
        return builder.compact();
    }

    /**
     * 返回 JwtBuilder 对象
     * @param uuid
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static JwtBuilder getJwtBuilder(String uuid, String subject, Long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis != null) {
            JWT_TTL = ttlMillis;
        }
        long expiredMillis = nowMillis + JWT_TTL;
        Date expiredDate = new Date(expiredMillis);
        SecretKey secretKey = generateKey();

        JwtBuilder builder = Jwts.builder()
                // 唯一的ID
                .setId(uuid)
                // 主题，可以是 JSON 数据
                .setSubject(subject)
                // 签发者
                .setIssuer("tying")
                // 签发时间
                .setIssuedAt(now)
                // 使用 HS256 对称加密算法签名，第二个参数为秘钥
                .signWith(signatureAlgorithm, secretKey)
                // 设置过期时间
                .setExpiration(expiredDate);

        return builder;
    }

    /**
     * 生成加密后的秘钥
     * @return
     */
    public static SecretKey generateKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtils.JWT_KEY);
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 返回 UUID
     * @return
     */
    public static String getUUID() {

        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    /**
     * 解析 jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{

        SecretKey secretKey = generateKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}