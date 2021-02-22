package com.ll.test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author lihaoxuan
 * @date 2020/12/25 15:45
 */
public class JwtTest {
    public static void main(String[] args) {
        String token = Jwts.builder()
                .setSubject("hx")
                //自定义属性 放入用户拥有请求权限
                .claim("authorities","admin")
                // 设置失效时间为1分钟
                .setExpiration(new Date(System.currentTimeMillis()+1000*60))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, "java")
                .compact();
        System.out.println(token);
    }
}
