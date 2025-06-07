package com.erp.mapper;

import com.erp.pojo.OutStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hzr
* @description 针对表【t_out_store(出库单)】的数据库操作Mapper
* @createDate 2025-06-03 11:44:04
* @Entity com.erp.pojo.OutStore
*/
public interface OutStoreMapper extends BaseMapper<OutStore> {

    /*实现出库单列表信息分页查询*/
    public List<OutStore> queryOutStoreListMapper();
}




