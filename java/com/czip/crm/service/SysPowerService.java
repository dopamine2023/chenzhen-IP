package com.czip.crm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czip.crm.dao.SysPowerMapper;
import com.czip.crm.enums.StateStatus;
import com.czip.crm.vo.SysPower;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**   
 * @author: Na
 * 
 */
@Service
public class SysPowerService extends ServiceImpl<SysPowerMapper, SysPower>  {

    @Resource
    private SysPowerMapper sysPowerMapper;


    /**
     * 根据当前用户查询所有权限
     * 0 ：无权限
     * 1 : 有权限
     * @param userId
     * @return
     */
    public List<Integer> queryUserRower(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        QueryWrapper<SysPower> queryWrapper = new QueryWrapper();
        LambdaQueryWrapper<SysPower> eq = queryWrapper.lambda()
                .eq(SysPower::getUserId, userId)
                .eq(SysPower::getStatus, StateStatus.STATED.getType());
        List<SysPower> list = list(eq);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Integer> collect = list.stream().filter(Objects::nonNull)
                .map(SysPower::getOprationId).collect(Collectors.toList());

        return collect;
    }
}