package com.gist.cithub.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

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
    @TableField("username")
    private String username;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private String useremail;

    private String usertype;

    private String usertoken;


}
