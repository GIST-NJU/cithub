package com.gist.cithub.user.feign;

import com.gist.cithub.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("cithub-guitools")
public interface GuitoolsFeignService {
/*该接口想要远程调用guitools模块的服务
* 1、在该接口上写上注解@FeignClient,告诉springcloud该接口是Feign客户端。（Users调guitools，Users相当于客户端，guitools相当于服务器）
* 2、@FeignClient的value属性写上要调用的模块在注册中心中的名字，这里guitools在注册中心中的名字是cithub-guitools
* 3、cithub-guitools模块有很多接口，要调用哪一个呢？写上完整签名就行，完整签名=注解+函数声明。
* */
    //完整签名如下：注意@RequestMapping的value属性要写全路径
    @RequestMapping("/guitools/projects/getUserProjects")
    public R getUserProjects();

}
