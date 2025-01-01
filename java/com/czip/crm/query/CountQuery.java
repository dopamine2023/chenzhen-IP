package com.czip.crm.query;

import com.czip.crm.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CountQuery extends BaseQuery {

    @ApiModelProperty("类型名称")
    private String name;
    @ApiModelProperty("总数/总计成交量")
    private Integer count;
    @ApiModelProperty("未下证数/今日成交量")
    private Integer number;

}
