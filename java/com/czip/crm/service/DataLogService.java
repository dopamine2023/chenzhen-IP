package com.czip.crm.service;

import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czip.crm.dao.DataLogMapper;
import com.czip.crm.query.CountQuery;
import com.czip.crm.vo.DataLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


@Service
public class DataLogService extends ServiceImpl<DataLogMapper, DataLog> {



    /**
     * 成交总数
     * @return
     */
    public CountQuery dealCount() {
        CountQuery countQuery = new CountQuery();
        // 总数
        DataLog one = getOne(new QueryWrapper<DataLog>().select("sum(count) sum"));
        if (Objects.isNull(one)) {
            countQuery.setCount(0);
        }
        countQuery.setCount(one.getSum());

        // 当天数据
        QueryWrapper<DataLog> queryWrapper = new QueryWrapper();
        queryWrapper.select("sum(count) sum");
        queryWrapper.lambda()
                .eq(DataLog::getCreateDate, DateUtils.format(new Date(), DateUtils.DATE_FORMAT_10));
        DataLog dayData = getOne(queryWrapper);
        if (Objects.isNull(dayData)) {
            countQuery.setNumber(0);
        }else {
            countQuery.setNumber(dayData.getSum());
        }

        countQuery.setName("成交");
        return countQuery;
    }
}