package com.czip.crm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czip.crm.dao.SysUserMapper;
import com.czip.crm.enums.StateStatus;
import com.czip.crm.model.UserModel;
import com.czip.crm.query.UserQuery;
import com.czip.crm.utils.*;
import com.czip.crm.vo.SysPower;
import com.czip.crm.vo.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserService extends ServiceImpl<SysUserMapper, SysUser> {


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysPowerService sysPowerService;


    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return void
     */
    public UserModel userLogin(String userName,String userPwd) {
        // 1. 参数判断，判断用户姓名、用户密码非空
        checkLoginParams(userName, userPwd);
        // 2. 判断账号密码是否正确
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUser::getAccount,userName);
        queryWrapper.lambda().eq(SysUser::getPassword,userPwd);
        SysUser user = getOne(queryWrapper);
        // 3.判断账号密码
        AssertUtil.isTrue(Objects.isNull(user), "账户密码不正确！");
        // 4.更新最后一次登录时间
        user.setLastDate(new Date());
        updateById(user);
        // 5.返回构建用户对象
        return buildUserInfo(user);
    }


    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassWord(Long userId, String oldPwd, String newPwd, String repeatPwd) {
        // 通过用户ID查询用户记录，返回用户对象
        SysUser sysUser = sysUserMapper.selectById(userId);
        // 判断用户记录是否存在
        AssertUtil.isTrue(null == sysUser, "待更新记录不存在！");

        // 参数校验
        checkPasswordParams(sysUser, oldPwd, newPwd, repeatPwd);

        // 设置用户的新密码
        sysUser.setPassword(newPwd);

        // 执行更新，判断受影响的行数
        AssertUtil.isTrue(!updateById(sysUser), "修改密码失败！");

    }

    /**
     * 修改密码的参数校验
     * @param user
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     * @return void
     */
    private void checkPasswordParams(SysUser user, String oldPwd, String newPwd, String repeatPwd) {
        //  判断原始密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd), "原始密码不能为空！");
        // 判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
        AssertUtil.isTrue(!user.getPassword().equals(oldPwd),"原始密码不正确！");

        // 判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd), "新密码不能为空！");
        // 判断新密码是否与原始密码一致 （不允许新密码与原始密码）
        AssertUtil.isTrue(oldPwd.equals(newPwd),"新密码不能与原始密码相同！");

        // 判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"确认密码不能为空！");
        // 判断确认密码是否与新密码一致
        AssertUtil.isTrue(!newPwd.equals(repeatPwd), "确认密码与新密码不一致！");

    }


    /**
     * 构建需要返回给客户端的用户对象
     * @param user
     * @return void
     */
    private UserModel buildUserInfo(SysUser user) {
        UserModel userModel = new UserModel();
        // userModel.setUserId(user.getId());
        // 设置加密的用户ID
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getAccount());
        userModel.setTrueName(user.getPassword());
        return userModel;
    }


    /**
     * 参数判断
     *  如果参数为空，抛出异常（异常被控制层捕获并处理）
     *

     * @param userName
     * @param userPwd
     * @return void
     */
    private void checkLoginParams(String userName, String userPwd) {
        // 验证用户姓名
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户姓名不能为空！");
        // 验证用户密码
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空！");
    }



    /**
     * 添加用户
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(Long id,SysUser user, String option) {
        /* 1. 参数校验 */
        checkUserParams(user.getAccount(), user.getEmail(), user.getPhone(), id, user.getPassword());
        user.setId(SnowFlake.nextId());
        user.setPassword(user.getPassword());
        user.setCreateDate(new Date());
        user.setCreateBy(id);
        user.setUpdateBy(id);
        user.setUpdateDate(new Date());

        /* 2. 执行添加操作，判断受影响的行数 */
        AssertUtil.isTrue(sysUserMapper.insert(user) < 1, "用户添加失败！");

        // 添加权限
        addOrUpdateUserOption(id, option, user);

    }
    private void addOrUpdateUserOption(Long id, String options, SysUser user) {
        // 判断id是否已经有操作权限
        List<SysPower> sysPower = sysPowerService.list(new QueryWrapper<SysPower>().eq("user_id", user.getId()));
        if (!CollectionUtils.isEmpty(sysPower)) {
            // 删除原有记录
            List<Integer> ids = sysPower.stream().filter(Objects::nonNull).map(SysPower::getId).collect(Collectors.toList());
            sysPowerService.removeByIds(ids);
        }
        if (StringUtils.isNotBlank(options)) {
            List<SysPower> list = new ArrayList();
            // 插入数据
            String[] split = options.split(",");
            for (String s : split) {
                SysPower sys = new SysPower();
                sys.setCreateBy(id);
                sys.setCreateTime(new Date());
                sys.setUserId(user.getId());
                sys.setOprationId(Integer.valueOf(s));
                sys.setStatus(StateStatus.STATED.getType());
                list.add(sys);
            }
            if (!CollectionUtils.isEmpty(list)) {
                sysPowerService.saveBatch(list);
            }
        }
    }



    /**
     * 更新用户
     * @param user
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(SysUser user) {
        // 判断用户ID是否为空，且数据存在
        AssertUtil.isTrue(null == user.getAccount(), "待更新记录不存在！");
        // 通过账号查询数据
        SysUser sysUser = getUserByAccount(user.getAccount());
        // 判断是否存在
        AssertUtil.isTrue(null == sysUser, "待更新记录不存在！");
        user.setId(sysUser.getId());
        // 密码和之前不一样
        if (StringUtils.isNotBlank(user.getPassword())) {
            if (!user.getPassword().equals(sysUser.getPassword())) {
               user.setPassword(user.getPassword());
            }
        }
        // 判断状态
        if (user.getStatus() == 0) {
            user.setStatus(sysUser.getStatus());
        }
        // 设置默认值
        user.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(sysUserMapper.updateById(user) != 1, "用户更新失败！");
    }


    /**
     *  参数校验
     * @param account
     * @param email
     * @param phone
     * @return void
     */
    private void checkUserParams(String account, String email, String phone, Long userId, String password) {
        // 判断用户名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(account), "用户名不能为空！");
        // 判断用户名的唯一性
        // 通过用户名查询用户对象
        SysUser user = getOne(new QueryWrapper<SysUser>().eq("account", account));

        // 如果用户对象为空，则表示用户名可用；如果用户对象不为空，则表示用户名不可用
        // 如果是添加操作，数据库中无数据，只要通过名称查到数据，则表示用户名被占用
        // 如果是修改操作，数据库中有对应的记录，通过用户名查到数据，可能是当前记录本身，也可能是别的记录
        // 如果用户名存在，且与当前修改记录不是同一个，则表示其他记录占用了该用户名，不可用
        AssertUtil.isTrue(null != user && !(user.getId().equals(userId)), "用户名已存在，请重新输入！");

        // 密码非空
        AssertUtil.isTrue(StringUtils.isBlank(password), "密码不能为空！");
        // 邮箱 非空
        AssertUtil.isTrue(StringUtils.isBlank(email), "用户邮箱不能为空！");

        // 手机号 非空
        AssertUtil.isTrue(StringUtils.isBlank(phone), "用户手机号不能为空！");

        // 手机号 格式判断
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "手机号格式不正确！");
    }

    /**
     * 用户删除
     * @param account
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(String account) {
        AssertUtil.isTrue(StringUtils.isBlank(account), "待删除记录不存在！");
        // 判断该用户
        SysUser user = getUserByAccount(account);
        AssertUtil.isTrue(Objects.isNull(user), "该用户不存在");

        // 删除该用户
        AssertUtil.isTrue(!removeById(user.getId()), "删除用户失败");

        // 删除操作权限
        List<SysPower> list = sysPowerService.list(new QueryWrapper<SysPower>().lambda().eq(SysPower::getUserId, user.getId()));
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> collect = list.stream().filter(Objects::nonNull).map(SysPower::getId).collect(Collectors.toList());
            sysPowerService.removeByIds(collect);
        }
    }


    public SysUser queryById(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        SysUser sysUser = sysUserMapper.selectById(userId);
        return sysUser;
    }

    public Map<String, Object> listUser(Long id,UserQuery userQuery) {
        // 查询用户是否是超级管理员
        SysUser sysUser = sysUserMapper.selectById(id);
        if(Objects.isNull(sysUser)) {
            return null;
        }
        // 查询数据
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(userQuery.getPage(), userQuery.getLimit());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        List<SysUser> status = null;
        if (Objects.nonNull(sysUser.getStatus()) && sysUser.getStatus() == 1) {
            status = this.list(queryWrapper.eq("status", sysUser.getStatus()));
        }

        if (StringUtils.isNotBlank(userQuery.getKeyword())) {
            queryWrapper.and(u -> {
                u.like("account", "%" + userQuery.getKeyword() + "%")
                        .or().like("dept","%" + userQuery.getKeyword() + "%")
                        .or().like("phone","%" + userQuery.getKeyword() + "%");
            });
        }
        status = this.list(queryWrapper);
        PageInfo<SysUser> pageInfo = new PageInfo(status);
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;
    }

    public Map<String, Object> listUser(Long id) {
        // 查询数据
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(1, 1);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUser::getId,id);
        List<SysUser> list = this.list(queryWrapper);
        PageInfo<SysUser> pageInfo = new PageInfo(list);
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 根据账户获取信息
     * @return
     */
    public SysUser getUserByAccount(String account) {
        return getOne(new QueryWrapper<SysUser>().eq("account",account));
    }

    public List<Integer> updatePowerPage(String account) {
        SysUser user = getUserByAccount(account);
        if (Objects.isNull(user)) {
            return new ArrayList<>();
        }
        List<SysPower> list = sysPowerService
                .list(new QueryWrapper<SysPower>().lambda().eq(SysPower::getUserId,user.getId()));
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<Integer> collect = list.stream().map(SysPower::getOprationId).collect(Collectors.toList());
        return collect;
    }

    public Boolean updatePower(Long userId, String account, String option) {
        SysUser user = getUserByAccount(account);
        addOrUpdateUserOption(userId, option, user);
        return true;

    }
}
