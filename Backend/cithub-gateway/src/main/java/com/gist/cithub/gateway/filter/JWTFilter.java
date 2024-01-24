//package com.gist.cithub.gateway.filter;
//
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import static com.gist.cithub.common.utils.JWTUtils.verifyAndgetToken;
//
//@Component
//public class JWTFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("进入Gateway网关全局过滤器！");
//        String token = exchange.getRequest().getHeaders().getFirst("token");
//        String userUrl = exchange.getRequest().getURI().toString();
//        ServerHttpResponse response = exchange.getResponse();
//        System.out.println("token是" + token);
////        进入user模块不需要token
//        if (userUrl.contains("api/user")) {
////            进入USER模块
//            System.out.println("进入user模块");
//            return chain.filter(exchange);
//        } else if (token == null) {
//            System.out.println("没有token，跳转到user模块！");
////           String redirectUrl = "http://localhost:5173/";
////           response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
//            response.setStatusCode(HttpStatus.NO_CONTENT);
//            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//            return response.setComplete();
//
//        }
////        有token 判断token是否正确/过期
//        else if (token != null) {
//            try {
////                token有效，则放行
//
//                verifyAndgetToken(token);
//            } catch (Exception e) {
////                token无效，返回前端
//                e.printStackTrace();
//                System.out.println("token无效！");
//                response.setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
//                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//                return response.setComplete();
//            }
//        }
//        System.out.println("token有效，放行！");
//        return chain.filter(exchange);
//
//
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
