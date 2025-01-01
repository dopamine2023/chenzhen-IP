package com.czip.crm.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czip.crm.query.CountQuery;
import com.czip.crm.vo.PatentManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatentManagementMapper extends BaseMapper<PatentManagement> {

    List<CountQuery> count();

    Integer countDeal(String date);

    List<PatentManagement> selectListBy();

    List<PatentManagement> listAll(@Param("ew") QueryWrapper<PatentManagement> queryWrapper);
}
