package com.erp.service;

import com.erp.pojo.OutStore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author hzr
* @description 针对表【t_out_store(出库单)】的数据库操作Service
* @createDate 2025-06-03 11:44:04
*/
public interface OutStoreService extends IService<OutStore> {

    public void saveOutStoreService(OutStore outStore);

    public Map<String,Object> queryOutStoreService(Integer pageNum,Integer pageSize);

}
