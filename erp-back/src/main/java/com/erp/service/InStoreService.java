package com.erp.service;

import com.erp.pojo.BuyList;
import com.erp.pojo.InStore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author hzr
* @description 针对表【t_in_store(入库单)】的数据库操作Service
* @createDate 2025-05-30 11:02:11
*/
public interface InStoreService extends IService<InStore> {

    public void saveInStoreService(BuyList buyList);


    public Map<String,Object> queryInStoreListService(Integer pageNum, Integer pageSize);

}
