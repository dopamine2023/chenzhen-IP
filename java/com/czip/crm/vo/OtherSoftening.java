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
public class OtherSoftening extends Model<OtherSoftening> {

	private static final long serialVersionUID = 1661621148842L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键")
    @TableField(value = "id")
	private Integer id;
    
	@ApiModelProperty(name = "name" , value = "完成工作日")
    @TableField(value = "name")
	private String name;
    
	@ApiModelProperty(name = "existence" , value = "有材料")
    @TableField(value = "existence")
	private String existence;
    
	@ApiModelProperty(name = "nonExistent" , value = "无材料")
    @TableField(value = "non_existent")
	private String nonExistent;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "oprationTime" , value = "操作时间")
    @TableField(value = "opration_time")
	private Date oprationTime;
    
	@ApiModelProperty(name = "oprationBy" , value = "操作人")
    @TableField(value = "opration_by")
	private Long oprationBy;

}
