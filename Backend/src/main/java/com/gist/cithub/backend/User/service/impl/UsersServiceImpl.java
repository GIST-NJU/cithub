package com.gist.cithub.backend.User.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.Query;
import com.gist.cithub.backend.User.dao.UsersDao;
import com.gist.cithub.backend.User.entity.UsersEntity;
import com.gist.cithub.backend.User.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


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
        usersEntity.setAccount(userinfo.get("account").toString());
        usersEntity.setName(userinfo.get("name").toString());
        usersEntity.setInstitution(userinfo.get("institution").toString());
        usersEntity.setPassword(userinfo.get("password").toString());
        usersEntity.setUseremail(userinfo.get("email").toString());
        usersEntity.setCountry(userinfo.get("country").toString());
        if(usersdao.insert(usersEntity)!=0)
        {
            return true;
        }
        else
            return false;

    }

}