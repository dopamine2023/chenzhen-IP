package com.czip.crm.interceptor;

import com.czip.crm.dao.SysUserMapper;
import com.czip.crm.exceptions.NoLoginException;
import com.czip.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 非法访问拦截
 *    继承HandlerInterceptorAdapter适配器
 *
*
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    // 注入UserMapper
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 拦截用户是否是登录状态
     *  在目标方法（目标资源）执行前，执行的方法
     *
     *  方法返回布尔类型：
     *      如果返回true，表示目标方法可以被执行
     *      如果返回false，表示阻止目标方法执行
     *
     *  如果判断用户是否是登录状态：
     *      1. 判断cookie中是否存在用户信息（获取用户ID）
     *      2. 数据库中是否存在指定用户ID的值
     *
     *  如果用户是登录状态，则允许目标方法执行；如果用户是非登录状态，则抛出未登录异常 （在全局异常中做判断，如果是未登录异常，则跳转到登录页面）
     *

     * @param request
     * @param response
     * @param handler
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取cookie中的用户ID
        Long userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 判断用户ID是否为空，且数据库中存在该ID的用户记录
        if (null == userId || sysUserMapper.selectById(userId) == null) {
            // 抛出未登录异常
            throw new NoLoginException();
        }
        return true;
    }
}
