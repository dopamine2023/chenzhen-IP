package com.czip.crm.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czip.crm.dao.PatentManagementMapper;
import com.czip.crm.enums.DateEnums;
import com.czip.crm.enums.OperationEnums;
import com.czip.crm.listener.ImportExcelLinkListener;
import com.czip.crm.query.CountQuery;
import com.czip.crm.query.PatentManagementQuery;
import com.czip.crm.utils.AssertUtil;
import com.czip.crm.utils.LoginUserUtil;
import com.czip.crm.utils.TimeUtils;
import com.czip.crm.vo.PatentManagement;
import com.czip.crm.vo.SysPower;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @author: Na
 * 
 */
@Service
public class PatentManagementService extends ServiceImpl<PatentManagementMapper, PatentManagement> {

    @Resource
    private PatentManagementMapper patentManagementMapper;

    @Resource
    private SysPowerService sysPowerService;

    public String importFile(MultipartFile file) throws IOException {
            if (ObjectUtils.isEmpty(file)) {
                return "未选择文件，请重新选择！";
            }
            ImportExcelLinkListener importExcelLinkListener = new ImportExcelLinkListener();
            EasyExcel.read(file.getInputStream(),PatentManagement.class,importExcelLinkListener).sheet().doRead();
            List<PatentManagement> list = importExcelLinkListener.getList();
            if (!CollectionUtils.isEmpty(list)) {
                this.saveOrUpdateBatch(list);
            }

        return "导入成功";
    }

    /**
     * 根据类型获取总数以及未下证数
     * @return
     */
    public List<CountQuery> countByType() {
        List<CountQuery> count= patentManagementMapper.count();
        if (CollectionUtils.isEmpty(count)) {
            Collections.emptyMap();
        }
        return count;
    }


    public Integer countDeal(@RequestParam String date) {
        return patentManagementMapper.countDeal(date);
    }


    /**
     * 根据条件查询数据
     * @param patentManagement
     * @return
     */
    public Map<String, Object> listAll(HttpServletRequest request, PatentManagementQuery patentManagement) {

        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(patentManagement.getPage(), patentManagement.getLimit());
        QueryWrapper<PatentManagement> queryWrapper = new QueryWrapper();

        // 关键词
        if(!StringUtils.isEmpty(patentManagement.getKeyWord()) && patentManagement.getName().equals("name")) {
            String[] patents = patentManagement.getKeyWord().split(" ");
            queryWrapper.and(p -> {
                for (String patent : patents) {
                    String trim = patent.trim();
                    p.like(patentManagement.getName(),"%"+trim+"%").or();
                }
            });
        }else {
            queryWrapper.like(!StringUtils.isEmpty(patentManagement.getKeyWord()),patentManagement.getName(),"%" + patentManagement.getKeyWord() + "%");
        }
        queryWrapper.eq(!StringUtils.isEmpty(patentManagement.getType()), "type", patentManagement.getType());
        queryWrapper.eq(!StringUtils.isEmpty(patentManagement.getStatus()),"status",patentManagement.getStatus());
        queryWrapper.eq(!StringUtils.isEmpty(patentManagement.getLevel()),"level",patentManagement.getLevel());
        queryWrapper.eq(!StringUtils.isEmpty(patentManagement.getHolderType()), "holder_type", patentManagement.getHolderType());
        queryWrapper.eq(!StringUtils.isEmpty(patentManagement.getSellStatus()), "sell_status", patentManagement.getSellStatus());
        //时间
        if (!StringUtils.isEmpty(patentManagement.getDateName())) {
            Date date = getDate(patentManagement.getDateName());
            if (null != date) {
                queryWrapper.lambda().between(PatentManagement::getCreateTime, date, new Date());
            }
        }

        List<PatentManagement> list = new ArrayList<>();
        // 去重
        if (!StringUtils.isEmpty(patentManagement.getOnly())&& patentManagement.getOnly().equals("true")) {
            queryWrapper.orderByDesc("create_time");
            list = patentManagementMapper.listAll(queryWrapper);
        }else {
            // 排序
            queryWrapper.orderByDesc("create_time");
            list = list(queryWrapper);
        }

        //将数据鉴权
        checkPatentData(request, list);
        PageInfo<PatentManagement> pageInfo = new PageInfo(list);
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 获取时间
     * @param dateName
     * @return
     */
    private Date getDate(String dateName) {
        if (DateEnums.seven.getType().equals(dateName)) {
            return TimeUtils.getDay(7);
        }

        if (DateEnums.fifteen.getType().equals(dateName)) {
            return TimeUtils.getDay(15);
        }

        if (DateEnums.one_month.getType().equals(dateName)) {
            return TimeUtils.getMonth(1);
        }

        if (DateEnums.three_month.getType().equals(dateName)) {
            return TimeUtils.getMonth(3);
        }

        if (DateEnums.six_moth.getType().equals(dateName)) {
            return TimeUtils.getMonth(6);
        }

        if (DateEnums.ont_year.getType().equals(dateName)) {
            return TimeUtils.getYear(1);
        }
        return null;
    }

    public void updateByLevel(HttpServletRequest request,Long id, String level) {
        checkUser(request, OperationEnums.LEVEL.getCode());
        PatentManagement byId = this.getById(id);
        AssertUtil.isTrue(Objects.isNull(byId), "数据缺失，请联系管理员");
        byId.setLevel(level);
        this.updateById(byId);
    }

    private void checkUser(HttpServletRequest request, Integer code) {
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        QueryWrapper<SysPower> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysPower::getUserId,userId);
        queryWrapper.lambda().eq(SysPower::getOprationId,code);
        List<SysPower> list = sysPowerService.list(queryWrapper);
        AssertUtil.isTrue(CollectionUtils.isEmpty(list),"您没有操作权限！");

    }


    public void updateBySellStatus(HttpServletRequest request,Long id, String status) {
        checkUser(request, OperationEnums.SELL_STATUS.getCode());
        PatentManagement byId = this.getById(id);
        AssertUtil.isTrue(Objects.isNull(byId), "数据缺失，请联系管理员");
        byId.setSellStatus(status);
        SimpleDateFormat data =new SimpleDateFormat("yyyy-MM-dd HH");
        byId.setStatusUpdateTime(data.format(new Date()));
        this.updateById(byId);
    }

    public void downloadExcel(HttpServletResponse response, Long[] ids) throws IOException {
        ServletOutputStream outputStream = null;
        ExcelWriterBuilder write = null;
        try {
            AssertUtil.isTrue(Objects.isNull(ids), "您未进行选择数据，请选择");
            List list = this.listByIds(CollectionUtils.arrayToList(ids));
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            String fileName = "专利管理.xlsx";
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            outputStream = response.getOutputStream();
            write = EasyExcel.write(outputStream, PatentManagement.class);
            ExcelWriterSheetBuilder sheet = write.sheet();
            if (null != list && list.size() > 0) {
                sheet.doWrite(list);
            }
        } catch (Exception e) {
            log.error("导出文件异常，异常原因为{}", e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("excel文件导出失败, 失败原因：{}", e);
            }
        }
    }


    public void delByIds(Long[] ids) {
        this.removeByIds(CollectionUtils.arrayToList(ids));
    }

    private void checkPatentData(HttpServletRequest request, List<PatentManagement> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 获取权限
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        List<Integer> rower = sysPowerService.queryUserRower(userId);
        for (PatentManagement patentManagement : list) {
            if (CollectionUtils.isEmpty(rower) || !rower.contains(1001)) {
                patentManagement.setDeadline("*****");
            }
            if (CollectionUtils.isEmpty(rower) || !rower.contains(1002)) {
                patentManagement.setMoney("*****");
            }
            if (CollectionUtils.isEmpty(rower) || !rower.contains(1003)) {
                patentManagement.setHolder("*****");
            }
            if (CollectionUtils.isEmpty(rower) || !rower.contains(1004)) {
                patentManagement.setPhone("*****");
            }
            if (CollectionUtils.isEmpty(rower) || !rower.contains(1005)) {
                patentManagement.setSellStatus("*****");
            }
            if (CollectionUtils.isEmpty(rower) || !rower.contains(1006)) {
                patentManagement.setLevel("*****");
            }
            if (CollectionUtils.isEmpty(rower) || !rower.contains(1007)) {
                patentManagement.setHolderType("*****");
            }

        }
    }
}