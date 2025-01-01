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
public class OtherTrademark extends Model<OtherTrademark> {

	private static final long serialVersionUID = 1661621041473L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键id")
    @TableField(value = "id")
	private Integer id;
    
	@ApiModelProperty(name = "ordinary" , value = "商标普通注册")
    @TableField(value = "ordinary")
	private String ordinary;
    
	@ApiModelProperty(name = "reject" , value = "驳回复审")
    @TableField(value = "reject")
	private String reject;
    
	@ApiModelProperty(name = "dissent" , value = "异议答辩")
    @TableField(value = "dissent")
	private String dissent;
    
	@ApiModelProperty(name = "withdraw" , value = "撤三答辩")
    @TableField(value = "withdraw")
	private String withdraw;
    
	@ApiModelProperty(name = "extension" , value = "续展")
    @TableField(value = "extension")
	private String extension;
    
	@ApiModelProperty(name = "expand" , value = "宽展")
    @TableField(value = "expand")
	private String expand;
    
	@ApiModelProperty(name = "licenseFiling" , value = "许可备案")
    @TableField(value = "license_filing")
	private String licenseFiling;
    
	@ApiModelProperty(name = "patentChanger" , value = "商标变更")
    @TableField(value = "patent_changer")
	private String patentChanger;
    
	@ApiModelProperty(name = "apply" , value = "申请")
    @TableField(value = "apply")
	private String apply;
    
	@ApiModelProperty(name = "design" , value = "设计")
    @TableField(value = "design")
	private String design;
    
	@ApiModelProperty(name = "name" , value = "取名")
    @TableField(value = "name")
	private String name;
    
	@ApiModelProperty(name = "evaluate" , value = "评价")
    @TableField(value = "evaluate")
	private String evaluate;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "cerateDate" , value = "")
    @TableField(value = "cerate_date")
	private Date cerateDate;
    
	@ApiModelProperty(name = "updateBy" , value = "")
    @TableField(value = "update_by")
	private Long updateBy;
    

}
