package com.erp.mapper;

import com.erp.pojo.Item;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erp.vo.ItemCond;

import java.util.List;

/**
* @author hzr
* @description 针对表【t_item】的数据库操作Mapper
* @createDate 2025-05-26 16:56:36
* @Entity com.erp.pojo.Item
*/
public interface ItemMapper extends BaseMapper<Item> {

    /*public List<Item> queryItemListMapper();*/

    public List<Item> queryItemListMapper(ItemCond itemCond);
}




