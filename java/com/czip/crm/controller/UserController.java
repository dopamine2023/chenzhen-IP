package com.czip.crm.controller;

import com.czip.crm.base.BaseController;
import com.czip.crm.base.ResultInfo;
import com.czip.crm.exceptions.ParamsException;
import com.czip.crm.model.UserModel;
import com.czip.crm.query.UserQuery;
import com.czip.crm.service.UserService;
import com.czip.crm.utils.LoginUserUtil;
import com.czip.crm.vo.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
*
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param userName 账号密码
     * @param userPwd
     * @return com.czip.crm.base.ResultInfo
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd) {

        ResultInfo resultInfo = new ResultInfo();

        // 调用service层登录方法
        UserModel userModel = userService.userLogin(userName, userPwd);

        // 设置ResultInfo的result的值 （将数据返回给请求）
        resultInfo.setResult(userModel);

        return resultInfo;
    }

    @GetMapping("/queryById")
    public String queryById(HttpServletRequest request, Model model) {
        // 获取cookie中的userId
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        SysUser sysUser = userService.queryById(userId);
        // 设置域中
        model.addAttribute("user",sysUser);
        return "user/sel_user";
    }

    /**
     * 用户修改密码
     *
     *
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param repeatPassword
     * @return com.czip.crm.base.ResultInfo
     */
    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
             String oldPassword, String newPassword, String repeatPassword) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            // 获取cookie中的userId
            Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
            // 调用Service层修改密码方法
            userService.updatePassWord(userId, oldPassword, newPassword, repeatPassword);
        } catch (ParamsException p) {
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        } catch (Exception e) {
            resultInfo.setCode(500);
            resultInfo.setMsg("修改密码失败！");
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 进入修改密码的页面
     * @param
     * @return java.lang.String
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }

    /**
     * 查询所有的用户
     */
    @RequestMapping("listSuperUser")
    @ResponseBody
    public Map<String,Object> listUser(HttpServletRequest request,UserQuery userQuery){
        // 获取cookie中的userId
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return userService.listUser(userId,userQuery);
    }

    /**
     * 普通用户
     */
    @RequestMapping("listUser")
    @ResponseBody
    public Map<String,Object> listUser(HttpServletRequest request){
        // 获取cookie中的userId
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return userService.listUser(userId);
    }



    /**
     * 进入超级管理员列表页面
     *
     * @param
     * @return java.lang.String
     */
    @RequestMapping("supperIndex")
    public String supperIndex() {
        return "user/super_user";
    }

    /**
     * 进入普通用户列表
     * @return java.lang.String
     */
    @RequestMapping("index")
    public String index() {
        return "user/user";
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping("toAddUserPage")
    public String toAddUserPage() {
        return "user/add";
    }
    /**
     * 添加用户
     * @param user
     * @return com.czip.crm.base.ResultInfo
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addUser(HttpServletRequest request,SysUser user, String option) {
        // 获取cookie中的userId
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.addUser(userId,user, option);
        return success("用户添加成功！");

    }

    /**
     * 更新用户页面
     * @return
     */
    @RequestMapping("updateSuperUserPage")
    public String updateSuperUserPage(String account, HttpServletRequest request) {
        // 判断id是否为空，不为空表示更新操作，查询用户对象
        if (account != null) {
            // 通过id查询用户对象
            SysUser byId = userService.getUserByAccount(account);
            // 将数据设置到请求域中
            request.setAttribute("userInfo",byId);
        }
        return "user/super_update";
    }

    /**
     * 更新用户页面
     * @return
     */
    @RequestMapping("updateUserPage")
    public String updateUserPage(String account, HttpServletRequest request) {
        // 判断id是否为空，不为空表示更新操作，查询用户对象
        if (account != null) {
            // 通过id查询用户对象
            SysUser byId = userService.getUserByAccount(account);
            // 将数据设置到请求域中
            request.setAttribute("userInfo",byId);
        }
        return "user/user_update";
    }

    /**
     * 更新用户
     *
     * @param user
     * @return com.czip.crm.base.ResultInfo
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateUser(SysUser user) {
        userService.updateUser(user);
        return success("用户更新成功！");
    }


    /**
     * 打开添加或修改用户的页面
     *
     * @param
     * @return java.lang.String
     */
    @RequestMapping("toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id, HttpServletRequest request) {

        // 判断id是否为空，不为空表示更新操作，查询用户对象
        if (id != null) {
            // 通过id查询用户对象
            SysUser byId = userService.getById(id);
            // 将数据设置到请求域中
            request.setAttribute("userInfo",byId);
        }

        return "user/add_update";
    }


    /**
     * 用户删除
     *
     * @param account
     * @return com.czip.crm.base.ResultInfo
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(String account) {
        userService.deleteByIds(account);
        return success("用户删除成功！");
    }



    /**
     * 修改权限页面
     *
     */
    @RequestMapping("updatePowerPage")
    public String updatePowerPage(HttpServletRequest request, String account) {
        List<Integer>list = userService.updatePowerPage(account);
        request.setAttribute("resultInfo",list);
        request.setAttribute("account", account);
        return "user/power";
    }

    /**
     * 修改权限功能
     *
     */
    @PostMapping("updatePower")
    @ResponseBody
    public ResultInfo updatePowerPage(HttpServletRequest request, String account, String option) {
        // 获取cookie中的userId
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updatePower(userId,account, option);
        return success("用户修改成功");
    }
}
