package com.czip.crm.query;

import com.czip.crm.vo.DesignYear;
import com.czip.crm.vo.InventYear;
import com.czip.crm.vo.Knowledge;
import com.czip.crm.vo.OfficialMoney;
import lombok.Data;

import java.util.List;

@Data
public class PatentMoneyQuery {

    private List<DesignYear> designYear;
    private List<InventYear> inventYear;
    private Knowledge knowledge;
    private OfficialMoney officialMoney;
}
