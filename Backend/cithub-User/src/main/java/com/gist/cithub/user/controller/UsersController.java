package com.gist.cithub.user.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gist.cithub.common.utils.JWTUtils;
import com.gist.cithub.user.feign.GuitoolsFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gist.cithub.user.entity.UsersEntity;
import com.gist.cithub.user.service.UsersService;
import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.common.utils.R;

import static com.gist.cithub.common.utils.JWTUtils.verifyAndgetToken;


/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 16:09:03
 */
@RestController
@RequestMapping("user/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    //    注入远程调用接口
    @Autowired
    private GuitoolsFeignService guitoolsFeignService;

    //    远程调用测试
    @RequestMapping("/testRPC")
    public R TestRPC() {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername("xjt");
//        已注入远程调用接口，直接调用它里面的方法！
        R result = guitoolsFeignService.getUserProjects();
//        远程调用返回的结果result里有key为projects的对象，这里可以用result.get("projects")获取该对象
//        最终响应的结果里有key为user的对象，还有key为Projects的对象
        return R.ok().put("user", usersEntity).put("projects", result.get("projects")).put("coveringArrayUser", result.get("coveringArray")).put("nullkey", result.get("nullkey"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = usersService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userid}")
    public R info(@PathVariable("userid") Integer userid) {
        UsersEntity users = usersService.getById(userid);

        return R.ok().put("users", users);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UsersEntity users) {
        usersService.save(users);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UsersEntity users) {
        usersService.updateById(users);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] userids) {
        usersService.removeByIds(Arrays.asList(userids));

        return R.ok();
    }

    @RequestMapping("/getUserInfoByToken")
    public R getUserInfoByToken(@RequestParam String userToken) {
//        System.out.println(userToken);
        QueryWrapper<UsersEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("usertoken", userToken);
        UsersEntity usersEntity = usersService.getOne(queryWrapper);
        return R.ok().put("userid", usersEntity.getUserid()).put("username", usersEntity.getUsername()).put("useremail", usersEntity.getUseremail()).put("usertype", usersEntity.getUsertype());
    }

    @RequestMapping("/RegisterUser")
    public R registerUser(@RequestBody Map<String, Object> frontRequest) {
        Boolean flag = usersService.registerUser(frontRequest);
//        System.out.println(frontRequest);
//        frontRequest.remove("password");
//        frontRequest.remove("passwordagain");
//        String username=frontRequest.get("username").toString();
//        String email=frontRequest.get("email").toString();
//        Map<String,String> mapNew=new HashMap<>();
//        mapNew.put("username",username);
//        mapNew.put("email",email);
//        String token=JWTUtils.createToken(mapNew);
//        System.out.println("token里的username是:"+verifyAndgetToken(token).getClaim("username"));
//        System.out.println("token里的email是:"+verifyAndgetToken(token).getClaim("email"));
        if (flag) {
            System.out.println("注册成功！");
            return R.ok().put("res", frontRequest);
        } else return R.ok().put("res", "注册失败!");
    }

    @RequestMapping("/CheckUnique")
    public R checkUnique(@RequestBody Map<String, String> username) {
//        System.out.println("接收到的用户名是:"+username.get("username"));
        QueryWrapper<UsersEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username.get("username"));
        if (usersService.getOne(queryWrapper) == null) {
            return R.ok().put("checkresult", "username is available");
        } else {
            return R.ok().put("checkresult", "username is unavailable");
        }

    }

    @RequestMapping("/login")
    public R userlogin(@RequestBody Map<String, Object> userLoginInfo) {
        //先验证密码是否正确
//        System.out.println(userLoginInfo.toString());
        String username = userLoginInfo.get("username").toString();
        String password = userLoginInfo.get("password").toString();
        QueryWrapper<UsersEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UsersEntity usersEntityChecker = usersService.getOne(queryWrapper);
        if (usersEntityChecker.getPassword().equals(password)) {
            System.out.println("密码正确");
//        密码正确，再去检查token是否有效
            try {
//                验证token是否有错误

                JWTUtils.verifyAndgetToken(usersEntityChecker.getUsertoken());
//                若无异常抛出，则说明token无错误
//                返回登录成功的status与token
                System.out.println("token正确，登录");
                return R.ok().put("status", "LoginSuccess!").put("token", usersEntityChecker.getUsertoken());

            } catch (Exception e) {
                System.out.println("token错误，更新token");
//                token错误,更新token
                Map<String, String> mapforToken = new HashMap<>();
                mapforToken.put("username", usersEntityChecker.getUsername());
                mapforToken.put("userid", usersEntityChecker.getUserid().toString());
                mapforToken.put("email", usersEntityChecker.getUseremail());
                mapforToken.put("usertype", usersEntityChecker.getUsertype());

                UpdateWrapper<UsersEntity> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("userid", usersEntityChecker.getUserid());
                String token = JWTUtils.createToken(mapforToken);
                updateWrapper.set("usertoken", token);
                usersService.update(updateWrapper);
//              然后返回登录成功的status 与 新token
                return R.ok().put("status", "LoginSuccess!").put("token", token);
            }

        }
//        密码错误 返回登录失败
        else return R.ok().put("status", "LoginFail!");
    }

    @RequestMapping("/VerifyToken")
    public R VerifyToken(@RequestParam String tokenForVerify) {
        try {
            JWTUtils.verifyAndgetToken(tokenForVerify);
            return R.ok().put("verifstatus","success");
        } catch (Exception e) {
            return R.ok().put("verifstatus","failed");
        }
    }

    @RequestMapping("/testNewCithub")
    public R testNewCithub() {
     return R.ok().put("success!","success");
    }

}
