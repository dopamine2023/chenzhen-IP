package com.czip.crm.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czip.crm.service.PatentManagementService;
import com.czip.crm.utils.SpringApplicationUtils;
import com.czip.crm.vo.PatentManagement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class ImportExcelLinkListener extends AnalysisEventListener<PatentManagement> {

    public ImportExcelLinkListener() {
    }

    private ArrayList<PatentManagement> arrayList = new ArrayList<>();

    public List<PatentManagement> getList() {
        return arrayList;
    }

    @Override
    public void invoke(PatentManagement promoteLink, AnalysisContext analysisContext) {
        setPromoteNumber(promoteLink);
        PatentManagementService patentManagementService = SpringApplicationUtils.getBean(PatentManagementService.class);
        promoteLink.setCreateTime(new Date());
        // 1.判断专利号是否存在 否：新增
        if (Objects.isNull(promoteLink) && Objects.isNull(promoteLink.getNumber())) {
            return;
        }
        List<PatentManagement> patent =
                patentManagementService.list(new QueryWrapper<PatentManagement>().eq("number", promoteLink.getNumber()));
        if (CollectionUtils.isEmpty(patent)) {
            arrayList.add(promoteLink);
            return;
        }
        // 2.专利持有人是否相同 否：新增
        List<String> holders = patent.stream().filter(Objects::nonNull).map(PatentManagement::getHolder).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(holders) && !holders.contains(promoteLink.getHolder())) {
            arrayList.add(promoteLink);
            return;
        }

        // 3.价格是否相同 否：新增
        List<String> money = patent.stream().filter(p -> {
            return StringUtils.equals(p.getHolder(), promoteLink.getHolder());
        }).map(PatentManagement::getMoney).collect(Collectors.toList());
        if (! CollectionUtils.isEmpty(money) && !money.contains(promoteLink.getMoney())) {
            arrayList.add(promoteLink);
            return;
        }

        // 以上不符合，进行覆盖
        QueryWrapper<PatentManagement> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(PatentManagement::getNumber,promoteLink.getNumber());
        queryWrapper.lambda().eq(PatentManagement::getHolder,promoteLink.getHolder());
        queryWrapper.lambda().eq(PatentManagement::getMoney,promoteLink.getMoney());
        List<PatentManagement> list = patentManagementService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        PatentManagement patentManagement = list.get(0);
        promoteLink.setId(patentManagement.getId());
        arrayList.add(promoteLink);
    }

    private boolean checkPromoteLink(PatentManagement promoteLink) {
        if (ObjectUtils.isEmpty(promoteLink)) {
            return true;
        }
        if (ObjectUtils.isEmpty(promoteLink.getNumber()) || promoteLink.getNumber().length() != 13) {
            return true;
        }
        return false;
    }

    public void setPromoteNumber(PatentManagement promoteLink){
        System.out.println(promoteLink.getNumber());
        char c = promoteLink.getNumber().charAt(4);
        if (Objects.isNull(c)) return;
        String number = Character.toString(c);
        if ("1".equals(number) || "8".equals(number)) {
            promoteLink.setType("发明专利");
            return;
        }
        if ("2".equals(number) || "9".equals(number)) {
            promoteLink.setType("实用新型");
            return;
        }
        if ("3".equals(number)) {
            promoteLink.setType("外观设计");
            return;
        }
        promoteLink.setType("数据错误，未知");
        return;


    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

