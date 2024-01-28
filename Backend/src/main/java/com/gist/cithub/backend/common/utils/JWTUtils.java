package com.gist.cithub.backend.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

import static com.gist.cithub.backend.common.constant.JwtConstant.JWT_SECERT;
import static com.gist.cithub.backend.common.constant.JwtConstant.JWT_TTL;

/**
 * @author JianTu Xiang
 * @Email 1223564154@qq.com
 * @Group Gist https://gist.nju.edu.cn/ from Department of Computer Science and Technology of NanJing University
 * @date 2022/9/27
 */


public class JWTUtils {

    /*
     * 生成token: header.payload.signature
     * */

    public static String createToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();

        instance.add(Calendar.HOUR, JWT_TTL);
//        instance.add(Calendar.SECOND, JWT_TTL);
        JWTCreator.Builder builder = JWT.create();
//        根据收的key value 生成token
        map.forEach((key, value) ->
        {
            builder.withClaim(key, value);

        });
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(JWT_SECERT));

        return token;
    }

    /*验证并获取Token,若验证不通过则会抛出异常*/
    public static DecodedJWT verifyAndgetToken(String token) {
//
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JWT_SECERT)).build().verify(token);
        return decodedJWT;

    }



}
