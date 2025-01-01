package com.czip.crm.query;


import com.czip.crm.base.BaseQuery;

/**
 * 用户查询
 */
public class UserQuery extends BaseQuery {

    private String keyword; // 关键字

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
