package com.erp.mapper;

import com.erp.pojo.InStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hzr
* @description 针对表【t_in_store(入库单)】的数据库操作Mapper
* @createDate 2025-05-30 11:02:11
* @Entity com.erp.pojo.InStore
*/
public interface InStoreMapper extends BaseMapper<InStore> {

    /*完成入库单列表分页查询*/
    public List<InStore> queryInStoreListMapper();

}




