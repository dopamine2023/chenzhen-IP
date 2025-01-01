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
public class OfficialMoney extends Model<OfficialMoney> {

	private static final long serialVersionUID = 1662237486856L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键id")
    @TableField(value = "id")
	private Integer id;
    
	@ApiModelProperty(name = "invnetSignleY" , value = "官费：发明专利单个有费减")
    @TableField(value = "invnet_signle_y")
	private String invnetSignleY;
    
	@ApiModelProperty(name = "inventMultipleY" , value = "发明专利多人有费减")
    @TableField(value = "invent_multiple_y")
	private String inventMultipleY;
    
	@ApiModelProperty(name = "inventN" , value = "发明专利无费减")
    @TableField(value = "invent_n")
	private String inventN;
    
	@ApiModelProperty(name = "practicalSignleY" , value = "实用新型单人申请有费减")
    @TableField(value = "practical_signle_y")
	private String practicalSignleY;
    
	@ApiModelProperty(name = "practicalMultipeY" , value = "实用多人有费减")
    @TableField(value = "practical_multipe_y")
	private String practicalMultipeY;
    
	@ApiModelProperty(name = "practicalNo" , value = "实用无费减")
    @TableField(value = "practical_no")
	private String practicalNo;
    
	@ApiModelProperty(name = "updateBy" , value = "创建人")
    @TableField(value = "update_by")
	private Long updateBy;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "更新时间")
    @TableField(value = "update_time")
	private Date updateTime;
    

}
