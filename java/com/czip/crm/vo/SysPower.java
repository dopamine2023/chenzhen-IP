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

/**   
 * @version: V1.0
 * @author: Na
 * 
 */
@Data
@ApiModel
public class SysPower extends Model<SysPower> {

	private static final long serialVersionUID = 1658645880510L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "")
	private Integer id;
    
	@ApiModelProperty(name = "userId" , value = "用户id")
    @TableField(value = "user_id")
	private Long userId;
    
	@ApiModelProperty(name = "oprationId" , value = "权限id")
    @TableField(value = "opration_id")
	private Integer oprationId;
    
	@ApiModelProperty(name = "status" , value = "权限：0 不可操控 1 可操控")
    @TableField(value = "status")
	private Integer status;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间")
    @TableField(value = "create_time")
	private Date createTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "更新时间")
    @TableField(value = "update_time")
	private Date updateTime;
    
	@ApiModelProperty(name = "createBy" , value = "创建人")
    @TableField(value = "create_by")
	private Long createBy;
    
	@ApiModelProperty(name = "updateBy" , value = "更新人")
    @TableField(value = "update_by")
	private Long updateBy;
    

}
