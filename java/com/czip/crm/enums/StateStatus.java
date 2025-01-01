package com.czip.crm.enums;

/**
 * 分配状态枚举类
 */
public enum  StateStatus {
    // 未权限
    UNSTATE(0),
    // 有权限
    STATED(1);

    private Integer type;

    StateStatus(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
