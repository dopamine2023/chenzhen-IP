package com.czip.crm.task;

import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czip.crm.service.DataLogService;
import com.czip.crm.service.PatentManagementService;
import com.czip.crm.utils.TimeUtils;
import com.czip.crm.vo.DataLog;
import com.czip.crm.vo.PatentManagement;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 定时任务的执行

 */
@Component
public class DataTask {

    @Resource
    private DataLogService dataLogService;

    @Resource
    private PatentManagementService patentManagementService;
    /**
     * 定时增加数据（20-80）
     * 9点-18点（每小时不定时增加）
     */
    @Scheduled(cron = "0 0 9-18 * * ? ")
    //@Scheduled(fixedDelay = 1000)
    public void addData() {
        // 获取随机数据进行增加
        int number = (int)(Math.random() * 10 + 1);
        // 判断当天数据是否超出
        QueryWrapper<DataLog> queryWrapper = new QueryWrapper();
        queryWrapper.select("sum(count) sum");
        queryWrapper.lambda()
                .eq(DataLog::getCreateDate, DateUtils.format(new Date(), DateUtils.DATE_FORMAT_10));
        DataLog one = dataLogService.getOne(queryWrapper);
        if (Objects.nonNull(one) && 80 < one.getSum() + number) {
            return;
        }
        // 插入数据
        DataLog dataLog = new DataLog();
        dataLog.setCount(number);
        dataLog.setCreateDate(new Date());
        dataLogService.save(dataLog);
    }

    /**
     * 每小时执行一次
     */
     @Scheduled(cron = "0 * */1 * * ? ")
   // @Scheduled(fixedDelay = 1000) //一秒执行一次
    public void updatePatentStatus() {
        // 获取当前时间前24小时时间
        String yesTerDay = TimeUtils.getYesTerDay();
        QueryWrapper<PatentManagement> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(PatentManagement::getSellStatus,"预定");
        queryWrapper.lambda().eq(PatentManagement::getStatusUpdateTime,yesTerDay);
        List<PatentManagement> list = patentManagementService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 更新状态为未售
        for (PatentManagement patentManagement : list) {
            patentManagement.setSellStatus("未售");
        }
        patentManagementService.updateBatchById(list);

    }
}
