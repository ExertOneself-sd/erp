package com.erp.mapper;

import com.erp.dto.HisData;
import com.erp.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hzr
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2025-05-13 09:08:58
* @Entity com.erp.pojo.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    public List<HisData> queryCountHisDataMapper();

    public List<Order> queryOrderListMapper(Order order);
}




