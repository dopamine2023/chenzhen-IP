package com.czip.crm.controller;

import com.czip.crm.base.BaseController;
import com.czip.crm.base.ResultInfo;
import com.czip.crm.query.CountQuery;
import com.czip.crm.query.PatentManagementQuery;
import com.czip.crm.service.DataLogService;
import com.czip.crm.service.PatentManagementService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: Na
 * @time    Wed Jul 13 00:06:25 CST 2022
 *
 */
@Api(tags = "专利数据", value="专利数据" )
@Controller
@Log4j2
@RequestMapping("/patentManagement")
public class PatentManagementController extends BaseController {

    @Resource
    private PatentManagementService patentManagementService;

    @Resource
    private DataLogService dataLogService;

    @PostMapping("/importFile")
    public String importFile(MultipartFile multipartFile) throws IOException {
        return patentManagementService.importFile(multipartFile);
    }

    /**
     * 首页展示总数
     * @param request
     * @return
     */
    @RequestMapping("/countByType")
    public String countByType(HttpServletRequest request) {
        List<CountQuery> countQueries = patentManagementService.countByType();
        CountQuery countQuery = dataLogService.dealCount();
        if (Objects.nonNull(countQuery)) {
            countQueries.add(countQuery);
        }
        Map<String, CountQuery> collect = countQueries.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(CountQuery::getName, Function.identity(), (a1, a2) -> a2));
        request.setAttribute("count", collect);
        return "user/home";
    }

    /**
     * 专利管理页面
     * @return java.lang.String
     */
    @RequestMapping("patentManagement")
    public String patentManagement(){
        return "patentManagement/patentManagement";
    }

    /**
     * 专利数据
     * @param
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> listAll(HttpServletRequest request,PatentManagementQuery patentManagement) {
        return patentManagementService.listAll(request,patentManagement);
    }

    /**
     * 修改供应商等级
     * @param
     * @return
     */
    @PostMapping("/updateByLevel")
    @ResponseBody
    public ResultInfo updateByLevel(HttpServletRequest request, Long id, String level) {
        patentManagementService.updateByLevel(request,id, level);
        return new ResultInfo();
    }

    /**
     * 修改销售状态
     * @param
     * @return
     */
    @PostMapping("/updateBySellStatus")
    @ResponseBody
    public ResultInfo updateBySellStatus(HttpServletRequest request,Long id, String status) {
        patentManagementService.updateBySellStatus(request,id, status);
        return new ResultInfo();
    }

    /**
     * 删除
     * @param
     * @return
     */
    @PostMapping("/deleteByIds")
    @ResponseBody
    public ResultInfo deleteById(Long[] ids) {
        patentManagementService.removeByIds(Arrays.asList(ids));
        return new ResultInfo();
    }

    /**
     * 导入页面
     * @return
     */
    @RequestMapping("/import")
    public String patentImport() {
        return "patentManagement/import";
    }

    /**
     * 导入数据
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/importData")
    @ResponseBody
    public ResultInfo importData(MultipartFile file) throws IOException {
        patentManagementService.importFile(file);
        return new ResultInfo();
    }

    /**
     * 批量导出数据
     * @param httpServletResponse
     */
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse httpServletResponse, Long[] ids) throws IOException {
        patentManagementService.downloadExcel(httpServletResponse,ids);

    }

    /**
     * 下载模板
     * @return
     */
    @ResponseBody
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) {
        log.info("开始下载模板--------------->");
        String fileName = "专利模板.xlsx";
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("file/template.xlsx")) {
            response.setContentType("application/vnd.ms-template;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("下载模板出错：" + e);
        }
    }

    @ResponseBody
    @PostMapping("/delByIds")
    public ResultInfo delByIds(Long[] ids) {
        patentManagementService.delByIds(ids);
        return new ResultInfo();
    }

    @GetMapping("/quote")
    public String quoteIndex() {
        return "patentManagement/quote";
    }
}