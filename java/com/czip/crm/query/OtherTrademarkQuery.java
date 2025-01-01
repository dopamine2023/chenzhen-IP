package com.czip.crm.query;

import com.czip.crm.vo.OtherSoftening;
import lombok.Data;

import java.util.List;

@Data
public class OtherTrademarkQuery {

    private Integer id;

    private String ordinary;


    private String reject;

    private String dissent;

    private String withdraw;

    private String extension;

    private String expand;

    private String licenseFiling;

    private String patentChanger;

    private String apply;

    private String design;

    private String name;

    private String evaluate;

    private List<OtherSoftening> others;
}
