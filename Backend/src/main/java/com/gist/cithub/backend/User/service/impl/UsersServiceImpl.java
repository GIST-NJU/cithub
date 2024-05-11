package com.gist.cithub.backend.User.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.Tools.service.ModelsService;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.Query;
import com.gist.cithub.backend.User.dao.UsersDao;
import com.gist.cithub.backend.User.entity.UsersEntity;
import com.gist.cithub.backend.User.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Autowired
    private  UsersDao usersdao;

    @Autowired
    private ModelsService modelsService;

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
            QueryWrapper<UsersEntity> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("account",userinfo.get("account").toString());

            //        用户注册时，给该用户一个Demo Model

            ModelsEntity modelsEntity=new ModelsEntity();
            modelsEntity.setUserid(usersdao.selectOne(queryWrapper).getUserid());
            modelsEntity.setModelname("DemoModel Phone");
            modelsEntity.setModeldescriptions("This is a Demo Model named `Phone`.");
            modelsEntity.setModeltype("Manual");
            modelsEntity.setParamsvalues("[{\"Parameter\":\"emailViewer\",\"Value\":\"TRUE,FALSE\"},{\"Parameter\":\"textLines\",\"Value\":\"25,26,27,28,29,30\"},{\"Parameter\":\"display\",\"Value\":\"16MC,8MC,BW\"}]");
            modelsEntity.setCons("[{\"Constraint\":[{\"Parameter\":\"emailViewer\",\"Value\":\"TRUE\"},{\"Parameter\":\"textLines\",\"Value\":\"25\"}]},{\"Constraint\":[{\"Parameter\":\"emailViewer\",\"Value\":\"TRUE\"},{\"Parameter\":\"textLines\",\"Value\":\"26\"}]},{\"Constraint\":[{\"Parameter\":\"emailViewer\",\"Value\":\"TRUE\"},{\"Parameter\":\"textLines\",\"Value\":\"27\"}]},{\"Constraint\":[{\"Parameter\":\"emailViewer\",\"Value\":\"TRUE\"},{\"Parameter\":\"textLines\",\"Value\":\"28\"}]}]");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime createTime = LocalDateTime.now();
            LocalDateTime lastUpdateTime = LocalDateTime.now();

            String createTimeString = createTime.format(formatter);
            String lastUpdateTimeString = lastUpdateTime.format(formatter);

            modelsEntity.setCreatedtime(Timestamp.valueOf(createTimeString));
            modelsEntity.setLastupdatedtime(Timestamp.valueOf(lastUpdateTimeString));
            modelsService.save(modelsEntity);

            return true;
        }
        else
            return false;

    }

}