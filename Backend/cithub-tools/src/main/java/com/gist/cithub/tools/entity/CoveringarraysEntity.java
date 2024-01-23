package com.gist.cithub.tools.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * 
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
@Data
@TableName("coveringarrays")
public class CoveringarraysEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer coveringarrayid;
	/**
	 * 
	 */
	private String coveringarrayname;
	/**
	 * 
	 */
	private String coveringarraydescriptions;
	/**
	 * 
	 */
	private Integer modelid;

}
