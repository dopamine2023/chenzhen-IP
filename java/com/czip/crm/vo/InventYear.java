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
public class InventYear extends Model<InventYear> {

	private static final long serialVersionUID = 1662237329645L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键id")
    @TableField(value = "id")
	private Integer id;
    
	@ApiModelProperty(name = "year" , value = "年份")
    @TableField(value = "year")
	private String year;
    
	@ApiModelProperty(name = "moneyAll" , value = "年费全额")
    @TableField(value = "money_all")
	private String moneyAll;
    
	@ApiModelProperty(name = "moneyEight" , value = "年费减百分之70")
    @TableField(value = "money_eight")
	private String moneyEight;
    
	@ApiModelProperty(name = "moneySeven" , value = "年费减70%")
    @TableField(value = "money_seven")
	private String moneySeven;
    
	@ApiModelProperty(name = "updateBy" , value = "创建人")
    @TableField(value = "update_by")
	private Long updateBy;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "创建时间")
    @TableField(value = "update_time")
	private Date updateTime;
    

}
