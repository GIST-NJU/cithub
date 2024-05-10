package com.gist.cithub.backend.User.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 16:09:03
 */
@Data
@TableName("users")
public class UsersEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer userid;
    /**
     *
     */
    @TableField("account")
    private String account;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private String useremail;
    private String name;
    private String institution;

    private String usertype;

    private String usertoken;
    private String country;


}
