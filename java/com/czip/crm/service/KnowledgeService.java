package com.czip.crm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czip.crm.dao.KnowledgeMapper;
import com.czip.crm.query.PatentMoneyQuery;
import com.czip.crm.vo.DesignYear;
import com.czip.crm.vo.InventYear;
import com.czip.crm.vo.Knowledge;
import com.czip.crm.vo.OfficialMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**   
 * @author: Na
 * 
 */
@Service
public class KnowledgeService extends ServiceImpl<KnowledgeMapper, Knowledge>  {
    @Autowired
    private DesignYearService designYearService;

    @Autowired
    private InventYearService inventYearService;

    @Autowired
    private OfficialMoneyService officialMoneyService;


    public PatentMoneyQuery selectAll(HttpServletRequest request) {
        PatentMoneyQuery patentMoneyQuery = new PatentMoneyQuery();
        List<DesignYear> designYearList = designYearService.list();
        if (!CollectionUtils.isEmpty(designYearList)) {
            patentMoneyQuery.setDesignYear(designYearList);
        }

        List<InventYear> inventYearsList = inventYearService.list();
        if (!CollectionUtils.isEmpty(inventYearsList)) {
            patentMoneyQuery.setInventYear(inventYearsList);
        }

        Knowledge kon = this.getById(1);
        patentMoneyQuery.setKnowledge(kon);
        OfficialMoney byId = officialMoneyService.getById(1);
        patentMoneyQuery.setOfficialMoney(byId);
        return patentMoneyQuery;
    }

    public void updateAll(PatentMoneyQuery patentMoneyQuery) {
        List<DesignYear> designYear = patentMoneyQuery.getDesignYear();
        if (!CollectionUtils.isEmpty(designYear)) {
            designYearService.updateBatchById(designYear);
        }

        List<InventYear> inventYear = patentMoneyQuery.getInventYear();
        if (!CollectionUtils.isEmpty(inventYear)) {
            inventYearService.updateBatchById(inventYear);
        }
        Knowledge knowledge = patentMoneyQuery.getKnowledge();
        if (Objects.nonNull(knowledge)) {
            this.updateById(knowledge);
        }
        OfficialMoney officialMoney = patentMoneyQuery.getOfficialMoney();
        if (Objects.nonNull(officialMoney)) {
            officialMoneyService.updateById(officialMoney);
        }


    }
}