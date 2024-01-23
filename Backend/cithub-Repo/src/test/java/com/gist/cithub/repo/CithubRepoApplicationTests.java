package com.gist.cithub.repo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gist.cithub.repo.entity.ListEntity;
import com.gist.cithub.repo.service.ListService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest
class CithubRepoApplicationTests {
//    @Test
//    void testJwt() {
//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.HOUR, 1);
//        String toke = JWT.create().
//                withIssuer("auth0").
//                withClaim("userid", 1).
//                withClaim("username", "张三").
//                withExpiresAt(instance.getTime()).
//                sign(Algorithm.HMAC256("secrect111"));
//        System.out.println(toke);
//    }

//
//    @Test
//    void verifyJWT() {
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("secrect111")).build();
//        DecodedJWT decodedJWT = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY2NDI0NjYxOSwidXNlcmlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9.4A6mA2Ac9gNp4nP0wSjSp-QsctJEPdcEBBSXBXpn3Mc");
//        System.out.println(decodedJWT.getClaim("userid").asInt());
//        System.out.println(decodedJWT.getClaim("username").asString());
//        System.out.println(decodedJWT.getExpiresAt());
//    }

//
//    @Test
//    void fortest() {
//        ListEntity[] array = new ListEntity[3];
//        for (int i = 0; i < 3; i++) {
//            array[i].setProjects("1");
//            array[i].setTitle("1");
//            array[i].setAuthor("1");
//            array[i].setTag("1");
//        }
//        for(ListEntity item : array)
//        {
//            System.out.println(item);
//        }
//    }
}
