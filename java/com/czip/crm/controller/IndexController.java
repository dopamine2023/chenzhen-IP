package com.czip.crm.controller;

import com.czip.crm.base.BaseController;
import com.czip.crm.service.SysPowerService;
import com.czip.crm.service.UserService;
import com.czip.crm.utils.LoginUserUtil;
import com.czip.crm.vo.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
*
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private SysPowerService sysPowerService;

    /**
     * 系统登录页
     * @return java.lang.String
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }


    /**
     * 系统界面欢迎页
     * @return java.lang.String
     */
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("indexError")
    public String indexError(HttpServletRequest request, String msg) {
        request.setAttribute("msg", msg);
        return "index_error";
    }

    /**
     * 后端管理主页面
     * @param
     * @return java.lang.String
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){

        // 获取cookie中的用户Id
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 查询用户对象，设置session作用域
        SysUser user = userService.getById(userId);
        request.getSession().setAttribute("user",user);
        List<Integer> list = new ArrayList<>();
        // 权限列表
        List<Integer> permissions = sysPowerService.queryUserRower(userId);
        if (!CollectionUtils.isEmpty(permissions)) {
            list.addAll(permissions);
        }
        list.add(user.getStatus());
        request.getSession().setAttribute("permissions", list);
        return "main";
    }


}
