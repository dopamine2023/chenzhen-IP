package com.czip.crm.query;


import com.czip.crm.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**   
 * @version: V1.0
 * @author: Na
 * 
 */
@Data
@ApiModel
public class PatentManagementQuery extends BaseQuery {

	@ApiModelProperty(name = "keyWord" , value = "关键词")
	private String keyWord;

	@ApiModelProperty(name = "name" , value = "关键词所属类型")
	private String name;

	@ApiModelProperty(name = "type" , value = "专利类型")
	private String type;
    
	@ApiModelProperty(name = "sellStatus" , value = "销售状态")
	private String sellStatus;
    
	@ApiModelProperty(name = "status" , value = "专利状态")
	private String status;

	@ApiModelProperty(name = "holderType" , value = "专利人类型")
	private String holderType;

	@ApiModelProperty(name = "level" , value = "专利来源")
	private String level;

	@ApiModelProperty(name = "dateName" , value = "时间范围")
	private String dateName;

	@ApiModelProperty(name = "only" , value = "去重")
	private String only;


	
}
