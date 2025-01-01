package com.czip.crm.enums;

/**
 * 时间枚举
 */
public enum  DateEnums {
    seven("7天", 0, 7),
    fifteen("15天", 0, 15),
    one_month("一个月", 1, 1),
    three_month("三个月", 1, 3),
    six_moth("六个月", 1, 6),
    ont_year("一年", 1, 1);
    private String type;
    private Integer status;
    private Integer date;
    DateEnums(String type,Integer status, Integer date) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getDate() {
        return date;
    }
}
