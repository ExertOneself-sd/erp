package com.erp.mapper;

import com.erp.pojo.Roler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hzr
* @description 针对表【t_roler】的数据库操作Mapper
* @createDate 2025-05-19 14:32:14
* @Entity com.erp.pojo.Roler
*/
public interface RolerMapper extends BaseMapper<Roler> {


    public List<Integer> queryRoleMidsMapper(Integer rid);
}




