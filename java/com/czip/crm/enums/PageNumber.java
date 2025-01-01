package com.czip.crm.enums;

public enum PageNumber {
    // 未权限
    userPage(15),
    // 有权限
    patetPage(12);

    private Integer page;
    PageNumber(Integer page) {
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }
}
