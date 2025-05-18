package com.erp.service;

import com.erp.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author hzr
* @description 针对表【t_order】的数据库操作Service
* @createDate 2025-05-13 09:08:58
*/
public interface OrderService extends IService<Order> {
    public Map<String,Object> queryOrderListServie(Integer pageNum,Integer pageSize,Order order);
}
