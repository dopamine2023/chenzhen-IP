package com.czip.crm.vo;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class PatentManagement extends Model<PatentManagement> {

	private static final long serialVersionUID = 1657641985700L;

	@ApiModelProperty(name = "id" , value = "主键")
	@TableId(value = "id", type = IdType.AUTO)
	@ExcelIgnore
	private Long id;
    
	@ApiModelProperty(name = "number" , value = "专利号")
    @TableField(value = "number")
	@ExcelProperty(value = "专利号",index = 0)
	private String number;
    
	@ApiModelProperty(name = "name" , value = "专利名")
    @TableField(value = "name")
	@ExcelProperty(value = "专利名", index = 1)
	private String name;


    @TableField(value = "deadline")
	@ExcelProperty(value = "缴费截止日", index = 3)
	private String deadline;
    
	@ApiModelProperty(name = "money" , value = "价格")
    @TableField(value = "money")
	@ExcelProperty(value = "价格", index = 4)
	private String money;
    
	@ApiModelProperty(name = "holder" , value = "专利持有人")
    @TableField(value = "holder")
	@ExcelProperty(value = "专利持有人", index = 5)
	private String holder;
    
	@ApiModelProperty(name = "type" , value = "专利类型")
    @TableField(value = "type")
	@ExcelProperty(value = "专利类型")
	@ExcelIgnore
	private String type;
    
	@ApiModelProperty(name = "sellStatus" , value = "销售状态")
    @TableField(value = "sell_status")
	@ExcelProperty(value = "销售状态")
	@ExcelIgnore
	private String sellStatus;
    
	@ApiModelProperty(name = "status" , value = "专利状态")
    @TableField(value = "status")
	@ExcelProperty(value = "专利状态", index = 2)
	private String status;

	@ApiModelProperty(name = "holderType" , value = "专利权人类型")
	@TableField(value = "holder_type")
	@ExcelProperty(value = "专利权人类型", index = 8)
	private String holderType;

	@ApiModelProperty(name = "level" , value = "供应商等级")
	@TableField(value = "level")
	@ExcelProperty(value = "供应商等级", index = 7)
	private String level;

	@ApiModelProperty(name = "phone" , value = "联系方式")
	@TableField(value = "phone")
	@ExcelProperty(value = "联系方式", index = 6)
	private String phone;
    
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@JsonFormat(pattern="yyyy/MM/dd",timezone = "GMT+8")
	@ApiModelProperty(name = "crateTime" , value = "导入时间")
    @TableField(value = "create_time")
	@ExcelIgnore
	private Date createTime;
    
	@ApiModelProperty(name = "createBy" , value = "导入人")
    @TableField(value = "create_by")
	@ExcelIgnore
	private String createBy;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "更新时间")
    @TableField(value = "update_time")
	@ExcelIgnore
	private Date updateTime;
    
	@ApiModelProperty(name = "updateBy" , value = "更新人")
    @TableField(value = "update_by")
	@ExcelIgnore
	private String updateBy;

	@ApiModelProperty(name = "statusUpdateTime" , value = "最后一次更新状态时间")
	@TableField(value = "status_update_time")
	@ExcelIgnore
	private String statusUpdateTime;
}
