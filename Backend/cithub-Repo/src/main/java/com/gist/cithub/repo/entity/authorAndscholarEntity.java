package com.gist.cithub.repo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName("authorAndscholar")
public class authorAndscholarEntity implements Serializable{
    private static final long serialVersionUID = 1L;


    @TableId
    private Integer id;

    private String authorname;
    private String projects;
    private String institution;
    private String category;
    private String country;
    private String email;
    private String homepage;
}
