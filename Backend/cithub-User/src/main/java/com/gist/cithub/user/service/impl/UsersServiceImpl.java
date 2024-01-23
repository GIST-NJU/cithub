package com.gist.cithub.user.service.impl;

import com.gist.cithub.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.common.utils.Query;

import com.gist.cithub.user.dao.UsersDao;
import com.gist.cithub.user.entity.UsersEntity;
import com.gist.cithub.user.service.UsersService;

import javax.annotation.Resource;


@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Autowired
    private  UsersDao usersdao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsersEntity> page = this.page(
                new Query<UsersEntity>().getPage(params),
                new QueryWrapper<UsersEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Boolean registerUser(Map<String, Object> userinfo) {
        UsersEntity usersEntity=new UsersEntity();
        usersEntity.setUsername(userinfo.get("username").toString());
        usersEntity.setPassword(userinfo.get("password").toString());
        usersEntity.setUseremail(userinfo.get("email").toString());
        if(usersdao.insert(usersEntity)!=0)
        {
            return true;
        }
        else
            return false;

    }

}