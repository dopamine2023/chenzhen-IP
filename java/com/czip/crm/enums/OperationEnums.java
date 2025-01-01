package com.czip.crm.enums;

public enum  OperationEnums {

    SELL_STATUS(1005,"销售状态"),
    LEVEL(1006,"供应商等级");

    private Integer code;
    private String name;

     OperationEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
