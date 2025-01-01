package com.czip.crm.controller;

import com.czip.crm.base.BaseController;
import com.czip.crm.query.OtherTrademarkQuery;
import com.czip.crm.service.OtherSofteningService;
import com.czip.crm.service.OtherTrademarkService;
import com.czip.crm.vo.OtherSoftening;
import com.czip.crm.vo.OtherTrademark;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author:
 * @time    Sun Aug 28 01:25:48 CST 2022
 *
 */
@Api(tags = "报价管理-其他报价-知产软著", value="报价管理-其他报价-知产软著" )
@Controller
@RequestMapping("/otherSoftening")
public class OtherSofteningController extends BaseController {

    private final Integer editFlay = 2;
    @Resource
    private OtherSofteningService otherSofteningService;

    @Resource
    private OtherTrademarkService otherTrademarkService;

    @GetMapping("/selectAll")
    public String selectALL(HttpServletRequest request, Integer edit) {
        List<OtherTrademark> tran = otherTrademarkService.list();
        OtherTrademark otherTrademark = new OtherTrademark();
        if (!CollectionUtils.isEmpty(tran)) {
            otherTrademark = tran.get(0);
        }
        request.setAttribute("trademark",otherTrademark);
        List<OtherSoftening> list = otherSofteningService.list();
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        request.setAttribute("softening", list);
        if (editFlay.equals(edit)) {
            return "patentManagement/other_quotation_edit";
        }
        return "patentManagement/other_quotation";
    }

    @PostMapping("/update")
    public String updateAll(HttpServletRequest request, OtherTrademarkQuery otherTrademarkQuery) {
        if (Objects.isNull(otherTrademarkQuery)) {
            return null;
        }

        if (Objects.nonNull(otherTrademarkQuery.getOthers())) {
            otherSofteningService.updateBatchById(otherTrademarkQuery.getOthers());
        }
        OtherTrademark other = new OtherTrademark();
        BeanUtils.copyProperties(otherTrademarkQuery, other);
        otherTrademarkService.updateById(other);

        List<OtherTrademark> tran = otherTrademarkService.list();
        OtherTrademark otherTrademark = new OtherTrademark();
        if (!CollectionUtils.isEmpty(tran)) {
            otherTrademark = tran.get(0);
        }
        request.setAttribute("trademark",otherTrademark);
        List<OtherSoftening> list = otherSofteningService.list();
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        request.setAttribute("softening", list);
        return "patentManagement/other_quotation";
    }
}