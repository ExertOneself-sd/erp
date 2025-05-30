package com.erp.service;

import com.erp.pojo.Item;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.vo.ItemCond;

import java.util.Map;

/**
* @author hzr
* @description 针对表【t_item】的数据库操作Service
* @createDate 2025-05-26 16:56:36
*/
public interface ItemService extends IService<Item> {

    /*public Map<String,Object> queryItemListService(Integer pageNum, Integer pageSize);*/

    public Map<String,Object> queryItemListService(ItemCond itemCond);

}
