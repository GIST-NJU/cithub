package com.gist.cithub.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gist.cithub.common.utils.JWTUtils;
import com.gist.cithub.user.entity.UsersEntity;
import com.gist.cithub.user.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CithubUserApplicationTests {
//    @Autowired
//    UsersService usersService;
//
//    @Test
//    void contextLoads() {
//        UsersEntity usersEntity = new UsersEntity();
//        usersEntity.setUserid(112);
//        usersEntity.setUseremail("112");
//        usersEntity.setUsername("xjt");
//        usersEntity.setPassword("qweasdas");
//        usersService.save(usersEntity);
//
//    }
//
//    @Test
//    void Tokentest() {
//        try {
//
//            Map<String,String> testmap=new HashMap<>();
//            testmap.put("username","testuser");
//            testmap.put("userID","testID");
//            testmap.put("userEmail","testEmail");
//            String token=JWTUtils.createToken(testmap);
////            System.out.println(token);
////            DecodedJWT verifyResult=JWTUtils.verifyAndgetToken(token);
////        测试token抛出异常
////        DecodedJWT verifyResult=JWTUtils.verifyAndgetToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJ0ZXN0RW1haWwiLCJleHAiOjE2NzYzNTk5NDUsInVzZXJJRCI6InRlc3RJRCIsInVzZXJuYW1lIjoidGVzdHVzZXIifQ.2HK9lY4DNgKENru-fmJ56q-gTNrL-16oNFYXcme4Dfk");
//        DecodedJWT verifyResult=JWTUtils.verifyAndgetToken("iOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJ0ZXN0RW1haWwiLCJleHAiOjE2NzYzNTk5NDUsInVzZXJJRCI6InRlc3RJRCIsInVzZXJuYW1lIjoidGVzdHVzZXIifQ.2HK9lY4DNgKENru-fmJ56q-gTNrL-16oNFYXcme4Dfk");
//            System.out.println(verifyResult.getClaim("username"));
//            System.out.println(verifyResult.getClaim("userID"));
//            System.out.println(verifyResult.getClaim("userEmail"));
//        } catch ( Exception e) {
//            System.out.println("异常是"+e);
//        }
//
//
//    }

}
