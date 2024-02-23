package com.gist.cithub.backend.Repo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("repository_scholar")
public class authorAndscholarEntity implements Serializable{
    private static final long serialVersionUID = 1L;


    @TableId
    private Integer id;

    @TableField(value = "name")
    private String name;


    private String institution;
    private String category;
    private String country;
    private String email;
    private String homepage;
}
