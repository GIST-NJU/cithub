package com.gist.cithub.backend.Repo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

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
