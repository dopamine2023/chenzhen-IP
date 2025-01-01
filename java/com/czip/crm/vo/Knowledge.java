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
public class Knowledge extends Model<Knowledge> {

	private static final long serialVersionUID = 1662237454991L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键id")
    @TableField(value = "id")
	private Integer id;
    
	@ApiModelProperty(name = "inventN" , value = "发明专利风险否")
    @TableField(value = "invent_n")
	private String inventN;
    
	@ApiModelProperty(name = "inventY" , value = "发明专利风险驳回劝退")
    @TableField(value = "invent_y")
	private String inventY;
    
	@ApiModelProperty(name = "practicalN" , value = "实用新型风险否")
    @TableField(value = "practical_n")
	private String practicalN;
    
	@ApiModelProperty(name = "practicalY" , value = "实用新型风险驳回劝退")
    @TableField(value = "practical_y")
	private String practicalY;
    
	@ApiModelProperty(name = "desugnN" , value = "发明专利风险否")
    @TableField(value = "desugn_n")
	private String desugnN;
    
	@ApiModelProperty(name = "desugnY" , value = "发明专利风险驳回全退")
    @TableField(value = "desugn_y")
	private String desugnY;
    
	@ApiModelProperty(name = "first" , value = "优先审查")
    @TableField(value = "first")
	private String first;
    
	@ApiModelProperty(name = "urgent" , value = "预审加急")
    @TableField(value = "urgent")
	private String urgent;
    
	@ApiModelProperty(name = "updateBy" , value = "修改人")
    @TableField(value = "update_by")
	private Long updateBy;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "修改时间")
    @TableField(value = "update_time")
	private Date updateTime;
    

}
