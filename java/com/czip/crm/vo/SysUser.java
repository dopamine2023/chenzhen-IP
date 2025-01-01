package com.czip.crm.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**   
 * @version: V1.0
 * @author: Na
 * 
 */
@Data
@ApiModel
public class SysUser extends Model<SysUser> {

	private static final long serialVersionUID = 1657642245137L;
	
	@TableId(value = "id", type = IdType.INPUT)
	@ApiModelProperty(name = "id" , value = "主键id")
	private Long id;
    
	@ApiModelProperty(name = "account" , value = "账号")
    @TableField(value = "account")
	private String account;
    
	@ApiModelProperty(name = "password" , value = "密码")
    @TableField(value = "password")
	private String password;
    
	@ApiModelProperty(name = "dept" , value = "所属部门")
    @TableField(value = "dept")
	private String dept;
    
	@ApiModelProperty(name = "phone" , value = "联系方式")
    @TableField(value = "phone")
	private String phone;
    
	@ApiModelProperty(name = "email" , value = "个人邮箱")
    @TableField(value = "email")
	private String email;
    
	@ApiModelProperty(name = "createBy" , value = "创建人")
    @TableField(value = "create_by")
	private Long createBy;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createDate" , value = "创建时间")
    @TableField(value = "create_date")
	private Date createDate;
    
	@ApiModelProperty(name = "updateBy" , value = "更新用户")
    @TableField(value = "update_by")
	private Long updateBy;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateDate" , value = "更新时间")
    @TableField(value = "update_date")
	private Date updateDate;
    
	@ApiModelProperty(name = "status" , value = "状态： 1 普通用户 2 超级管理员")
    @TableField(value = "status")
	private int status;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "lastDate" , value = "最后一次登录时间")
    @TableField(value = "last_date")
	private Date lastDate;

	@ApiModelProperty(name = "sysPower" , value = "权限")
	@TableField(exist = false)
	private List<SysPower> sysPower;
    

}
