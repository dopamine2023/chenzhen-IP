package com.czip.crm.controller;

import com.czip.crm.base.BaseController;
import com.czip.crm.base.ResultInfo;
import com.czip.crm.query.PatentMoneyQuery;
import com.czip.crm.service.KnowledgeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Na
 * @time    Sun Sep 04 04:37:34 CST 2022
 *
 */
@Api(tags = "知产专利报价单", value="知产专利报价单" )
@Controller
@RequestMapping("/knowledge")
public class KnowledgeController extends BaseController {

    @Autowired
    private KnowledgeService knowledgeService;

    private final Integer editFlay = 2;

    @GetMapping("/selectAll")
    public String selectAll(HttpServletRequest request, Integer edit) {
      PatentMoneyQuery query = knowledgeService.selectAll(request);
      request.setAttribute("queryAll",query);
        if (editFlay.equals(edit)) {
            return "patentManagement/patent_quotation_edit";
        }
        return "patentManagement/patent_quotation";

    }

    @PostMapping("/updateAll")
    public ResultInfo updateAll(HttpServletRequest request, PatentMoneyQuery patentMoneyQuery) {
        knowledgeService.updateAll(patentMoneyQuery);
        return new ResultInfo();
    }


}